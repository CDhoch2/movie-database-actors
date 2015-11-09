#!/usr/bin/env bash

# DockerHub Login
./docker-latest login -e ${dockerhub_mail} -u ${dockerhub_user} -p ${dockerhub_pwd}

#Tag images
./docker-latest tag -f moviedatabase_actors cdzwei/mvdb_actors:latest

# Push to DockerHub
./docker-latest push docker.io/cdzwei/mvdb_actors
