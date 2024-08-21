/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
app.service('remoteApi', ['$http','tools', function ($http,tools) {
    var queryTopic = function(callback){
        $http({
            method: "GET",
            url: "topic/list.query"
        }).then(callback);
    }

    var queryClusterList = function(callback){
        $http({
            method: "GET",
            url: "cluster/list.query"
        }).then(callback);
    }

    var queryBrokerHisData = function(date,callback){
        $http({
            method: "GET",
            url: "dashboard/broker.query?date="+date,
            timeout:15000,
        }).then(callback);
    }

    var queryTopicHisData = function(date,topicName,callback){
        $http({
            method: "GET",
            url: "dashboard/topic.query?date="+date+"&topicName="+topicName,
            timeout:15000,
        }).then(callback);
    }

    var queryTopicCurrentData = function(callback){
        $http({
            method: "GET",
            url: "dashboard/topicCurrent",
            timeout:15000,
        }).then(callback);
    }


    return {
        queryTopic:queryTopic,
        queryClusterList:queryClusterList,
        queryBrokerHisData:queryBrokerHisData,
        queryTopicHisData:queryTopicHisData,
        queryTopicCurrentData:queryTopicCurrentData
    }
}])

