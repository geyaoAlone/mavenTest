package com.yxf.contoller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yxf.dao.MyDao;
import com.yxf.dao.RedisDao;
import com.yxf.pojo.Blogs;
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
    @PostMapping("/commonSave.do")
    public String commonSave(@RequestBody JSONObject object){
        String collectionName = object.getString("collectionName");
        if(collectionName == null){
            return "失败";
        }
        object.remove("collectionName",collectionName);
        myDao.save(object,collectionName);
        return "成功";
    }
   /***登陆退出****/
   @PostMapping("/checkLogin.do")
   public String find(@RequestBody JSONObject object,HttpServletRequest request) {
       String usename = object.getString("usename");
       String passwd = object.getString("passwd");
       if(usename == null || passwd == null){
           return "0";
       }
       List<User> users = myDao.find(usename,passwd);
       String res = users.size() == 1 ? "1" : "0";
       if(res.equals("1")){
           redisDao.set(users.get(0).getUsename(),JSON.toJSONString(users.get(0)),1800);
       }
       return res;
   }

    @PostMapping("/loginOut.do")
    public String loginOut(@RequestBody JSONObject object) {
        String usename = object.getString("usename");
        redisDao.del(usename);
        return "1";
    }



    /***时间线***/
    @PostMapping("/findAllTimeline.do")
    public List<Timeline> findAllTimeline(@RequestBody JSONObject object) {
        return myDao.findAllTimeline();
    }
    @PostMapping("/saveTimeline.do")
    public String saveTimeline(@RequestBody JSONObject object,HttpServletRequest request) {
        String usename = object.getString("usename");
        User user = JSON.parseObject((String) redisDao.get(usename),User.class);
        if(user == null || !usename.equals(user.getUsename()) || !"0".equals(user.getUsetype())){
            return "抱歉，无权插入";
        }
        object.put("time",getNowTime());
        object.put("author",user.getUsename());
        Timeline timeline = JSON.parseObject(JSON.toJSONString(object),Timeline.class);
        if(!"1".equals(timeline.validate())){
            return timeline.validate();
        }
        myDao.saveTimeline(timeline);
        return "保存成功";
    }

    /***博客***/
    @PostMapping("/findAllBlogs.do")
    public JSONObject findAllBlogs(@RequestBody JSONObject object) {
        String usename = object.getString("usename");
        JSONObject res = new JSONObject();
        if(usename == null){
            res.put("dataList",myDao.findAllBlogs());
            return res;
        }
        User user = JSON.parseObject((String) redisDao.get(usename),User.class);
        if(user == null || !usename.equals(user.getUsename())){
            return null;
        }
        res.put("dataList",myDao.findBlogsByAuthor(usename));
        res.put("user",user);
        return res;
    }

    @PostMapping("/findBlogsById.do")
    public Blogs findBlogsById(@RequestBody JSONObject object) {
        String blogid = object.getString("blogid");
        if(blogid == null){
            return null;
        }
        return myDao.findBlogsById(blogid);
    }
    @PostMapping("/saveBlogs.do")
    public String saveBlogs(@RequestBody JSONObject object,HttpServletRequest request) {
        String usename = object.getString("usename");
        User user = JSON.parseObject((String) redisDao.get(usename),User.class);
        if(user == null || !usename.equals(user.getUsename())){
            return "抱歉，无权插入";
        }
        object.put("time",getNowTime());
        object.put("author",user.getUsename());
        object.put("blogid",getMajorKeyId("bg"));
        object.put("blogtype","1");
        object.put("ispublic",true);
        Blogs blogs = JSON.parseObject(JSON.toJSONString(object),Blogs.class);
        if(!"1".equals(blogs.validate())){
            return blogs.validate();
        }
        myDao.saveBlogs(blogs);
        return "保存成功";
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
