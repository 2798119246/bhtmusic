package com.luanxiede.bhtmusic.service.impl;


import com.luanxiede.bhtmusic.dao.RankMapper;
import com.luanxiede.bhtmusic.domain.Rank;
import com.luanxiede.bhtmusic.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RankServiceImpl implements RankService {

    @Autowired
    private RankMapper rankMapper;

    @Override
    public int rankOfSongListId(Long songListId) {
        return rankMapper.selectScoreSum(songListId) / rankMapper.selectRankNum(songListId);
    }

    @Override
    public boolean addRank(Rank rank) {

        return rankMapper.insertSelective(rank) > 0 ? true:false;
    }
}
