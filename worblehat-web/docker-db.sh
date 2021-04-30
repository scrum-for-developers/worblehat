#!/bin/bash

docker ps -a | grep worblehat-db >> /dev/null

if [ $? -eq 0 ]; then
  docker ps -a | grep worblehat-db | grep Exited >> /dev/null
  if [ $? == 0 ]; then 
    echo Starting existing but stopped container worblehat-db
    docker start worblehat-db  >> /dev/null
  else 
    echo Container worblehat-db is already running
  fi
else
  echo Creating and starting container worblehat-db
  docker run --detach \
    --name worblehat-db \
    --env MYSQL_ROOT_PASSWORD=root \
    --env MYSQL_USER=worblehat \
    --env MYSQL_PASSWORD=worblehat \
    --env MYSQL_DATABASE=worblehat_test \
    --publish 3306:3306 \
    mysql:5.7  >> /dev/null
fi