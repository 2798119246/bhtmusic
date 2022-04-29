package com.luanxiede.bhtmusic.service.impl;


import com.luanxiede.bhtmusic.dao.ConsumerMapper;
import com.luanxiede.bhtmusic.domain.Consumer;
import com.luanxiede.bhtmusic.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumerServiceImpl implements ConsumerService {

    @Autowired
    private ConsumerMapper consumerMapper;

    @Override
    public boolean addUser(Consumer consumer) {
        return consumerMapper.insertSelective(consumer) >0 ?true:false;
    }

    @Override
    public boolean updateUserMsg(Consumer consumer) {
        return consumerMapper.updateUserMsg(consumer) >0 ?true:false;
    }

    @Override
    public boolean updateUserAvator(Consumer consumer) {

        return consumerMapper.updateUserAvator(consumer) >0 ?true:false;
    }

    @Override
    public boolean existUser(Consumer consumer) {
        return consumerMapper.existUsername(consumer)>0 ? true:false;
    }

    @Override
    public boolean veritypasswd(String username, String password) {

        return consumerMapper.verifyPassword(username, password)>0?true:false;
    }

//    删除用户
    @Override
    public boolean deleteUser(Integer id) {
        return consumerMapper.deleteUser(id) >0 ?true:false;
    }

/*    核验邮箱手机号用户名是否重复
* 1为用户名重复 ，2 为手机号重复 3 为邮箱重复 4为正常可以注册
* */
    @Override
    public int checkRepeatItem(Consumer consumer) {
        String phone_num = consumer.getPhoneNum();
        if (consumerMapper.existUsername(consumer)>0)
            return 1;
        else if (consumerMapper.existPhoneNum(phone_num)>0)
            return 2;
        else if (consumerMapper.existEmail(consumer)>0)
            return 3;
        else return 4;
    }

    @Override
    public String findUsername(String email) {
        return consumerMapper.findUsername(email);
    }

    @Override
    public List<Consumer> allUser() {
        return consumerMapper.allUser();
    }

    @Override
    public List<Consumer> userOfId(Integer id) {

        return consumerMapper.userOfId(id);
    }

    @Override
    public List<Consumer> loginStatus(String username) {

        return consumerMapper.loginStatus(username);
    }
}
