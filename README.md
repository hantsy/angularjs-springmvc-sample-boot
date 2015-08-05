angularjs-springmvc-sample
==========================

An example application using AnguarJS/Bootstrap as frontend and Spring MVC as REST API producer.

**This is a Spring Boot version of the original application.**


##Requirements

   * JDK 8

     Oracle Java 8 is required, go to [Oracle Java website](http://java.oracle.com) to download it and install into your system. 
     
     Optionally, you can set **JAVA\_HOME** environment variable and add *&lt;JDK installation dir>/bin* in your **PATH** environment variable.

   * Apache Maven
   
     Download the latest Apache Maven from [http://maven.apache.org](http://maven.apache.org), and uncompress it into your local system. 
    
     Optionally, you can set **M2\_HOME** environment varible, and also do not forget to append *&lt;Maven Installation dir>/bin* your **PATH** environment variable.  

## Run this project

   1. Clone the codes.

    <pre>
    git clone https://github.com/hantsy/angularjs-springmvc-sample
    </pre>
  
   2. Instead of running this project via `mvn tomcat7:run` command, using `mvn spring-boot:run` here.
  
    <pre>
    mvn spring-boot:run
    </pre>

   3. Go to [http://localhost:9000](http://localhost:9000) to test it. If you want to explore the REST API docs online, there is a *Swagger UI* configured for visualizing the REST APIs, just go to [http://localhost:9000/swagger-ui.html](http://localhost:9000/swagger-ui.html).



*The detailed steps will be added in the wiki pages of this project soon.*
