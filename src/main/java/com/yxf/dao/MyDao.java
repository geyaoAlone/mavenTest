package com.yxf.dao;

import com.yxf.pojo.CainiaoRes1;
import com.yxf.pojo.CainiaoRes2;
import com.yxf.pojo.CainiaoResDetail;
import com.yxf.pojo.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class MyDao {

    @Resource
    private MongoTemplate mongoTemplate;

    public void save(User user) {
        mongoTemplate.save(user);
    }
    public void saveCainiaoRes1(CainiaoRes1 cn1) {
        mongoTemplate.save(cn1);
    }
    public void saveCainiaoRes2(CainiaoRes2 cn2) {
        mongoTemplate.save(cn2);
    }
    public void saveCainiaoDetail(CainiaoResDetail cn2) {
        mongoTemplate.save(cn2);
    }
    public List<CainiaoRes1> findCainiaoRes1(String date) {
        Query query = new Query(Criteria.where("date").is(date));
        return mongoTemplate.find(query, CainiaoRes1.class);
    }
    public List<CainiaoRes2> findCainiaoRes2(String date,String agentId) {
        Query query = new Query(Criteria.where("date").is(date).and("userId").is(agentId));
        return mongoTemplate.find(query, CainiaoRes2.class);
    }

    public List<User> find(String name) {
        Query query = new Query(Criteria.where("name").is(name));
        return mongoTemplate.find(query, User.class);
    }

    public List<User> find(String name, String passwd) {
        Query query = new Query(Criteria.where("name").is(name).and("passwd").is(passwd));
        return mongoTemplate.find(query, User.class);
    }
    public List<CainiaoResDetail> findDetail() {
        return mongoTemplate.findAll(CainiaoResDetail.class);
    }
}
