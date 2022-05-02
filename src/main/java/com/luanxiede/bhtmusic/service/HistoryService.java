package com.luanxiede.bhtmusic.service;

import com.luanxiede.bhtmusic.domain.History;

import java.util.List;

/**
 * @author passer-by
 * @date 2022/4/30
 */

public interface HistoryService {

    boolean  insertPrint(History print);

    List<History> selectSongIdByUserId(Integer userId);

    History findDetail(History print);

    boolean updatePrint(History print);
}
