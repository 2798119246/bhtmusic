package com.luanxiede.bhtmusic.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author passer-by
 * @date 2022/4/30
 */

@Data
public class History implements Serializable {
    // 主键
    private Integer id;
    // 业务主键
    private String historyId;
    // 用户id
    private Integer userId;
    // 歌曲id
    private Integer songId;
    // 最新播放时间
    private Date playTime;
    // 播放次数
    private Integer views;
}
