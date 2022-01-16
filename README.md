## Intro
This project uses spring boot + dubbo microservice architecture, and the registry uses [nacos](https://nacos.io/). The `demo-user-web` provides the rest api service, the `demo-user-service` provides the user dubbo service, and the `demo-mail-service` provides the mail dubbo service. The calling chain is `demo-user-web` -> `demo-user-service` -> `demo-mail-service`

## Modify configuration file
Please modify the parameters in the following configuration files to your own actual values


```
# demo-mail-service/src/main/resources/application.yml
spring.mail.host=smtp.xxx.com
spring.mail.username=xxx@xxx.com
spring.mail.password=xxxxxx
dubbo.registry.address=nacos://127.0.0.1:8848?namespace=${spring.profiles.active}

# demo-user-service/src/main/resources/application.yml
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/demo
spring.datasource.username=xxx
spring.datasource.password=xxx
dubbo.registry.address=nacos://127.0.0.1:8848?namespace=${spring.profiles.active}

# demo-web-service/src/main/resources/application.yml
dubbo.registry.address=nacos://127.0.0.1:8848?namespace=${spring.profiles.active}
```

## Initialize database
Create a database named `demo` in the mysql client and execute `demo-user-service/demo.sql` in this database

## Package && build image && run
Run the following command in the project root directory
```
mvn clean package -Dmaven.test.skip=true

docker build -t demo-mail-service:1.0.0 demo-mail-service
docker build -t demo-user-service:1.0.0 demo-user-service
docker build -t demo-user-web:1.0.0 demo-user-web

docker run -d --name demo-mail-service demo-mail-service:1.0.0
docker run -d --name demo-user-service demo-user-service:1.0.0
docker run -d --name demo-user-web -p 8080:8080 demo-user-web:1.0.0
```

## Api documentation
Use knife4j to automatically generate api documents, after running demo-user-web, access http://localhost:8080/doc.html
