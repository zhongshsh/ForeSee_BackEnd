import redis   
import json
<<<<<<< HEAD

# 用于1、2的数据导入，数据比较零散，因此没有封装

=======
>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270
# /data/ForeSee_BackEnd/server-192/src/main/resources/Redis
redis_conn = redis.Redis(host='localhost', port=6479, decode_responses=True, 
                        password='nopassword',db=2)   # host是redis主机，需要redis服务端和客户端都启动 redis默认端口是6379

# with open('/data/prj2020/EnterpriseSpider/basic/stock_index.csv','r') as f:
#     data = f.read().strip().split('\n')[1:]
with open('/data/prj2020/EnterpriseSpider/basic/stock_index.json','r') as f:
    data = json.loads(f.read())
print("Successfully load data")
for i in data:
    s = i.get('industry_name')
    t = i.get('industry_code')
<<<<<<< HEAD
    redis_conn.set(s, t)
    redis_conn.set(t, t)
    stocks = i.get('stock')
    for j in stocks.keys():
        redis_conn.set(""+j,t)
    for j in stocks.values():
        redis_conn.set(""+j,t)
=======
    # redis_conn.set(s, t)
    # redis_conn.set(t, t)
    stocks = i.get('stock')
    for j in stocks.keys():
        redis_conn.sadd(j,t)
>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270
    # i = i.split(',')
    # redis_conn.sadd(i[0],i[1])
    # redis_conn.sadd(i[2],i[1])
    # redis_conn.sadd(i[2],i[1])
    # redis_conn.sadd(i[3],i[1])
    # redis_conn.sadd(i[4],i[1])
<<<<<<< HEAD
=======

>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270
