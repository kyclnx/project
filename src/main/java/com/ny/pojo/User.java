package com.ny.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author njx
 * @date 2024/11/4
 * @Description
 */
@Setter
@Getter
public class User {
    private Long id;
    private String username;
    private String password;

    public User() {
    }

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
