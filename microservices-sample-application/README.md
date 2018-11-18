Pre - Setup

1.Change the config/src/main/resources/application.properties to reflect whatever your application-config directory is on your workspace.  This will be the hub for all your configuration.

2.cd to that directory

3.git init

4.git add *

5.git commit -am "x"



Startup

The startup order is config, embedded-redis(optional*) discovery, services, and finally gateway.   If its started out of order things tend to not work.

Most services except config (which looks for the discovery service post startup) will startup without issue.  You might see that gateway doesn't start up okay if it can not see the config server( which takes a while to become truly available after it sees discovery)... just try again and it should work.   

sample-service (since you can run multiple instances) expects server.port to be specified.  Both boot runners in /build of that project set 8085 and 9085 respectively.  The payload has the port that services the request in it so you can see the load balancing running.



urls

<http://localhost:8080/index.html>

<http://localhost:8080/discovery/eureka/apps>

<http://localhost:9411/zipkin>





additional 

session management (*)-- this project uses redis. In the real world you will want to install it.  For the purposes of this demo we use embedded redis as a short cut.  Embedded-redis is a community project with limited support.  If you have trouble with it just install the actual redis.