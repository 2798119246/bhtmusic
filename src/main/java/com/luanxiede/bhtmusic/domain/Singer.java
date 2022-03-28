package com.luanxiede.bhtmusic.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

@Data
public class Singer {

    private Integer id;

    private String name;

    private Byte sex;

    private String pic;

    private Date birth;

    private String location;

    private String introduction;

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }


    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
