angularjs-springmvc-sample-boot
===============================

An example application using AnguarJS/Bootstrap as frontend and Spring MVC as REST API producer.

This version improved the original version, including:

* Gulp build system for the static resources
* All user to select package the static resources as part of final jar.

##Requirements

* JDK 8

 Oracle Java 8 is required, go to [Oracle Java website](http://java.oracle.com) to download it and install into your system. 
 
 Optionally, you can set **JAVA\_HOME** environment variable and add *&lt;JDK installation dir>/bin* in your **PATH** environment variable.

* Apache Maven

 Download the latest Apache Maven from [http://maven.apache.org](http://maven.apache.org), and uncompress it into your local system. 

 Optionally, you can set **M2\_HOME** environment varible, and also do not forget to append *&lt;Maven Installation dir>/bin* your **PATH** environment variable.  

* NodeJS

 Install [NodeJS](http://nodejs.org).
 
## Get the source codes

Clone the codes into your local system.

    git clone https://github.com/hantsy/angularjs-springmvc-sample-boot


## Run the project

### Run the frontend UI and backend respectively

1. Run the backend API server.

        spring-boot:run

  The backend APIs will run on port 9000.

2. Run the frontend UI resources standalone.
   
        npm install
        bower install
        gulp serve

  By default, the gulp serves the UI static resources on port 3000.

3. Go to [http://localhost:3000](http://localhost:3000) to test it.

### Run the project via Spring Boot command
     
1. Run the following command to clear the dependencies.
   
        npm install
        bower install

2. Run the backend API server with `spring-boot` command.

        spring-boot:run -Dstatic-resources

3. Go to [http://localhost:9000](http://localhost:9000) to test it. 

If you want to explore the REST API docs online, there is a *Swagger UI* configured for visualizing the REST APIs, just go to [http://localhost:9000/swagger-ui.html](http://localhost:9000/swagger-ui.html).


