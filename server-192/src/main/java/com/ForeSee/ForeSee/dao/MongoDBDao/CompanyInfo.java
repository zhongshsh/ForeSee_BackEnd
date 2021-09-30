package com.ForeSee.ForeSee.dao.MongoDBDao;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
<<<<<<< HEAD
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;
import java.util.Iterator;
// import org.springframework.data.mongodb.MongoDatabaseFactory;
=======
import com.alibaba.fastjson.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.mongodb.client.model.Filters.in;
>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270

@Slf4j
public class CompanyInfo {

    private static final String tableName="companyInfo";
    private static MongoCollection<Document> collection;
    private static StringBuilder sb;
<<<<<<< HEAD
    private static int totalRecords;
    private static int pageSize=6;
=======
>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270

    /**
     * 使用表companyInfo，返回参数列表中的企业的基本信息，用于一框式检索展示企业简要信息
     * @param stockCodes
     * @return [companyInfo, companyInfo]，详见project/server-192/src/main/resources/FrontEndData/Query/companyInfo.json
     */
<<<<<<< HEAD
    public static String getCompanyInfo(List<String> stockCodes, MongoClient client, String page) {
        totalRecords = stockCodes.size();
        collection = client.getDatabase("ForeSee").getCollection(tableName);
        sb = new StringBuilder("{\"page\":"+page+",\"totalRecords\":"+totalRecords+",\"company\":[");
        try{
            stockCodes = stockCodes.subList((Integer.parseInt(page)-1)*pageSize, Integer.parseInt(page)*pageSize);
        }catch (Exception e){
            stockCodes = stockCodes.subList((Integer.parseInt(page)-1)*pageSize, totalRecords);
        }
        Iterator<String> it = stockCodes.iterator();
        while (it.hasNext()) {
            String code = it.next();
            // 有it.next()为空的情况，需要排查
            try {
                Document originDoc = (Document) collection.find(eq("companyInfo.stock_code", code)).first().get("companyInfo");
                if (originDoc.toJson() != null) sb.append(originDoc.toJson()+",");
            }catch (Exception e){
                continue;
            }
            
=======
    public static String getCompanyInfo(List<String> stockCodes, MongoClient client) {

        collection = client.getDatabase("ForeSee").getCollection(tableName);
        sb = new StringBuilder("[");
        MongoCursor<Document> cursor = collection.find(in("companyInfo.stock_code", stockCodes)).iterator();
        while (cursor.hasNext()) {
            Document originDoc = (Document) cursor.next().get("companyInfo");
            sb.append(originDoc.toJson());
            sb.append(",");
>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270
        }
        if (sb.length() > 1) {
            sb.deleteCharAt(sb.length() - 1);
        }
<<<<<<< HEAD
        sb.append("]}");
=======
        sb.append("]");
        log.info("has already queried companyInfo from MongoDB based stockCodes");
>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270
        return sb.toString();
    }

    
    /**
     * 使用表companyInfo，返回单个企业的基本信息，用于展示企业详情信息
     * @param stockCode
     * @return {}，详见/server-192/src/main/resources/FrontEndData/BasicInfo/companyInfo.json
     */
    public static String getCompanyInfo(String stockCode,MongoClient client) {
<<<<<<< HEAD
        // MongoDatabaseFactory 
        collection = client.getDatabase("ForeSee").getCollection(tableName);
        // collection = client.getMongoDatabase("ForeSee").getCollection(tableName);
=======

        collection = client.getDatabase("ForeSee").getCollection(tableName);
>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270
        sb= new StringBuilder();
        Document originDoc = collection.find(in("companyInfo.stock_code", stockCode)).first();
        originDoc.remove("_id");
        sb.append(originDoc.toJson());
<<<<<<< HEAD
=======
        log.info("has already queried companyInfo from MongoDB based "+stockCode);
>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270
        return sb.toString();
    }

}
