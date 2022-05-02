package com.luanxiede.bhtmusic.service.impl;

import com.luanxiede.bhtmusic.dao.HistoryMapper;
import com.luanxiede.bhtmusic.domain.History;
import com.luanxiede.bhtmusic.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author passer-by
 * @date 2022/4/30
 */
@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    HistoryMapper historyMapper;

    @Override
    public boolean insertPrint(History print) {
        return historyMapper.insertPrint(print) > 0;
    }

    @Override
    public List<History> selectSongIdByUserId(Integer userId) {
        return historyMapper.selectSongIdByUserId(userId);
    }

    @Override
    public History findDetail(History print) {
        return historyMapper.findDetail(print);
    }

    @Override
    public boolean updatePrint(History print) {
        return historyMapper.updatePrint(print) > 0;
    }
}
