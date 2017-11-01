# access-log-parser
A simple parametric access log parser.

`$ mvn clean package`
`$ java -jar target/parser-0.0.1-SNAPSHOT.jar --startDate=2017-01-01.15:00:00 --duration=hourly --threshold=200`
`$ java -jar target/parser-0.0.1-SNAPSHOT.jar --startDate=2017-01-01.00:00:00 --duration=daily --threshold=500`
