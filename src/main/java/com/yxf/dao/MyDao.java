package com.yxf.dao;

import com.alibaba.fastjson.JSONObject;
import com.yxf.pojo.Blogs;
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

    /***通用***/
    public List<JSONObject> queryJSONObject(String collectionName){
        return mongoTemplate.find(new Query(),JSONObject.class,collectionName);
    }
    public void save(JSONObject object,String collectionName){
        mongoTemplate.save(object,collectionName);
    }

    /***博客查询***/
    public List<Blogs> findAllBlogs() {
        Query query = new Query(Criteria.where("ispublic").is(true));
        query.with(new Sort(Sort.Direction.DESC,"time"));
        return mongoTemplate.find(query,Blogs.class);
    }
    public Blogs findBlogsById(String blogid) {
        return mongoTemplate.find(new Query(Criteria.where("blogid").is(blogid)),Blogs.class).get(0);
    }
    public List<Blogs> findBlogsByAuthor(String author) {
        Query query = new Query(Criteria.where("author").is(author));
        query.with(new Sort(Sort.Direction.DESC,"time"));
        return mongoTemplate.find(query,Blogs.class);
    }
    public void saveBlogs(Blogs Blogs) {
        mongoTemplate.save(Blogs);
    }



    /***时间线***/
    public void saveTimeline(Timeline timeline) {
        mongoTemplate.save(timeline);
    }

    public List<Timeline> findAllTimeline() {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC,"time"));
        return mongoTemplate.find(query,Timeline.class);
    }

    /***用户***/
    public List<User> findUser(User user) {
        Query query = new Query(Criteria.where("usename").is(user.getUsename()).and("passwd").is(user.getPasswd()));
        return mongoTemplate.find(query, User.class);
    }
    public List<User> findUserByUseid(String useid) {
        Query query = new Query(Criteria.where("useid").is(useid));
        return mongoTemplate.find(query, User.class);
    }
    public List<User> find(String usename) {
        Query query = new Query(Criteria.where("usename").is(usename));
        return mongoTemplate.findAll(User.class);
    }
    public List<User> find(String usename, String passwd) {
        Query query = new Query(Criteria.where("usename").is(usename).and("passwd").is(passwd));
        return mongoTemplate.find(query, User.class);
    }



}
