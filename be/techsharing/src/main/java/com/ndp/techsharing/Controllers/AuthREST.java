package com.ndp.techsharing.Controllers;

import com.ndp.techsharing.Entities.User;
import com.ndp.techsharing.Entities.UserRole;
import com.ndp.techsharing.Models.Auth.CredentialReturn;
import com.ndp.techsharing.Models.Auth.PasswordChangeModel;
import com.ndp.techsharing.Models.Auth.UserModel;
import com.ndp.techsharing.Services.UserRoleService;
import com.ndp.techsharing.Services.UserService;
import com.ndp.techsharing.Utils.Auth.PasswordAuthUtil;
import com.ndp.techsharing.Utils.Auth.JWT.jwtSecurity;
import com.ndp.techsharing.Utils.Auth.JWT.myJWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/auth")
public class AuthREST {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    // Login
    @PutMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> login(@RequestBody UserModel user) {
        ResponseEntity<Object> entity;

        if(user.getUsername() == null || user.getPassword() == null) {
            entity = new ResponseEntity<>("{ \"Notice\": \"Username and password not be empty\" }", HttpStatus.BAD_REQUEST);
        } else {
            User tmpUser = userService.retrieveOne(user.getUsername());

            if(tmpUser == null) {
                entity = new ResponseEntity<>("{ \"Notice\": \"Invalid username or password\" }", HttpStatus.BAD_REQUEST);  // invalid username
            } else {
                if(tmpUser.getActive()) {
                    PasswordAuthUtil passwordAuthUtil = new PasswordAuthUtil();

                    if(passwordAuthUtil.verifyPassword(user.getPassword(), tmpUser.getPassword())) {
                        // create token here
                        myJWT jwt = new jwtSecurity();

                        String token = jwt.GenerateToken(user.getUsername());

                        if(token == "") {
                            entity = new ResponseEntity<>("{ \"Notice\": \"Invalid username or password\" }", HttpStatus.BAD_REQUEST);  // failed to create token
                        } else {
                            UserRole userRole = userRoleService.retrieveOneByUsername(user.getUsername());

                            String roleStr = "";

                            if(userRole.getRoleId() == 1) {
                                roleStr = "admin";
                            } else if(userRole.getRoleId() == 2) {
                                roleStr = "mod";
                            } else if(userRole.getRoleId() == 3) {
                                roleStr = "norm";
                            }

                            CredentialReturn credentialReturn = new CredentialReturn(user.getUsername(), roleStr, token);

                            entity = new ResponseEntity<>(credentialReturn, HttpStatus.OK);
                        }
                    } else {
                        entity = new ResponseEntity<>("{ \"Notice\": \"Invalid username or password\" }", HttpStatus.BAD_REQUEST);  // invalid password
                    }
                } else {
                    entity = new ResponseEntity<>("{ \"Notice\": \"User was blocked\" }", HttpStatus.LOCKED);
                }
            }
        }

        return entity;
    }

    // Update password

    @PatchMapping(
        value = "/{username}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> updatePassword(@PathVariable("username") String username, @RequestBody PasswordChangeModel passwordChangeModel) {
        ResponseEntity<Object> entity;

        if(passwordChangeModel.getOldValue() == null | passwordChangeModel.getNewValue() == null) {
            entity = new ResponseEntity<>("{ \"Notice\": \"Not be empty\" }", HttpStatus.BAD_REQUEST);
        } else {
            User tmpUser = userService.retrieveOne(username);

            if(tmpUser == null) {
                entity = new ResponseEntity<>("{ \"Notice\": \"Invalid username or password\" }", HttpStatus.BAD_REQUEST);  // invalid username
            } else {
                PasswordAuthUtil passwordAuthUtil = new PasswordAuthUtil();

                if(passwordAuthUtil.verifyPassword(passwordChangeModel.getOldValue(), tmpUser.getPassword())) {

                    String encryptedPassword = passwordAuthUtil.storePassword(passwordChangeModel.getNewValue());
                    User tmpToUpdate = new User(username, encryptedPassword, true);
                    User tmpUserChangedPwd = userService.updateOne(tmpToUpdate);

                    if(tmpUserChangedPwd == null) {
                        entity = new ResponseEntity<>("{ \"Notice\": \"Invalid username or password\" }", HttpStatus.BAD_REQUEST);  // failed to create
                    } else {
                        entity = new ResponseEntity<>("{ \"username\": \"" + username + "\", \"password\": \"" + passwordChangeModel.getNewValue() + "\" }", HttpStatus.OK);
                    }
                } else {
                    entity = new ResponseEntity<>("{ \"Notice\": \"Invalid username or password\" }", HttpStatus.BAD_REQUEST);  // invalid password
                }
            }
        }
        
        return entity;
    }

    // Register
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
                UserRole tmpUserRoleToSave = new UserRole(0, user.getUsername(), 3);
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
