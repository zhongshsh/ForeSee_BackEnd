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
import org.json.JSONObject;
=======

>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270

/**
 * @author zhongshsh
 * @ClassName ReportQuery
 * @Description 关于资讯的检索
<<<<<<< HEAD
=======
 * @create 2021-03-02
>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270
 */


@Slf4j
@Component
public class ReportQuery {

<<<<<<< HEAD
    //模糊查询
    @Autowired
    FuzzySearch fz;

    @Autowired
    JedisUtil jedisUtil;

    @Autowired
    HttpDao httpDao;
=======
    @Autowired
    JedisUtil jedisUtil;

    // @Autowired
    JedisUtil_105 jedisUtil_105 = new JedisUtil_105();

>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270

    //记录时间，用于测试
    long startTime;
    long finishTime;

    /**
     * 根据传入的query，返回reportId列表
     * @param words
     * @return newsIdList
     */
    public List<String> getReportIds(String query)
    {
        startTime = System.currentTimeMillis();
<<<<<<< HEAD

        try{ // 实体提取
            String jsonResult = httpDao.getEntities(query, "report");
            JSONObject jsonObject =  new JSONObject(jsonResult);
            query = jsonObject.getString("core_ent") + " " + jsonObject.getString("norm_ent") + " " + jsonObject.getString("non_ent");
            query = query.trim().replace("  ", " ");
        } catch (Exception e) {
            e.printStackTrace();
        }

=======
>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270
        Jedis jedis= jedisUtil.getClient();
        List<String> res = new ArrayList<>();
        //对检索词串进行切词
        String queries[] = query.split(" ");
        int runSize = queries.length;
        for(int i = 0; i < runSize; i++)
        {
            String key = queries[i];
<<<<<<< HEAD
            //模糊匹配
            res.addAll(fz.FuzzySearchList(key, 10));
        }
        for(int i = 0; i < runSize; i++)
        {
            String key = queries[i];
=======
>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270
            try {
                jedis.select(12);
                if(jedis.exists(key)){
                    res.addAll(jedis.smembers(key));
<<<<<<< HEAD
                    // log.info("DB 12: "+key+"; result: "+jedis.smembers(key));
=======
                    log.info("DB 12: "+key+"; result: "+jedis.smembers(key));
>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270
                }
            } catch (Exception e){
                System.out.println("Error in RedisDao getReportIds");
                e.printStackTrace();
            }
        }
        jedis.close();
        jedis = null;
        List<String> result = new ArrayList<String>(new LinkedHashSet<String>(res)); //去重（顺序不变）
        finishTime = System.currentTimeMillis();
        log.info("RedisDao getReportIds process time:" + (finishTime - startTime));
        return result;  
    }

}