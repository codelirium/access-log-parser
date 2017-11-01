# access-log-parser
A simple parametric access log parser. It is using H2 database with MySQL mode.

### To compile:

    `$ mvn clean package`

### To run example #1:

    `$ java -jar target/parser-0.0.1-SNAPSHOT.jar --accesslog=log/access.log --startDate=2017-01-01.15:00:00 --duration=hourly
    --threshold=200`

### To run example #2:

    `$ java -jar target/parser-0.0.1-SNAPSHOT.jar --accesslog=log/access.log --startDate=2017-01-01.00:00:00 --duration=daily 
    --threshold=500`
