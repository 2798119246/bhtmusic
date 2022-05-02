package com.luanxiede.bhtmusic.controller;

import com.luanxiede.bhtmusic.controller.vo.SongVo;
import com.luanxiede.bhtmusic.domain.History;
import com.luanxiede.bhtmusic.domain.Song;
import com.luanxiede.bhtmusic.service.HistoryService;
import com.luanxiede.bhtmusic.service.SongService;
import com.luanxiede.bhtmusic.utils.BizIdHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * @author passer-by
 * @date 2022/4/30
 */
@RestController
@Controller
@Slf4j
public class HistoryController {

    @Autowired
    HistoryService historyService;
    @Autowired
    BizIdHelper bizIdHelper;
    @Autowired
    SongService songService;

    @ResponseBody
    @RequestMapping(value = "/history/rememberPrint", method = RequestMethod.POST)
    public Object logBackTest(HttpServletRequest req){
        String id = req.getParameter("id");
        String userId = req.getParameter("userId");
        History history = new History();
        history.setSongId(Integer.parseInt(id));
        history.setUserId(Integer.parseInt(userId));
        history.setPlayTime(new Date());

        History check = historyService.findDetail(history);
        // 如果这条历史记录存在，那么更新这条记录的时间并让播放次数增加
        if (check != null){
            int views = check.getViews()+1;
            history.setViews(views);
            history.setHistoryId(check.getHistoryId());
            historyService.updatePrint(history);
            log.info("用户"+check.getId()+"播放历史更新成功");
            return null;
        }else {
            boolean flag;
            try {
                history.setHistoryId(bizIdHelper.id());
                history.setViews(1);
                flag = historyService.insertPrint(history);
                if (!flag) {
                    log.error("用户播放历史记录失败");
                }
            } catch (Exception e) {
                System.out.println(e);
                log.error("用户播放历史记录失败");
            }
            return null;
        }
    }
    @RequestMapping(value = "/history/selectSongId",method = RequestMethod.POST)
    public Object getSongId(HttpServletRequest req){
        String userId = req.getParameter("userId");
        return historyService.selectSongIdByUserId(Integer.parseInt(userId));
    }

    /**
     * 用于返回歌曲历史信息
     * @param req
     * @return
     */
    @RequestMapping(value = "/history/getSong",method = RequestMethod.POST)
    public Object getSong(HttpServletRequest req){
        String userId = req.getParameter("userId");
        String id = req.getParameter("id");
        History finder = new History();
        finder.setSongId(Integer.parseInt(id));
        finder.setUserId(Integer.parseInt(userId));
        // 获取时间和播放次数
        History print = historyService.findDetail(finder);
        SongVo repVo = new SongVo();
        SimpleDateFormat a=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        // 获取歌曲信息
        List<Song> temp = songService.songOfId(Integer.parseInt(id));
        for (Song song:temp){
            repVo.setId(song.getId());
            repVo.setSingerId(song.getSingerId());
            repVo.setName(song.getName());
            repVo.setIntroduction(song.getIntroduction());
            repVo.setCreateTime(song.getCreateTime());
            repVo.setUpdateTime(song.getUpdateTime());
            repVo.setLyric(song.getLyric());
            repVo.setPic(song.getPic());
            repVo.setUrl(song.getUrl());
            repVo.setViews(print.getViews());
            repVo.setPlayTime(print.getPlayTime());
        }
        System.out.println(repVo.getViews());
        return repVo;
    }

}
