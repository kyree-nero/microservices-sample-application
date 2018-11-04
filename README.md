Setup
1.Change the config/src/main/resources/application.properties to reflect whatever your application-config directory is on your workspace.  This will be the hub for all your configuration.

2.cd to that directory

3.git init

4.git add *

5.git commit -am "x"



The startup order is config,discovery, services, and finally gateway.   If its started out of order things tend to not work.

Most services except config (which looks for the discovery service post startup) will startup without issue.  You might see that gateway doesn't start up okay if it can not see the config server( which takes a while to become truly available after it sees discovery)... just try again and it should work.   

<http://localhost:8080>

<http://localhost:8080/book-service>

<http://localhost:8080/book-service/books/1>