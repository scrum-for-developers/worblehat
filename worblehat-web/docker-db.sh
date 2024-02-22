#!/bin/bash

OS=$(uname)

if [ "$OS" == "Darwin" ]; then
  # On Mac, check if colima is running and start it if necessary
  if ! colima status > /dev/null 2>&1; then
    echo "Starting colima..."
    colima start --network-address
    export TESTCONTAINERS_DOCKER_SOCKET_OVERRIDE=/var/run/docker.sock
    export TESTCONTAINERS_HOST_OVERRIDE=$(colima ls -j | jq -r '.address')
    export DOCKER_HOST="unix://${HOME}/.colima/default/docker.sock"
  fi
elif [ "$OS" == "Linux" ]; then
  # On Linux, docker should be managed differently (assuming it's already handled)
  echo "Assuming Docker is already handled on Linux"
else
  # On Windows or other OS
  echo "Please make sure Docker is started manually."
  exit 1
fi

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
    mariadb:11  >> /dev/null
fi
