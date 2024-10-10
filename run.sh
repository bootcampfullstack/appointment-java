#!/bin/bash
cd /home/ec2-user/agenda

docker stop agenda
docker rm agenda
docker build -t agenda-backend .
sudo aws s3 cp s3://abutua-docker-envs/agenda.env .
docker run --env-file ./agenda.env  --restart unless-stopped -d --name agenda -p 8080:8080  agenda-backend 
cd ..
sudo rm -rf agenda

