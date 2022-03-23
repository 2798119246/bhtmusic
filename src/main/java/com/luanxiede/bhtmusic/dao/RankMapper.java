package com.luanxiede.bhtmusic.dao;


import com.luanxiede.bhtmusic.domain.Rank;
import org.springframework.stereotype.Repository;

@Repository
public interface RankMapper {

    int insert(Rank record);

    // 查询该用户是否已经评分过，若已经评分过，则覆盖评分
    int isExistScore(Rank record);

    int insertSelective(Rank record);

    /**
     * 查总分
     * @param songListId
     * @return
     */
    int selectScoreSum(Long songListId);

    /**
     * 查总评分人数
     * @param songListId
     * @return
     */
    int selectRankNum(Long songListId);

    //更新评分
    int updateScore(Rank record);
}
