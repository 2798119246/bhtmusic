package com.luanxiede.bhtmusic.controller.vo;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

/**
 * @author passer-by
 * @date 2022/5/2
 * 用于返回历史播放记录
 */
@Data
public class SongVo {

    private Integer id;

    private Integer singerId;

    private String name;

    private String introduction;

    private Date createTime;

    private Date updateTime;

    private String pic;

    private String lyric;

    private String url;
    // 最新播放时间
    private Date playTime;
    // 播放次数
    private Integer views;

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }
    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    public void setLyric(String lyric) {
        this.lyric = lyric == null ? null : lyric.trim();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
