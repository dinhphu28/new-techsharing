package com.ndp.techsharing.Models.UsersMan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActiveStateChangeModel {
    private String username;

    private Boolean active;
}
