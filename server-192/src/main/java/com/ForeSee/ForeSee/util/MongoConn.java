package com.ForeSee.ForeSee.util;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
<<<<<<< HEAD
import com.mongodb.MongoClientOptions;

/**
 * @author Zhongshsh
 * @ClassName MongoConn
 * @Description Mongo连接
 */

@Slf4j
// @Configuration
=======

@Slf4j
>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270
public class MongoConn {
    /**
     * @return Mongodb的连接
     */
    public static MongoClient getConn(){
        MongoClient mongoClient = null;
<<<<<<< HEAD
        MongoClientOptions.Builder build = new MongoClientOptions.Builder();        
        build.connectionsPerHost(50);   
        build.threadsAllowedToBlockForConnectionMultiplier(50); 
        build.maxWaitTime(1000*60*2);
        build.connectTimeout(1000*60*1);    
        MongoClientOptions myOptions = build.build();  

        try{
            mongoClient = new MongoClient("192.168.1.107:27017", myOptions);
=======
        try{
            mongoClient = new MongoClient("192.168.1.107", 27017);
>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270
        }catch (Exception e){
            log.error(e.getClass().getName()+": "+e.getMessage());
        }
        return mongoClient;
<<<<<<< HEAD
    }

=======

    }
>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270

}
