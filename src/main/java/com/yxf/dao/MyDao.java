package com.yxf.dao;

import com.alibaba.fastjson.JSONObject;
import com.yxf.pojo.Blogs;
import com.yxf.pojo.Comments;
import com.yxf.pojo.Timeline;
import com.yxf.pojo.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public void updateBlogSupport(String blogid,int support){
        mongoTemplate.updateFirst(new Query(Criteria.where("blogid").is(blogid)),Update.update("support",support),Blogs.class);
    }

    public void updateBlogViewCount(String blogid,int viewCount){
        mongoTemplate.updateFirst(new Query(Criteria.where("blogid").is(blogid)),Update.update("viewCount",viewCount),Blogs.class);
    }
    /***留言处理***/
    public void saveComments(Comments comment){
        mongoTemplate.save(comment);
    }

    public List<Comments> findComments(JSONObject object) {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC,"time"));
        Set<Map.Entry<String,Object>> s = object.entrySet();
        for (Map.Entry<String, Object> entry : s) {
            query.addCriteria(Criteria.where(entry.getKey()).is(entry.getValue()));
        }
        return mongoTemplate.find(query,Comments.class);
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
    public List<User> findUser(String usename) {
        Query query = new Query(Criteria.where("usename").is(usename));
        return mongoTemplate.findAll(User.class);
    }
    public List<User> findUser(String usename, String passwd) {
        Query query = new Query(Criteria.where("usename").is(usename).and("passwd").is(passwd));
        return mongoTemplate.find(query, User.class);
    }



}
