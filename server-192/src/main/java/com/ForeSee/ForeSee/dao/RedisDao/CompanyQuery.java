package com.ForeSee.ForeSee.dao.RedisDao;

import com.ForeSee.ForeSee.util.*;
import com.ForeSee.ForeSee.dao.RedisDao.*;
<<<<<<< HEAD
import com.ForeSee.ForeSee.dao.*;
=======
>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
//import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
<<<<<<< HEAD
import com.mongodb.MongoClient;
import org.json.JSONObject;
import com.ForeSee.ForeSee.dao.MongoDBDao.*;
import org.bson.Document;
import java.util.Arrays;
=======
>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270

/**
 * @author zhongshsh
 * @ClassName CompanyQuery
 * @Description 关于行业信息的相关检索
<<<<<<< HEAD
=======
 * @create 2021-03-02
>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270
 */

@Slf4j
@Component
public class CompanyQuery {

    @Autowired
    JedisUtil jedisUtil;

    //模糊查询
    @Autowired
    FuzzySearch fz;

    //记录时间，用于测试
    long startTime;
    long finishTime;

<<<<<<< HEAD
    //http访问flask
    @Autowired
    HttpDao httpDao;

    private int sortNum = 50;


    /**
     * 组合query company 策略的方法
     * @param query
     * @return stockList
     */
    public List<String> queryService(String query, MongoClient mongoClient) {
        // redis查询返回stockCodeList
        List<String> stockCodes = getStockCodes(query, mongoClient);
        log.info("stock: " + stockCodes.toString());
        // 如果检索不到结果
        if (stockCodes.size() == 0) {
            stockCodes = getStockCodesOnlyVec(query);
            stockCodes.add(0,"true");
        } else {
            stockCodes.add(0,"false");
        }
        return stockCodes;
    }


    /**
     * 如果核心词检索不到，调用该方法直接使用向量，返回所有检索字段对应检索结果的stockCode列表
     * @param query
     * @return stockList
     */
    public List<String> getStockCodesOnlyVec(String query)
    {
        String info = "{\"query\":\"" + query + "\", " + "\"type\":\"stock\" }";
        log.info(info);
        String res = httpDao.sortIds(info);
        List<String> ids = new ArrayList<>();
        ids.addAll(Arrays.asList(res.split(" ")));
        List<String> result = new ArrayList<String>(new LinkedHashSet<String>(ids)); 
        return result;
    }


