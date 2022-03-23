package com.luanxiede.bhtmusic.service;


import com.luanxiede.bhtmusic.domain.Rank;

public interface RankService {

    int rankOfSongListId(Long songListId);

    boolean addRank(Rank rank);
}
