package com.luanxiede.bhtmusic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author passer-by
 * @date 2022/3/23
 */
@Configuration
public class FileConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/avatorImages/**").addResourceLocations("file:C:\\Users\\passerby\\Desktop\\music-website-master\\music-server\\avatorImages\\");
        registry.addResourceHandler("/img/singerPic/**").addResourceLocations("file:C:\\Users\\passerby\\Desktop\\music-website-master\\music-server\\img\\singerPic\\");
        registry.addResourceHandler("/img/songPic/**").addResourceLocations("file:C:\\Users\\passerby\\Desktop\\music-website-master\\music-server\\img\\songPic\\");
        registry.addResourceHandler("/song/**").addResourceLocations("file:C:\\Users\\passerby\\Desktop\\music-website-master\\music-server\\song\\");
        registry.addResourceHandler("/img/songListPic/**").addResourceLocations("file:C:\\Users\\passerby\\Desktop\\music-website-master\\music-server\\img\\songListPic\\");
    }

}
