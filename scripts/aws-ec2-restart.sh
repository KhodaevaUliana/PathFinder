#!/bin/bash
JAR_FILE=/home/ec2-user/app/PathFinder-0.0.1-SNAPSHOT.jar
PID=$(pgrep -f $JAR_FILE)

if [ -n "$PID" ]; then
  kill $PID
  sleep 10  # give it time to shutdown
fi

nohup java -jar $JAR_FILE > /home/ec2-user/app.log 2>&1 &