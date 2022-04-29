package com.luanxiede.bhtmusic.controller;

import com.alibaba.fastjson.JSONObject;
import com.luanxiede.bhtmusic.domain.Consumer;
import com.luanxiede.bhtmusic.service.impl.ConsumerServiceImpl;
import com.luanxiede.bhtmusic.utils.EmailHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@Controller
public class ConsumerController {

    @Autowired
    private ConsumerServiceImpl consumerService;
    @Autowired
    private EmailHelper emailHelper;
    @Autowired
    private RedisTemplate redisTemplate;
    //邮箱格式正则表达式
    private static String REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    //    添加用户
    @ResponseBody
    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public Object addUser(HttpServletRequest req){
        JSONObject jsonObject = new JSONObject();
        String username = req.getParameter("username").trim();
        String password = req.getParameter("password").trim();
        String sex = req.getParameter("sex").trim();
        String phone_num = req.getParameter("phone_num").trim();
        String email = req.getParameter("email").trim();
        String birth = req.getParameter("birth").trim();
        String introduction = req.getParameter("introduction").trim();
        String location = req.getParameter("location").trim();
        String avator = req.getParameter("avator").trim();
        if (username.equals("") || username == null || password.equals("") || password == null){
            jsonObject.put("code", 0);
            jsonObject.put("msg", "用户名或密码为空");
            return jsonObject;
        }
        Consumer consumer = new Consumer();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date myBirth = new Date();
        try {
            myBirth = dateFormat.parse(birth);
        } catch (Exception e){
            e.printStackTrace();
        }
        consumer.setUsername(username);
        consumer.setPassword(password);
        consumer.setSex(new Byte(sex));
        if (phone_num == "") {
            consumer.setPhoneNum(null);
        } else{
            consumer.setPhoneNum(phone_num);
        }

        if (email == "") {
            consumer.setEmail(null);
        } else{
            consumer.setEmail(email);
        }
        consumer.setBirth(myBirth);
        consumer.setIntroduction(introduction);
        consumer.setLocation(location);
        consumer.setAvator(avator);
        consumer.setCreateTime(new Date());
        consumer.setUpdateTime(new Date());
        // if (consumerService.checkRepeatItem(consumer) == 4)
        // 注册需要检查用户名手机号还有邮箱是否重复再进行数据库插入操作，否则会报错
        if (consumerService.checkRepeatItem(consumer) == 1){
            jsonObject.put("code", 0);
            jsonObject.put("msg", "注册失败,用户名重复");
            return  jsonObject;
        }
        if (consumerService.checkRepeatItem(consumer) == 2){
            jsonObject.put("code", 0);
            jsonObject.put("msg", "注册失败,该手机号已注册，请登录");
            return  jsonObject;
        }
        if (consumerService.checkRepeatItem(consumer) == 3){
            jsonObject.put("code", 0);
            jsonObject.put("msg", "注册失败,该邮箱已注册，请登录");
            return  jsonObject;
        }
        boolean res = false;
        try {
             res = consumerService.addUser(consumer);
        }catch (Exception e){
            System.out.println(e);
        }
            if (res) {
                jsonObject.put("code", 1);
                jsonObject.put("msg", "注册成功");
                return jsonObject;
            } else {
                jsonObject.put("code", 0);
                jsonObject.put("msg", "注册失败");
                return jsonObject;
            }
    }

//    判断是否登录成功（用户密码登录）
    @ResponseBody
    @RequestMapping(value = "/user/login/status", method = RequestMethod.POST)
    public Object loginStatus(HttpServletRequest req, HttpSession session){

        JSONObject jsonObject = new JSONObject();
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        boolean res = consumerService.veritypasswd(username, password);

        if (res){
            jsonObject.put("code", 1);
            jsonObject.put("msg", "登录成功");
            jsonObject.put("userMsg", consumerService.loginStatus(username));
            session.setAttribute("username", username);
            return jsonObject;
        }else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "用户名或密码错误");
            return jsonObject;
        }

    }

//  判断是否登录成功 （验证码登录）
    @ResponseBody
    @RequestMapping(value = "/user/verCodelogin/status", method = RequestMethod.POST)
    public Object VerCodeloginStatus(HttpServletRequest req, HttpSession session){

        JSONObject jsonObject = new JSONObject();
        String email = req.getParameter("email").trim();
        String verCode = req.getParameter("verCode").trim();
       // System.out.println(email);
        String value = null;
        try{
            value = (String)redisTemplate.opsForValue().get(email);
          //  System.out.println(value);
        }catch (Exception e){
            System.out.println(e);
        }
        if (value != null && value.equals(verCode)){
            String username = consumerService.findUsername(email);
            jsonObject.put("code", 1);
            jsonObject.put("msg", "登录成功");
            jsonObject.put("userMsg", consumerService.loginStatus(username));
            session.setAttribute("username", username);
            return jsonObject;
        }else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "验证码错误或验证码超时，请重新发送");
            return jsonObject;
        }
    }



