#!/usr/bin/env bash
echo "Shell-Skipt"
printenv

# DockerHub Login
/home/ec2-user/docker-latest login -e ${dockerhub_mail} -u ${dockerhub_user} -p ${dockerhub_pwd}

#Tag images
sudo /home/ec2-user/docker-latest tag -f moviedatabase_actors cdzwei/mvdb_actors:$(git log | head -1 | sed s/'commit '//)

# Push to DockerHub
/home/ec2-user/docker-latest push docker.io/cdzwei/mvdb_actors
