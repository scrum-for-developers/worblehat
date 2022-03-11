#!/bin/bash

# This script also runs on Apple M1 chips because the platform is specified
# Even if it's no ARM platform it works because of Apples simulation

podman ps -a | grep worblehat-db >> /dev/null

if [ $? -eq 0 ]; then
  podman ps -a | grep worblehat-db | grep Exited >> /dev/null
  if [ $? == 0 ]; then
    echo Starting existing but stopped container worblehat-db
    podman start worblehat-db  >> /dev/null
  else
    echo Container worblehat-db is already running
  fi
else
  echo Creating and starting container worblehat-db
  podman run --detach \
    --name worblehat-db \
    --env MYSQL_ROOT_PASSWORD=root \
    --env MYSQL_USER=worblehat \
    --env MYSQL_PASSWORD=worblehat \
    --env MYSQL_DATABASE=worblehat_test \
    --publish 3306:3306 \
    mariadb:10.5
fi
