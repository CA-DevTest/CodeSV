### Spring Boot virtualized SOAP example

This is a sample gradle project showing the capabilities of CodeSV at virtualizing SOAP messages with the Spring Boot WS application. This project contains the Spring Boot application that creates a webservice server from a WSDL file and creates a webservice client that can be used for making requests. When the Spring Boot application is running, it exposes the real webservice to the localhost and can be found at `http://localhost:9090/codesv/ws/helloworld.wsdl`.

Tests in this project contain an example of a real webservice call and also an example of CodeSV virtualizing the SOAP response.

This project is runnable only with Java 8 and Gradle.

#### Prerequisite
To successfully compile and run this sample project, you first need to run the Gradle task genJaxb to generate necessary classes:
```
./gradlew genJaxb
```

#### Running the spring boot application
```
./gradlew bootRun
```

