package com.ndp.techsharing.Controllers;

import java.util.ArrayList;
import java.util.List;

import com.ndp.techsharing.Entities.User;
import com.ndp.techsharing.Entities.UserInfo;
import com.ndp.techsharing.Entities.UserRole;
import com.ndp.techsharing.Models.Auth.UserModel;
import com.ndp.techsharing.Models.UsersMan.ActiveStateChangeModel;
import com.ndp.techsharing.Models.UsersMan.PasswordForceChangeModel;
import com.ndp.techsharing.Models.UsersMan.UsersManModelReturn;
import com.ndp.techsharing.Services.UserInfoService;
import com.ndp.techsharing.Services.UserRoleService;
import com.ndp.techsharing.Services.UserService;
import com.ndp.techsharing.Utils.Auth.PasswordAuthUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/users")
public class UserREST {
    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping(
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> retrieveByRole(@RequestParam(value = "role", required = true) String role) {
        ResponseEntity<Object> entity;

        Integer roleId = 3;

        if(role.equals("admin")) {
            roleId = 1;
        } else if(role.equals("mod")) {
            roleId = 2;
        } else if(role.equals("norm")) {
            roleId = 3;
        }

        List<UserRole> userRoles = userRoleService.retrieveByRole(roleId);

        List<UsersManModelReturn> usersManModelReturns = new ArrayList<UsersManModelReturn>();

        for(UserRole item : userRoles) {
            String tmpUsername = item.getUsername();

            User tmpUser = userService.retrieveOne(tmpUsername);

            Boolean tmpActive = tmpUser.getActive();

            UserInfo tmpUserInfo = userInfoService.retrieveOne(tmpUsername);

            String tmpAvatar = "";

            if(tmpUserInfo != null) {
                tmpAvatar = tmpUserInfo.getAvatar();
            }

            UsersManModelReturn tmpUsersManModelReturn = new UsersManModelReturn(tmpUsername, tmpActive, tmpAvatar);

            usersManModelReturns.add(tmpUsersManModelReturn);
        }

        entity = new ResponseEntity<>(usersManModelReturns, HttpStatus.OK);

        return entity;
    }

    // Change password (by admin)
    @PutMapping(
        value = "/password",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> forceChangePassword(@RequestBody PasswordForceChangeModel passwordForceChangeModel) {
        ResponseEntity<Object> entity;

        if(passwordForceChangeModel.getUsername() == null || passwordForceChangeModel.getNewPassword() == null) {
            entity = new ResponseEntity<>("{ \"Notice\": \"Username and password not be empty\" }", HttpStatus.BAD_REQUEST);
        } else {
            User tmpUser = userService.retrieveOne(passwordForceChangeModel.getUsername());

            PasswordAuthUtil passwordAuthUtil = new PasswordAuthUtil();

            String encryptedPassword = passwordAuthUtil.storePassword(passwordForceChangeModel.getNewPassword());

            tmpUser.setPassword(encryptedPassword);
            
            User tmpToSave = userService.updateOne(tmpUser);

            if(tmpToSave == null) {
                entity = new ResponseEntity<>("{ \"Notice\": \"Failed to change password\" }", HttpStatus.BAD_REQUEST);
            } else {
                entity = new ResponseEntity<>("{ \"username\": \"" + passwordForceChangeModel.getUsername() + "\", \"password\": \"" + passwordForceChangeModel.getNewPassword() + "\" }", HttpStatus.OK);
            }
        }

        return entity;
    }

    @PutMapping(
        value = "/active-state",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> changeActiveState(@RequestBody ActiveStateChangeModel activeStateChangeModel) {
        ResponseEntity<Object> entity;

        if(activeStateChangeModel.getUsername() == null || activeStateChangeModel.getActive() == null) {
            entity = new ResponseEntity<>("{ \"Notice\": \"Not be empty\" }", HttpStatus.BAD_REQUEST);
        } else {
            User tmpUser = userService.retrieveOne(activeStateChangeModel.getUsername());

            tmpUser.setActive(activeStateChangeModel.getActive());

            User tmpToSave = userService.updateOne(tmpUser);

            if(tmpToSave == null) {
                entity = new ResponseEntity<>("{ \"Notice\": \"Failed to change password\" }", HttpStatus.BAD_REQUEST);
            } else {
                entity = new ResponseEntity<>("{ \"username\": \"" + activeStateChangeModel.getUsername() + "\", \"active\": \"" + activeStateChangeModel.getActive() + "\" }", HttpStatus.OK);
            }
        }

        return entity;
    }

    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> register(@RequestBody UserModel user) {
        ResponseEntity<Object> entity;

        if(user.getUsername() == null || user.getPassword() == null) {
            entity = new ResponseEntity<>("{ \"Notice\": \"Username and password not be empty\" }", HttpStatus.BAD_REQUEST);
        } else {
            PasswordAuthUtil passwordAuthUtil = new PasswordAuthUtil();

            String encryptedPassword = passwordAuthUtil.storePassword(user.getPassword());
            User tmpToCreate = new User(user.getUsername(), encryptedPassword, true);
            User tmpSaved = userService.createOne(tmpToCreate);

            if(tmpSaved == null) {
                entity = new ResponseEntity<>("{ \"Notice\": \"Username is existed\" }", HttpStatus.BAD_REQUEST);
            } else {
                UserRole tmpUserRoleToSave = new UserRole(0, user.getUsername(), 2);
                UserRole tmpUserRoleSaved = userRoleService.createOne(tmpUserRoleToSave);

                if(tmpUserRoleSaved == null) {
                    entity = new ResponseEntity<>("{ \"Notice\": \"Failed to create user\" }", HttpStatus.BAD_REQUEST);
                } else {
                    entity = new ResponseEntity<>("{ \"username\": \"" + user.getUsername() + "\", \"password\": \"" + user.getPassword() + "\" }", HttpStatus.OK);
                }
            }
        }
        
        return entity;
    }
}
