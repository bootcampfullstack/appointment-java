#!/bin/bash
cd /home/ec2-user/agenda

docker stop agenda
docker rm agenda
docker build -t agenda-backend .
docker run -d --name agenda -p 8080:8080 agenda-backend

