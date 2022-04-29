package com.luanxiede.bhtmusic.service;



import com.luanxiede.bhtmusic.domain.Consumer;

import java.util.List;

public interface ConsumerService {

    boolean addUser(Consumer consumer);

    boolean updateUserMsg(Consumer consumer);

    boolean updateUserAvator(Consumer consumer);

    boolean existUser(Consumer consumer);

    boolean veritypasswd(String username, String password);

    boolean deleteUser(Integer id);

    int checkRepeatItem(Consumer consumer);

    String  findUsername(String email);

    List<Consumer> allUser();

    List<Consumer> userOfId(Integer id);

    List<Consumer> loginStatus(String username);

}
