package com.yxf.contoller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yxf.dao.MyDao;
import com.yxf.pojo.CainiaoRes1;
import com.yxf.pojo.CainiaoRes2;
import com.yxf.pojo.CainiaoResDetail;
import com.yxf.pojo.User;
import com.yxf.utils.EncryptUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class HelloController {

    private static final String key = "c7LHz5oPS/oEoX+TkYHsKA==";
    @GetMapping("/hello/{name}")
    public String hello(@PathVariable("name") String name) {
        System.out.println("hello" + name);
        return "hello world " + name;
    }

    @Resource
    MyDao myDao;

    @GetMapping("/find.do")
    public User find(@RequestParam("name") String name, @RequestParam("passwd") String passwd) {
        List<User> users = Optional.ofNullable(myDao.find(name, passwd)).orElse(Collections.singletonList(new User()));
        return users.size() > 0 ? users.get(0) : null;
    }

    @GetMapping("/save/{name}/{passwd}")
    public String save(@PathVariable("name") String name2, @PathVariable("passwd") String passwd) {
        myDao.save(new User().setName(name2).setPasswd(passwd));
        return "hello world " + name2;
    }

    @GetMapping("/find/{name}")
    public User find(@PathVariable("name") String name2) {
        System.out.println(myDao.find("gy1"));
        List<User> users = Optional.ofNullable(myDao.find(name2)).orElse(Collections.singletonList(new User()));

        return users.size() > 0 ? users.get(0) : null;
    }

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
    @PostMapping("/findCn.do")
    public JSONObject findCn(HttpServletRequest request, HttpServletResponse response) {
        String logistics_interface = request.getParameter("logistics_interface");
        String src = "";
        try {
            src = EncryptUtil.decrypt(key,logistics_interface,"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject obj = JSON.parseObject(src);
        String date = obj.get("date").toString();
        System.out.println(date);
        List<CainiaoRes1> users = Optional.ofNullable(myDao.findCainiaoRes1(date)).orElse(Collections.singletonList(new CainiaoRes1()));
        CainiaoRes1 res1 = users.size() > 0 ? users.get(0) : null;
        String data = "";
        try {
            data = EncryptUtil.encrypt(key,JSON.toJSONString(res1),"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject resObj = new JSONObject();
        resObj.put("data",data);
        return resObj;
    }
    @PostMapping("/findCnDetail.do")
    public JSONObject findCnDetail(HttpServletRequest request, HttpServletResponse response) {
        String logistics_interface = request.getParameter("logistics_interface");
        String src = "";
        try {
            src = EncryptUtil.decrypt(key,logistics_interface,"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject obj = JSON.parseObject(src);
        System.out.println(JSON.toJSONString(obj));
        String date = obj.get("date").toString();
        String userId = obj.get("userId").toString();
        System.out.println(userId);
        List<CainiaoRes2> users = Optional.ofNullable(myDao.findCainiaoRes2(date,userId)).orElse(Collections.singletonList(new CainiaoRes2()));
        CainiaoRes2 res1 = users.size() > 0 ? users.get(0) : null;
        if(res1 ==null){
            JSONObject resObj = new JSONObject();
            resObj.put("data","");
            return resObj;
        }
        List<CainiaoResDetail> detailList = myDao.findDetail();
        res1.setDataList(detailList);
        String data = "";
        try {
            data = EncryptUtil.encrypt(key,JSON.toJSONString(res1),"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject resObj = new JSONObject();
        resObj.put("data",data);
        return resObj;
    }
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
    }
}
