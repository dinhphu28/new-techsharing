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
public class UserInfoUpdateModel {

    private String avatar;

    private String email;

    public UserInfo toUserInfoEntity(String username) {
        return new UserInfo(username, avatar, email);
    }
}
