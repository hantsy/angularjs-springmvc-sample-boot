FROM node:latest AS ui
WORKDIR /usr/src/ui
COPY package.json .
# Setup NPM mirror, optionally for China users.
#RUN npm config set registry https://registry.npm.taobao.org/ 
RUN npm install 
COPY . .
RUN node_modules/.bin/bower install --allow-root
RUN node_modules/.bin/gulp

FROM maven:latest AS boot
WORKDIR /usr/src/app
COPY pom.xml .
#COPY settings.xml /usr/share/maven/ref/settings-docker.xml
RUN mvn -B -f pom.xml -s /usr/share/maven/ref/settings-docker.xml dependency:resolve
COPY . .
RUN mvn -B -s /usr/share/maven/ref/settings-docker.xml clean package -DskipTests
 
FROM java:8-jdk-alpine
WORKDIR /static
COPY --from=ui /usr/src/ui/dist/ .
WORKDIR /app
COPY --from=boot /usr/src/app/target/angularjs-springmvc-sample-boot.jar .
ENTRYPOINT ["java", "-jar", "/app/angularjs-springmvc-sample-boot.jar"]
#CMD ["--spring.profiles.active=postgres"]