    /**
     * 早期版本，根据传入的query进行切词，并标题模糊匹配，返回所有检索字段对应检索结果的stockCode列表，没有进行精排，该函数是在其他接口无结果时返回企业倒推结果
     * @param query
     * @return stockList
     */
    public List<String> getStockCodeForOthers(String query, MongoClient mongoClient)
    {
        JSONObject jsonObject = new JSONObject();
        List<String> res = new ArrayList<>();
        // 实体提取
        try {
            String jsonResult = httpDao.getEntities(query, "stock");
            jsonObject =  new JSONObject(jsonResult);
            if (jsonObject.getString("core_ent").length() > 0) {
                query = jsonObject.getString("core_ent") + " " + jsonObject.getString("norm_ent");
            } else if (jsonObject.getString("norm_ent").length() > 0){
                query = jsonObject.getString("norm_ent");
            } else {
                query = jsonObject.getString("non_ent");
            }
            query = query.trim().replace("  ", " ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 模糊匹配
        if (query.length() == 0) return res;
        String queries[] = query.split(" ");
        int runSize = queries.length;
        //不适用多线程，因为每个词次序代表了重要性
        for(int i = 0; i < runSize; i++)
        {
            String key = queries[i];
            res.addAll(fz.FuzzySearchList(key, 1));
        }
        //内容匹配
        try {
            query = jsonObject.getString("core_ent") + " " + jsonObject.getString("norm_ent") + " " + jsonObject.getString("non_ent");
            query = query.trim().replace("  ", " ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<String> fcIds = getCompanyByContent(query);
        res.addAll(fcIds);
        res.remove("");
        //去重（顺序不变）
        List<String> result = new ArrayList<String>(new LinkedHashSet<String>(res));         
        return result; 
    }

    /**
     * 根据传入的query进行切词，并标题模糊匹配，返回所有检索字段对应检索结果的stockCode列表
     * @param query
     * @return stockList
     */
    public List<String> getStockCodes(String query, MongoClient mongoClient)
    {
        JSONObject jsonObject = new JSONObject();
        List<String> res = new ArrayList<>();
        // 实体提取
        try {
            String jsonResult = httpDao.getEntities(query, "stock");
            jsonObject =  new JSONObject(jsonResult);
            if (jsonObject.getString("core_ent").length() > 0) {
                query = jsonObject.getString("core_ent") + " " + jsonObject.getString("norm_ent");
            } else if (jsonObject.getString("norm_ent").length() > 0) {
                // query = jsonObject.getString("norm_ent");
                query = "";
            } else {
                query = "";
            }
            query = query.trim().replace("  ", " ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 模糊匹配
        if (query.length() == 0) return res;
        String queries[] = query.split(" ");
        int runSize = queries.length;
        //不适用多线程，因为每个词次序代表了重要性
        for(int i = 0; i < runSize; i++)
        {
            String key = queries[i];
            res.addAll(fz.FuzzySearchList(key, 1));
        }
        //内容匹配
        try {
            query = jsonObject.getString("core_ent") + " " + jsonObject.getString("norm_ent") + " " + jsonObject.getString("non_ent");
            query = query.trim().replace("  ", " ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<String> fcIds = getCompanyByContent(query);
        
        res.addAll(fcIds);
        res.remove("");
        //去重（顺序不变）
        List<String> result = new ArrayList<String>(new LinkedHashSet<String>(res)); 
        return result;
    }
=======
>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270
    
    /**
     * 根据传入的query进行切词，返回所有检索字段对应检索结果的stockCode列表
     * @param query
     * @return stockList
     */
<<<<<<< HEAD
    public List<String> getCompanyByContent(String query)
    {
        List<String> res = new ArrayList<>();
        if (query.length() == 0) return res;
=======
    public List<String> getStockCodes(String query)
    {
>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270
        startTime = System.currentTimeMillis();
        //对检索词串进行切词
        String queries[] = query.split(" ");
        int runSize = queries.length;
<<<<<<< HEAD
        Jedis jedis = jedisUtil.getClient();
        //不适用多线程，因为每个词次序代表了重要性
        //进行词匹配
        jedis.select(13);
        for(int i = 0; i < runSize; i++)
        {
            String key = queries[i];
            try {
                res.addAll(fz.FuzzySearchList(key, 1));
                if(jedis.exists(key)){
                    res.addAll(jedis.smembers(key));
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        jedis.close();
        jedis = null;
        List<String> result = new ArrayList<String>(new LinkedHashSet<String>(res)); //去重（顺序不变）
        finishTime = System.currentTimeMillis();
        log.info("RedisDao getStockCodes process time:" + (finishTime - startTime));
        return result;
    }

    /**
     * 根据传入的query进行切词，返回所有检索字段对应检索结果的stockCode列表，无模糊匹配
     * @param query
     * @return stockList
     */
    public List<String> getSomeStockCodes(String query)
    {
        List<String> res = new ArrayList<>();
        if (query.length() == 0) return res;
        startTime = System.currentTimeMillis();
        //对检索词串进行切词
        String queries[] = query.split(" ");
        int runSize = queries.length;
        Jedis jedis = jedisUtil.getClient();
        //不适用多线程，因为每个词次序代表了重要性
        //进行词匹配
        jedis.select(13);
        for(int i = 0; i < runSize; i++)
        {
            String key = queries[i];
            try {
                if(jedis.exists(key)){
                    res.addAll(jedis.smembers(key));
                    // log.info("DB 13: "+key+"; result: "+jedis.smembers(key));
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        jedis.close();
        jedis = null;
        List<String> result = new ArrayList<String>(new LinkedHashSet<String>(res)); //去重（顺序不变）
        finishTime = System.currentTimeMillis();
        log.info("RedisDao getStockCodes process time:" + (finishTime - startTime));
        return result; 
    }

=======
        //指定线程池大小为检索词数目
        ExecutorService executor = Executors.newFixedThreadPool(runSize);
        //countDownLatch这个类使一个线程等待其他线程各自执行完毕后再执行
        final CountDownLatch latch = new CountDownLatch(runSize);
        List<String> res = new ArrayList<>();

        //对每一个检索词用一个线程执行查询
        for(int i = 0; i < runSize; i++)
        {
            String key = queries[i];
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        //jedis连接在方法外面获取时，好像不能成功，所以放在了里面，存疑
                        Jedis jedis = jedisUtil.getClient();
                        jedis.select(1);
                        if(jedis.exists(key)){
                            res.addAll(jedis.smembers(key));
                            log.info("DB 1: "+key+"; result: "+jedis.smembers(key));
                        } else {
                            //模糊匹配
                            res.addAll(fz.FuzzySearchList(key, 1));
                            //进行词匹配
                            jedis.select(13);
                            if(jedis.exists(key)){
                                res.addAll(jedis.smembers(key));
                                log.info("DB 13: "+key+"; result: "+jedis.smembers(key));
                            }
                        }
                        jedis.close();
                        jedis = null;
                    } catch (Exception e){
                        System.out.println("Error in RedisDao getStockCodes");
                        e.printStackTrace();
                    }
                    //计数器-1操作
                    latch.countDown();
                }
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
        List<String> result = new ArrayList<String>(new LinkedHashSet<String>(res)); //去重（顺序不变）
        finishTime = System.currentTimeMillis();
        log.info("RedisDao getStockCodes process time:" + (finishTime - startTime));

        return result; 
    }

    
>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270
    /**
     * 根据传入的industryCode/industryName，返回stockCode列表，用于InfoService的getNews
     * @param industryCode
     * @return stockList
     */
    public List<String> getStockCodeBasedIndustry(String industryCode)
    {
        startTime = System.currentTimeMillis();
        Jedis jedis= jedisUtil.getClient();
        List<String> res = new ArrayList<>();
        try {
            jedis.select(1);
            if(jedis.exists(industryCode)){
                res.addAll(jedis.smembers(industryCode));
<<<<<<< HEAD
                // log.info("DB 1:  "+industryCode+" ; result: "+jedis.smembers(industryCode));
            }
            
        } catch (Exception e){
=======
                log.info("DB 1:  "+industryCode+" ; result: "+jedis.smembers(industryCode));
            }
            
        } catch (Exception e){
            System.out.println("Error in RedisDao getStockCodeBasedIndustry");
>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270
            e.printStackTrace();
        }
        jedis.close();
        jedis = null;
        List<String> result = new ArrayList<String>(new LinkedHashSet<String>(res)); //去重（顺序不变）
        finishTime = System.currentTimeMillis();
        log.info("RedisDao getStockCodeBasedIndustry process time:" + (finishTime - startTime));
        return result;  
    }

<<<<<<< HEAD
=======

>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270
}
