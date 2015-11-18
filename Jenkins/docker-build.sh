#!/usr/bin/env bash

cd ../movie-database-actors

# Docker Build Actors
/home/ec2-user/docker-latest build --tag="cdzwei/mvdb_actors:testtag" --label commit_id=test .
