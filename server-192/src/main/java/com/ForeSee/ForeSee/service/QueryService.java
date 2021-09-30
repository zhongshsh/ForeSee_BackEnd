package com.ForeSee.ForeSee.service;

import com.ForeSee.ForeSee.dao.MongoDBDao.*;
import com.ForeSee.ForeSee.dao.RedisDao.*;
<<<<<<< HEAD
import com.ForeSee.ForeSee.dao.Neo4jDao.*;
import com.ForeSee.ForeSee.dao.*;
import com.ForeSee.ForeSee.util.*;
=======
import com.ForeSee.ForeSee.util.MongoConn;
>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270
import com.mongodb.MongoClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
<<<<<<< HEAD
import java.util.ArrayList;
import java.util.LinkedHashSet;
import org.json.JSONObject;
import java.util.Arrays;
import org.bson.Document;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
=======
>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270

/**
 * @author zhongshsh
 * @ClassName QueryService
 * @Description 检索数据的获取
<<<<<<< HEAD
=======
 * @create 2021-03-02
>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270
 */

@Slf4j
@Service
public class QueryService {
    @Autowired
    CompanyQuery companyQ;
    @Autowired
    IndustryQuery industryQ;
    @Autowired
    NoticeQuery noticeQ;
    @Autowired
    NewsQuery newsQ;
    @Autowired
    ReportQuery reportQ;
<<<<<<< HEAD
    @Autowired
    RelationQuery relationQ;
    @Autowired
    RuleMatchDao rule;
    

    private int sortNum = 50;


    /**
     * 先将检索词传给正则方法，抽取实体和关系；再到图数据库，找出相关关系
     * @param query 检索词
     * @return
     */
    public String getRelationQuery(String query){
        String relationInfo;
        try {
            // 正则匹配
            List<String> target = rule.getRelation(query);
            // neo4j查询
            relationInfo = relationQ.getRelation(target);
        }catch (Exception e){
            e.printStackTrace();
            relationInfo = "[]";
        }
        return relationInfo;
    }
=======
>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270

    /**
     * 先将检索词传给redis，让redis找出这个公告的代号，再用mongodb查出他的信息
     * @param query 检索词
     * @return
     */
    public String getNoticeQuery(String query, String page){
<<<<<<< HEAD
        List<String> noticeIds = noticeQ.getNoticeIds(query);
        MongoClient mongoClient = null;
        String noticeInfo;
        try {
            mongoClient = MongoConn.getConn();
            if (noticeIds.size() == 0) {
                // 根据企业索引倒推
                List<String> stockCodes = companyQ.queryService(query, mongoClient);
                stockCodes.remove(0);
                noticeInfo = StockNotice.getNoticeBasedStockCodes(stockCodes, mongoClient, page);
            } else {
                noticeInfo = StockNotice.getNoticeBasedQuery(noticeIds, mongoClient, page);
            }
        }catch (Exception e){
            e.printStackTrace();
            noticeInfo = "[]";
        }
        mongoClient.close();
=======
        // redis查询返回stockCodeList
        List<String> noticeIds = noticeQ.getNoticeIds(query);
        log.info("Notice Query matching result: " + noticeIds);
        // mongodb方法
        MongoClient mongoClient=null;
        String noticeInfo;
        try {
            mongoClient = MongoConn.getConn();
            // 如果检索不到结果
            if (noticeIds.size()==0) {
                // 根据企业索引倒推
                List<String> stockCodes = companyQ.getStockCodes(query);
                if (stockCodes.size()==0){
                    // 根据新闻索引倒推
                    List<String> newsIds = newsQ.getNewsIds(query);
                    // 根据news倒推stockCodes
                    stockCodes = StockNews.getStockCodes(newsIds, mongoClient);
                } 
                // 根据industryCodes检索出report内容
                noticeInfo = StockNotice.getNoticeBasedStockCodes(stockCodes, mongoClient, page);
                
            } else {
                noticeInfo = StockNotice.getNoticeBasedQuery(noticeIds, mongoClient, page);
            }
        }finally {
            mongoClient.close();
        }
>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270
        return noticeInfo;
    }

