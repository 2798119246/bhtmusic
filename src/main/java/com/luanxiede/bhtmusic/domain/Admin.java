package com.luanxiede.bhtmusic.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Data
public class Admin {
    private Integer id;

    private String name;

    private String password;

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
