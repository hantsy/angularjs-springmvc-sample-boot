angularjs-springmvc-sample-boot
===============================

An example application using AnguarJS/Bootstrap as frontend and Spring MVC as REST API producer.

This version improved the original version, including:

* Introduction of Gulp build system to processing the static resources
* The frontend UI can be run standalone via NodeJS eco-system
* An option provided for users to package the static resources as part of final jar and run the application as before

##Requirements

* JDK 8

 Oracle Java 8 is required, go to [Oracle Java website](http://java.oracle.com) to download it and install into your system. 
 
 Optionally, you can set **JAVA\_HOME** environment variable and add *&lt;JDK installation dir>/bin* in your **PATH** environment variable.

* Apache Maven

 Download the latest Apache Maven from [http://maven.apache.org](http://maven.apache.org), and uncompress it into your local system. 

 Optionally, you can set **M2\_HOME** environment varible, and also do not forget to append *&lt;Maven Installation dir>/bin* your **PATH** environment variable.  

* NodeJS

 Install [NodeJS](http://nodejs.org). NodeJS is required to build the frontend static resources, and you should also install `bower`.
 
      npm install -g bower
 
## Get the source codes

Get a copy of the source codes into your local system.

    git clone https://github.com/hantsy/angularjs-springmvc-sample-boot


## Run the project

### Run frontend UI and backend respectively

1. Run the backend API server via Spring Boot.

        spring-boot:run

  The backend APIs will run on port 9000.

2. Run the frontend UI standalone.
   
        npm install
        bower install
        gulp serve

  By default, gulp serves the frontend UI static resources on port 3000.

3. Go to [http://localhost:3000](http://localhost:3000) to test it.

### Run the project via Spring Boot maven plugin
     
1. Run the following command to resovle the dependencies of the frontend static resources.
   
        npm install
        bower install

2. Run the backend API server with `spring-boot` command.

        spring-boot:run -Dstatic-resources

3. Go to [http://localhost:9000](http://localhost:9000) to test it. 

If you want to explore the REST API docs online, there is a *Swagger UI* configured for visualizing the REST APIs, just go to [http://localhost:9000/swagger-ui.html](http://localhost:9000/swagger-ui.html).


