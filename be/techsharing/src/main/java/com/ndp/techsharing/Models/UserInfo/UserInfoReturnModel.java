package com.ndp.techsharing.Models.UserInfo;

import com.ndp.techsharing.Entities.UserInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoReturnModel {
    private String username;

    private String avatar;

    private String email;

    public UserInfoReturnModel (UserInfo userInfo) {
        username = userInfo.getUsername();

        avatar = userInfo.getAvatar();

        email = userInfo.getEmail();
    }
}
