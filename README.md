# offertechnicaltest-pierretuaillon



## How to build from sources
```sh
cd pierretuaillon
mvn clean
mvn install
```


## Launch application
```sh
mvn spring-boot:run
```


## How to use the API
When the application is lunch 2 datas are create
"johnDoe", "French", 1995-11-30, null, null
"pierreTuaillon", "French", 1995-11-30, "0619797031", "M"

- Lauch the application
- use curl in consol or postman collection

For example to get johnDoe
```sh
curl -v localhost:8080/user/1
```

## H2 
H2 console is enable

localhost:8080/h2-console/

| Data          | value               |
| ------------- | -------------               |
| Driver Class  | org.h2.Driver               |
| JDBC URL      | jdbc:h2:mem:technicaltestdb |
| User Name     | sa                          |
| Password      |                             | 