package com.luanxiede.bhtmusic.dao;

import com.luanxiede.bhtmusic.domain.History;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author passer-by
 * @date 2022/4/30
 */
@Repository
public interface HistoryMapper {

    int insertPrint(History print);

    List<History> selectSongIdByUserId(Integer userId);

    History findDetail(History print);

    int updatePrint(History print);

}
