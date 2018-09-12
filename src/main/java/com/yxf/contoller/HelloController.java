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
    @PostMapping("/findAllTimeline.do")
    public JSONObject findAllTimeline(@RequestBody JSONObject object) {
        String name = object.getString("name");
        JSONObject res = new JSONObject();
        if(name == null){
            res.put("dataList",myDao.findAllTimeline());
            return res;
        }
        User user = JSON.parseObject((String) redisDao.get(name),User.class);
        if(user == null || !name.equals(user.getName())){
            return null;
        }
        res.put("dataList",myDao.findTimelineByName(name));
        res.put("checkData",user.getPasswd());
        return res;
    }
    @PostMapping("/saveTimeline.do")
    public String saveTimeline(@RequestBody JSONObject object,HttpServletRequest request) {
        String name = object.getString("name");
        User user = JSON.parseObject((String) redisDao.get(name),User.class);
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

   @PostMapping("/checkLogin.do")
    public String find(@RequestBody JSONObject object,HttpServletRequest request) {
        User user = JSON.parseObject(JSON.toJSONString(object),User.class);
        List<User> users = Optional.ofNullable(myDao.findUser(user)).orElse(Collections.singletonList(new User()));
        String res = users.size() == 1 ? "1" : "0";
        if(res.equals("1")){
             redisDao.set(user.getName(),JSON.toJSONString(users.get(0)),1800);
        }
       return res;
    }

    @PostMapping("/loginOut.do")
    public String loginOut(@RequestBody JSONObject object) {
        String name = object.getString("name");
        redisDao.del(name);
        return "1";
    }
    @GetMapping("/queryJSONObject.do")
    public List<JSONObject> queryJSONObject(){
        return myDao.queryJSONObject();
    }
    /*
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

    /*****************工具类*********************/

    private static String getNowTime() {
        Date nowTime = new Date();
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sysDate = time.format(nowTime);
        return sysDate;
    }
    static String str = "0000";
    private String getMajorKeyId(String type){
        String id = (String)redisDao.get("major_key_id");
        Date nowTime = new Date();
        SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmmss");
        String sysDate = time.format(nowTime);
        int p = Integer.parseInt(str) + 1;
        if(p > 9999) {
            p = 0;
        }
        str = String.format("%04d",p);
        id = type+sysDate+str;
        redisDao.set("major_key_id",id);
        return id;
    }
    @GetMapping("/testId.do")
    public List<String> testId(){
        List<String> list = new ArrayList<String>();
        for (int i=0;i< 10;i++){
            list.add(getMajorKeyId("gy"));
        }
      return list;

    }


}
