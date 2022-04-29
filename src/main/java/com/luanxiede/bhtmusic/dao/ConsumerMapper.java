package com.luanxiede.bhtmusic.dao;


import com.luanxiede.bhtmusic.domain.Consumer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsumerMapper {

    int insert(Consumer record);

    int insertSelective(Consumer record);

    int updateByPrimaryKey(Consumer record);

    int verifyPassword(String username, String password);

    int existUsername(Consumer record);

    int existPhoneNum(String phone_num);

    int existEmail(Consumer record);

    int updateUserMsg(Consumer record);

    int updateUserAvator(Consumer record);

    String  findUsername(String email);

    int deleteUser(Integer id);

    List<Consumer> allUser();

    List<Consumer> userOfId(Integer id);

    List<Consumer> loginStatus(String username);

}