//    返回所有用户
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Object allUser(){
        return consumerService.allUser();
    }

//    返回指定ID的用户
    @RequestMapping(value = "/user/detail", method = RequestMethod.GET)
    public Object userOfId(HttpServletRequest req){
        String id = req.getParameter("id");
        return consumerService.userOfId(Integer.parseInt(id));
    }

//    删除用户
    @RequestMapping(value = "/user/delete", method = RequestMethod.GET)
    public Object deleteUser(HttpServletRequest req){
        String id = req.getParameter("id");
        return consumerService.deleteUser(Integer.parseInt(id));
    }

//    更新用户信息
    @ResponseBody
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public Object updateUserMsg(HttpServletRequest req){
        JSONObject jsonObject = new JSONObject();
        String id = req.getParameter("id").trim();
        String username = req.getParameter("username").trim();
        String password = req.getParameter("password").trim();
        String sex = req.getParameter("sex").trim();
        String phone_num = req.getParameter("phone_num").trim();
        String email = req.getParameter("email").trim();
        String birth = req.getParameter("birth").trim();
        String introduction = req.getParameter("introduction").trim();
        String location = req.getParameter("location").trim();

        if (username.equals("") || username == null){
            jsonObject.put("code", 0);
            jsonObject.put("msg", "用户名或密码为空");
            return jsonObject;
        }
        Consumer consumer = new Consumer();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date myBirth = new Date();
        try {
            myBirth = dateFormat.parse(birth);
        }catch (Exception e){
            e.printStackTrace();
        }
        consumer.setId(Integer.parseInt(id));
        consumer.setUsername(username);
        consumer.setPassword(password);
        consumer.setSex(new Byte(sex));
        consumer.setPhoneNum(phone_num);
        consumer.setEmail(email);
        consumer.setBirth(myBirth);
        consumer.setIntroduction(introduction);
        consumer.setLocation(location);
//        consumer.setAvator(avator);
        consumer.setUpdateTime(new Date());

        boolean res = consumerService.updateUserMsg(consumer);
        if (res){
            jsonObject.put("code", 1);
            jsonObject.put("msg", "修改成功");
            return jsonObject;
        }else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "修改失败");
            return jsonObject;
        }
    }

//    更新用户头像
    @ResponseBody
    @RequestMapping(value = "/user/avatar/update", method = RequestMethod.POST)
    public Object updateUserPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id")int id){
        JSONObject jsonObject = new JSONObject();

        if (avatorFile.isEmpty()) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "文件上传失败！");
            return jsonObject;
        }
        String fileName = System.currentTimeMillis()+avatorFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "avatorImages" ;
        File file1 = new File(filePath);
        if (!file1.exists()){
            file1.mkdir();//如果目录不存在，创建一个
        }

        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storeAvatorPath = "/avatorImages/"+fileName;
        try {
            avatorFile.transferTo(dest);
            Consumer consumer = new Consumer();
            consumer.setId(id);
            consumer.setAvator(storeAvatorPath);
            boolean res = consumerService.updateUserAvator(consumer);
            if (res){
                jsonObject.put("code", 1);
                jsonObject.put("avator", storeAvatorPath);
                jsonObject.put("msg", "上传成功");
                return jsonObject;
            }else {
                jsonObject.put("code", 0);
                jsonObject.put("msg", "上传失败");
                return jsonObject;
            }
        }catch (IOException e){
            jsonObject.put("code", 0);
            jsonObject.put("msg", "上传失败"+e.getMessage());
            return jsonObject;
        }finally {
            return jsonObject;
        }
    }

    //发送邮件
    @ResponseBody
    @RequestMapping(value = "/user/sendEmail", method = RequestMethod.POST)
    public Object sendEmail(HttpServletRequest req){
        JSONObject jsonObject = new JSONObject();
        String email = req.getParameter("email").trim();
        if (!email.isEmpty()){
            if(!emailHelper.checkEmail(REGEX,email))
            {
                jsonObject.put("code", 0);
                jsonObject.put("msg", "邮箱格式不正确");
                return  jsonObject;
            }
        }
        Consumer consumer = new Consumer();
        consumer.setEmail(email);
        //如果此邮箱已注册，才发送邮件
        if (consumerService.checkRepeatItem(consumer) == 3){
            //
            if (emailHelper.sendVerCode(email) > 0){
                jsonObject.put("code", 1);
                jsonObject.put("msg", "验证码发送成功");
                return  jsonObject;
            }else {
                jsonObject.put("code", 0);
                jsonObject.put("msg", "验证码发送失败，请检查你的邮箱拼写");
                return  jsonObject;
            }
        }else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "此邮箱未注册，请先注册");
            return  jsonObject;
        }
    }
}
