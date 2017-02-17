#!/bin/bash


hdfs dfs -rm -r /user/hadoop/adahal3/

hdfs dfs -mkdir /user/hadoop/adahal3
hdfs dfs -put Parser/tokens.txt /user/hadoop/adahal3/input

cd HDoop; sbt clean; sbt package;
cd target; hadoop jar hparser-0.1.jar  /user/hadoop/adahal3/input  /user/hadoop/adahal3/output


 
exit 0