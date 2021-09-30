#!/bin/bash 

nc -vz 192.168.1.107 27017
if [ $? -eq 1 ]
then
  PID=`kill -9 `ps -ef | grep -w 27017 | grep -v "grep" | awk '{print $2}'``
  sudo mongod --dbpath /data/db --port 27017 --logpath /home/user02/mongodb/logs/20210122.log --fork
fi

nc -vz 192.168.1.107 7474
if [ $? -eq 1 ]
then
  PID=`kill -9 `ps -ef | grep -w neo4j | grep -v "grep" | awk '{print $2}'``
  /data/prj2020/neo4j-community-3.4.5/bin/neo4j start
fi

nc -vz 192.168.1.107 6479
if [ $? -eq 1 ]
then
  PID=`kill -9 `ps -ef | grep -w 6479 | grep -v "grep" | awk '{print $2}'``
  /home/user02/suoyin/bin/redis-server /home/user02/suoyin/redisdata/redis.conf
fi