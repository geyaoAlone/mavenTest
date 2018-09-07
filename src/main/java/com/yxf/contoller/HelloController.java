package com.yxf.contoller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yxf.dao.MyDao;
import com.yxf.dao.RedisDao;
import com.yxf.pojo.Timeline;
import com.yxf.pojo.User;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin
public class HelloController {

    @Resource
    MyDao myDao;
    @Resource
    RedisDao redisDao;

   /* @GetMapping("/find.do")
    public User find(@RequestParam("name") String name, @RequestParam("passwd") String passwd) {
        List<User> users = Optional.ofNullable(myDao.find(name, passwd)).orElse(Collections.singletonList(new User()));
        return users.size() > 0 ? users.get(0) : null;
    }*/
    private static HashMap<String,User> loginInfo = new HashMap<String,User>();
    @PostMapping("/findAllTimeline.do")
    public JSONObject findAllTimeline(@RequestBody JSONObject object) {
        String name = object.getString("name");
        System.out.println(name);
        JSONObject res = new JSONObject();
        if(name == null){
            res.put("dataList",myDao.findAllTimeline());
            return res;
        }
        User user = loginInfo.get(name);
        if(user == null || !name.equals(user.getName())){
            return null;
        }
        res.put("dataList",myDao.findTimelineByName(name));
        res.put("checkData",user.getPasswd());
        System.out.println(res);
        return res;
    }
    @PostMapping("/saveTimeline.do")
    public String saveTimeline(@RequestBody JSONObject object,HttpServletRequest request) {
        String name = object.getString("name");
        User user = loginInfo.get(name);
        if(user == null || !name.equals(user.getName())){
            return "抱歉，无权插入";
        }
        object.put("time",getNowTime());
        object.put("author",user.getName());
        Timeline tl = JSON.parseObject(JSON.toJSONString(object),Timeline.class);
        if(!"1".equals(tl.validate())){
            return tl.validate();
        }
        myDao.saveTimeline(tl);
        return "保存成功";
    }
    private static String getNowTime() {
        Date nowTime = new Date();
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sysDate = time.format(nowTime);
        return sysDate;
    }
   @PostMapping("/checkLogin.do")
    public String find(@RequestBody JSONObject object,HttpServletRequest request) {
        User user = JSON.parseObject(JSON.toJSONString(object),User.class);
        List<User> users = Optional.ofNullable(myDao.findUser(user)).orElse(Collections.singletonList(new User()));
        String res = users.size() == 1 ? "1" : "0";
        if(res.equals("1")){
            loginInfo.put(user.getName(),users.get(0));
            System.out.println(user.getName()+"登录成功"+loginInfo);
        }
       return res;
    }

    @PostMapping("/loginOut.do")
    public String loginOut(@RequestBody JSONObject object) {
        String name = object.getString("name");
        loginInfo.put(name,null);
        System.out.println(name+"退出登录以后"+loginInfo);
        return "1";
    }
    @GetMapping("/setRedis.do")
    public boolean setRedis(@RequestParam("key") String key,@RequestParam("value") String value ){
        return redisDao.set(key,value);
    }
    @GetMapping("/getRedis.do")
    public String getRedis(@RequestParam("key") String key){
        return (String)redisDao.get(key);
    }
    @GetMapping("/delRedis.do")
    public String delRedis(@RequestParam("key") String key){
        redisDao.del(key);
        return "1";
    }

/*

    @PostMapping("/saveJSONObject.do")
    public String save(@RequestBody JSONObject object, HttpServletRequest request, HttpServletResponse response) {
        //myDao.save(object);
        return "success";
    }
    @PostMapping("/save.do")
    public String saveObj(@RequestBody CainiaoRes2 object, HttpServletRequest request, HttpServletResponse response) {
        myDao.saveCainiaoRes2(object);
        return "success";
    }

    @PostMapping("/saveDetail.do")
    public String saveDetail(@RequestBody CainiaoResDetail object, HttpServletRequest request, HttpServletResponse response) {
        myDao.saveCainiaoDetail(object);
        return "success";
    }



    *//**
     *
     * @param request
     * @param response
     * @return
     *//*
    @PostMapping("/look.do")
    public String look(HttpServletRequest request, HttpServletResponse response) {
        String logistics_interface = request.getParameter("logistics_interface");
        String src = "";
        try {
            src = EncryptUtil.decrypt(key,logistics_interface,"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(src);

        return "success";
    }*/
}
