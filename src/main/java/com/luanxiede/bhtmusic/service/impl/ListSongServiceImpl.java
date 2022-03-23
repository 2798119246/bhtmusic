package com.luanxiede.bhtmusic.service.impl;


import com.luanxiede.bhtmusic.dao.ListSongMapper;
import com.luanxiede.bhtmusic.domain.ListSong;
import com.luanxiede.bhtmusic.service.ListSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListSongServiceImpl implements ListSongService {

    @Autowired
    private ListSongMapper listSongMapper;

    @Override
    public List<ListSong> allListSong()
    {
        return listSongMapper.allListSong();
    }

    @Override
    public boolean updateListSongMsg(ListSong listSong) {
        return listSongMapper.updateListSongMsg(listSong) >0 ?true:false;
    }

    @Override
    public boolean deleteListSong(Integer songId) {
        return listSongMapper.deleteListSong(songId) >0 ?true:false;
    }

    @Override
    public boolean addListSong(ListSong listSong)
    {
        return listSongMapper.insertSelective(listSong) > 0?true:false;
    }

    @Override
    public List<ListSong> listSongOfSongId(Integer songListId)
    {
        return listSongMapper.listSongOfSongId(songListId);
    }

}
