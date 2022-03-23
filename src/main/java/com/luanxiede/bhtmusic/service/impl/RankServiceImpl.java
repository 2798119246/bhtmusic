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
        // 必须做一下校验，否则会出现0/0的情况
        int Scoresum = rankMapper.selectScoreSum(songListId);
        int RankNum = rankMapper.selectRankNum(songListId);
        if (Scoresum != 0 && RankNum != 0){
            return Scoresum/RankNum;
        }else return 0;
    }

    @Override
    public boolean addRank(Rank rank) {
        /**
         * 评分有两种情况，一种是没有评过分，那么直接写入此次评分就好，
         * 另外就是已经评分了，那么会覆盖上次评分
         */
        int flag = rankMapper.isExistScore(rank);
        if (flag > 0){
            return rankMapper.updateScore(rank) > 0 ? true : false;
        }else
            return rankMapper.insertSelective(rank) > 0 ? true:false;
    }
}
