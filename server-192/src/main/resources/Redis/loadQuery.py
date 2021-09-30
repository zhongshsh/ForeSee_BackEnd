<<<<<<< HEAD
import redis   
import glob 

# /data/ForeSee_BackEnd/server-192/src/main/resources/Redis

=======
# 导入redis模块，通过python操作redis 也可以直接在redis主机的服务端操作缓存数据库
import redis   
import glob 
# /data/ForeSee_BackEnd/server-192/src/main/resources/Redis

# v = redis_conn.scard('stockCode') # set 数目
# v = redis_conn.smembers('stockCode') # set 元素,返回set object {}
# v = redis_conn.sismember('stockCode', 'value') # value是否在set中

>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270
# key   value value
# 通过key 返回相关的value值
# 存储：数据库words，key为key，value存储在key，通过word定位key，返回value

<<<<<<< HEAD
class loadQuery():
    def __init__(self, n):
        self.n = n
        self.redis_conn = redis.Redis(host='localhost', port=6479, decode_responses=True, 
                        password='nopassword',db=n['db'])  
    def load(self, fileName):
        print("正在导入：", fileName, "……")
        with self.redis_conn.pipeline(transaction=False) as p:
            with open(fileName,'r') as f:
                data = f.read().strip().split('\n')
                for i in data:
                    i = i.split('\t')
                    codes = i[1].split(' ')
                    for code in codes:
                        try:
                            if (self.n['type'] == 'int'):
                                p.sadd(i[0], int(code))
                            else :
                                p.sadd(i[0], code)
                        except Exception as e:
                            print(e)
                            print(code)
            p.execute()

    def run(self):
        if (self.n['add'] == 'files'):
            files = glob.glob(self.n['path'])
            for f in files:
                self.load(f)
        else:
            self.load(self.n['path'])
        print('finished!')

if __name__ == '__main__':
    # 行业11 : 
    industry = {
        'path': '/data/prj2020/EnterpriseSpider/index/index/industry_supplement.txt',
        'db': 11,
        'type':'string',
        'add':'file'
        }

    # 资讯12 : 
    report = {
        'path': '/data/prj2020/EnterpriseSpider/Index/index/report_index.txt',
        'db': 12,
        'type':'string',
        'add':'file'
        }

    # 企业13 : 
    company = {
        'path': '/data/prj2020/EnterpriseSpider/Index/index/stock_index.txt',
        'db': 13,
        'type':'string',
        'add':'file'
        }

    # 公告14 ：
    notice = {
        'path': '/data/prj2020/EnterpriseSpider/Index/index/notice/notice_index_*',
        'db': 14,
        'type':'int',
        'add':'files'
        }
    # 新闻15 : 
    news = {
        'path': '/data/prj2020/EnterpriseSpider/Index/index/news/news_index_*.txt',
        'db': 15,
        'type':'int',
        'add':'files'
        }

    # 新闻标题9
    newsTitle = {
        'path': '/data/prj2020/EnterpriseSpider/index/index/title_index.txt',
        'db': 9,
        'type':'int',
        'add':'file'
        }

    
    # 资讯标题10
    reportTitle = {
        'path': '/data/prj2020/EnterpriseSpider/index/index/report_title_index.txt',
        'db': 10,
        'type':'string',
        'add':'file'
        }

    n = loadQuery(industry)
    n.run()
    # /data/ForeSee_BackEnd/server-192/src/main/resources/Redis


=======
# 行业11 : 
industry = {
    'path': '/data/prj2020/EnterpriseSpider/Index/index/industry_index.txt',
    'db': 11
    }

# 资讯12 : 
report = {
    'path': '/data/prj2020/EnterpriseSpider/Index/index/report_index.txt',
    'db': 12
    }

# 企业13 : 
company = {
    'path': '/data/prj2020/EnterpriseSpider/Index/index/stock_index.txt',
    'db': 13
    }

# 公告14 ：
notice = {
    'path': '/data/prj2020/EnterpriseSpider/Index/index/notice/notice_index_*',
    'db': 14
    }
# 新闻15 : 
news = {
    'path': '/data/prj2020/EnterpriseSpider/Index/index/news/news_index_*.txt',
    'db': 15
    }

# select
n = notice


redis_conn = redis.Redis(host='localhost', port=6479, decode_responses=True, 
                        password='nopassword',db=n['db'])   
print("成功连接")
# with open(n['path'],'r') as f:
#     data = f.read().strip().split('\n')
#     for i in data:
#         i = i.split('\t')
#         codes = i[1].split(' ')
#         redis_conn.sadd(i[0], *codes)
# print("完成")

# news
files = glob.glob(n['path'])


# 导入部分 /data/prj2020/EnterpriseSpider/Index/index/news/news_index_16.txt
for k in files[:]:
    print(k)
    nw = k
    with open(nw,'r') as f:
        data = f.read().strip().split('\n')
        for i in data:
            i = i.split('\t')
            codes = i[1].split(' ')
            try:
                redis_conn.sadd(i[0], *codes)
            except:
                for code in codes:
                    redis_conn.sadd(i[0], code)
>>>>>>> c9ce903df66fa151612f875b4c001909a8b9b270