    /**
     * 先将检索词传给redis，让redis找出这个资讯的代号，再用mongodb查出他的信息
     * @param query 检索词
     * @return
     */
    public String getReportQuery(String query, String page){
<<<<<<< HEAD
        
        List<String> reportIds = reportQ.getReportIds(query);
        MongoClient mongoClient = null;
        String reportInfo;
        try {
            mongoClient = MongoConn.getConn();
            if (reportIds.size() == 0) {
                // 根据企业索引倒推
                List<String> stockCodes = companyQ.queryService(query, mongoClient);
                stockCodes.remove(0);
                // 根据stockCodes到redis DB2中检索出对应的industryCodes
                List<String> industryCodes = industryQ.getIndustryCodes(stockCodes);
=======
        // redis查询返回stockCodeList
        List<String> reportIds = reportQ.getReportIds(query);
        log.info("Report Query matching result: " + reportIds);
        // mongodb方法
        MongoClient mongoClient=null;
        String reportInfo;
        try {
            mongoClient = MongoConn.getConn();
            // 如果检索不到结果
            if (reportIds.size()==0) {
                // 根据企业索引倒推
                List<String> stockCodes = companyQ.getStockCodes(query);
                if (stockCodes.size()==0){
                    // 根据新闻索引倒推
                    List<String> newsIds = newsQ.getNewsIds(query);
                    // 根据news倒推stockCodes
                    stockCodes = StockNews.getStockCodes(newsIds, mongoClient);
                }
                // 根据stockCodes到redis DB2中检索出对应的industryCodes
                List<String> industryCodes = industryQ.getIndustryCodes(stockCodes);
                // 根据industryCodes检索出report内容
>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270
                reportInfo = IndustryReport.getReportBasedIndustryCodes(industryCodes, mongoClient, page);
            } else {
                reportInfo = IndustryReport.getReportBasedQuery(reportIds, mongoClient, page);
            }
<<<<<<< HEAD
        } catch (Exception e){
            e.printStackTrace();
            reportInfo = "{\"totalRecords\":0, \"information\":[], \"page\":1}";
        }
        mongoClient.close();
=======
        } finally {
            mongoClient.close();
        }
>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270
        return reportInfo;
    }

    /**
     * 先将检索词传给redis，让redis找出这个新闻的代号，再用mongodb查出他的信息
     * @param query 检索词
     * @return
     */
    public String getNewsQuery(String query, String page){
<<<<<<< HEAD
        
        String newsInfo;
        MongoClient mongoClient = null;
        try {
            mongoClient = MongoConn.getConn();
            List<String> newsIds = newsQ.getNewsIds(query, mongoClient, page);
            if (newsIds.size() == 0){
                // 根据企业索引倒推
                List<String> stockCodes = companyQ.queryService(query, mongoClient);
                stockCodes.remove(0);
=======
        // redis查询返回newsIds
        List<String> newsIds = newsQ.getNewsIds(query);
        log.info("News Query matching result: " + newsIds);
        // mongodb方法
        MongoClient mongoClient=null;
        String newsInfo;
        try {
            mongoClient = MongoConn.getConn();
            
            if (newsIds.size()==0){
                // 根据企业索引倒推
                List<String> stockCodes = companyQ.getStockCodes(query);
>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270
                newsInfo = StockNews.getNewsBasedStockCodes(stockCodes, mongoClient, page);
            } else {
                newsInfo = StockNews.getNewsBasedQuery(newsIds, mongoClient, page);
            }
<<<<<<< HEAD
        }catch (Exception e){
            e.printStackTrace();
            newsInfo = "[]";
        }
        mongoClient.close();
=======
        }finally {
            mongoClient.close();
        }
>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270
        return newsInfo;
    }

    /**
     * 先将检索词传给redis，让redis找出这个行业的代号，再用mongodb查出他的信息
     * @param query 检索词
     * @return
     */
    public String getIndustryQuery(String query){
<<<<<<< HEAD
        MongoClient mongoClient = null;
        String industryInfo, b;
        try {
            mongoClient = MongoConn.getConn();
            List<String> industryCodes = industryQ.queryService(query, mongoClient);
            b = industryCodes.get(0);
            industryCodes.remove(0);
            industryInfo = IndustryInfo.getIndustryInfo(industryCodes, mongoClient);

        }catch (Exception e){
            e.printStackTrace();
            industryInfo = "[]";
        }
        mongoClient.close();
=======
        List<String> industryCodes = industryQ.getIndustryCodes(query);

        log.info("Industry matching result: " + industryCodes);
        // mongodb方法
        MongoClient mongoClient=null;
        String industryInfo;
        try {
            mongoClient = MongoConn.getConn();
            // 如果检索不到结果
            if (industryCodes.size()==0) {
                // redis查询返回newsIds
                List<String> newsIds = newsQ.getNewsIds(query);
                // 根据news倒推stockCodes
                List<String> stockCodes = StockNews.getStockCodes(newsIds, mongoClient);
                // 根据stockCodes到redis DB2中检索出对应的industryCodes
                industryCodes = industryQ.getIndustryCodes(stockCodes);
            }
            industryInfo = IndustryInfo.getIndustryInfo(industryCodes, mongoClient);
        }finally {
            mongoClient.close();
        }
>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270
        return industryInfo;
    }

    /**
     * 先将检索词传给redis，让redis找出这个公司的代号，再用mongodb查出他的信息
     * @param query 检索词
     * @return
     */
<<<<<<< HEAD
    public String getCompanyQuery(String query, String page){
        MongoClient mongoClient = null;
        String companyInfo;
        String b;
        try {
            mongoClient = MongoConn.getConn();
            List<String> stockCodes = companyQ.queryService(query, mongoClient);
            b = stockCodes.get(0);
            stockCodes.remove(0);
            companyInfo = CompanyInfo.getCompanyInfo(stockCodes, mongoClient, page);
            companyInfo = companyInfo.substring(0, companyInfo.length()-1) + ", \"feedback\": \"" + b + "\"}";
            
        } catch (Exception e) {
            e.printStackTrace();
            companyInfo = "[]";
        }
        mongoClient.close();
=======
    public String getCompanyQuery(String query){
        // redis查询返回stockCodeList
        List<String> stockCodes = companyQ.getStockCodes(query);
        log.info("Company fuzzy matching result: " + stockCodes);
        
        // mongodb方法
        MongoClient mongoClient=null;
        String companyInfo;
        
        try {
            mongoClient = MongoConn.getConn();
            // 如果检索不到结果
            if (stockCodes.size()==0) {
                // redis查询返回newsIds
                List<String> newsIds = newsQ.getNewsIds(query);
                // 根据news倒推stockCodes
                stockCodes = StockNews.getStockCodes(newsIds, mongoClient);
            }
            companyInfo = CompanyInfo.getCompanyInfo(stockCodes, mongoClient);
        }finally {
            mongoClient.close();
        }
>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270
        return companyInfo;
    }

}
