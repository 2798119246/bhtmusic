package com.luanxiede.bhtmusic.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Data
public class SongList {

    private Integer id;

    private String title;

    private String pic;

    private String style;

    private String introduction;

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
