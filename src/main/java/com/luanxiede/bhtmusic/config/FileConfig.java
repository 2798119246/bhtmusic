package com.luanxiede.bhtmusic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author wangdeyun
 * @date  2022/3/23 18:17
 * 此配置类用于进行文件的磁盘映射
 */
@Configuration
public class FileConfig implements WebMvcConfigurer {
    // TODO 更新歌手图片时应该把原来的图片一并删除了，同样的，删除歌手歌单的同时也要把对应的图片给删除了
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 用户头像地址
        registry.addResourceHandler("/avatorImages/**").addResourceLocations(
                "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")+"avatorImages"+System.getProperty("file.separator")
        );
        // 歌手头像地址
        registry.addResourceHandler("/img/singerPic/**").addResourceLocations(
                "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
                        +System.getProperty("file.separator")+"singerPic"+System.getProperty("file.separator")
        );
        // 歌曲图片地址
        registry.addResourceHandler("/img/songPic/**").addResourceLocations(
                "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
                        +System.getProperty("file.separator")+"songPic"+System.getProperty("file.separator")
        );
        // 歌曲地址
        registry.addResourceHandler("/song/**").addResourceLocations(
                "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")+"song"
                        +System.getProperty("file.separator")
        );
        // 歌单图片地址
        registry.addResourceHandler("/img/songListPic/**").addResourceLocations(
                "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
                        +System.getProperty("file.separator")+"songListPic"+System.getProperty("file.separator")
        );
    }

}
