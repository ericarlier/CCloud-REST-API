# CCloud-REST-API

This is a Sample REST service to do topic basic administration tasks on Confluent Cloud
* List all topics
  * GET http://[host]:8080/topics
* Create one topic
  * POST http://[host]:8080/topics
  * BODY {"name":"test_java","partitions":3,"replicationFactor":3}
* Delete One Topic
  * DELETE http://[host]:8080/topics?name=[topic_to_delete]

**CAUTION** : This application does not provide any security, and thus should be run in a secure environment 

This sample application is implemented on Spring Boot Framework and uses the Apache Kafka Admin Java Class documented [here](https://kafka.apache.org/20/javadoc/index.html?org/apache/kafka/clients/admin/AdminClient.html)
 
This sample application loads your Confluent Cloud config files to enable connection to your Confluent Cloud Cluster.
Once you have created an account on Confluent Cloud, then created a cluster, and installed ccloud cli tool, then this config file is located under $USER_HOME_DIR/.ccloud/config
This is where this program will look for the config file by default, but you can specify another location by setting the following environment variable : CCLOUD_CONFIG

If you have maven installed, you can simply run this application with the following command in the project root dir:
`./mvnw spring-boot:run`
