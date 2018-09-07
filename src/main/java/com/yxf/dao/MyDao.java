package com.yxf.dao;

import com.alibaba.fastjson.JSONObject;
import com.yxf.pojo.Timeline;
import com.yxf.pojo.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class MyDao {

    @Resource
    private MongoTemplate mongoTemplate;


    public void saveTimeline(Timeline timeline) {
        mongoTemplate.save(timeline);
    }

    public List<Timeline> findAllTimeline() {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC,"time"));
        return mongoTemplate.find(query,Timeline.class);
    }
    public List<Timeline> findTimelineByName(String name) {
        Query query = new Query(Criteria.where("author").is(name));
        query.with(new Sort(Sort.Direction.DESC,"time"));
        return mongoTemplate.find(query,Timeline.class);
    }
    public List<User> findUser(User user) {
        Query query = new Query(Criteria.where("name").is(user.getName()).and("passwd").is(user.getPasswd()));
        return mongoTemplate.find(query, User.class);
    }

    public List<User> find(String name) {
        Query query = new Query(Criteria.where("name").is(name));
        //return mongoTemplate.find(query, User.class);
        return mongoTemplate.findAll(User.class);
    }

    public List<User> find(String name, String passwd) {
        Query query = new Query(Criteria.where("name").is(name).and("passwd").is(passwd));
        return mongoTemplate.find(query, User.class);
    }


}
