# Case Study - Price service

## Description

The '**Priceservice**' provides a REST interface for the users to fetch prices for the requested accommodation.
The application uses recent files shared by the partners with real prices in EUR currency to fetch the price list.

## Table of Contents

- [Assumptions](#assumptions)
- [Getting Started](#Getting-Started)
- [Project Structure](#Project-Structure)
- [Code Coverage-JaCoCo](#Code-Coverage)
- [Swagger](#Swagger-Link)
- [Spring Actuator](#Spring-Actuator)
- [Curl](#Curl-Request)
- [Questions](#Questions)

## Assumptions

1. Artist as a non-separate concept.
    * Reason: Currently, the dataloader bean is replacing the existing price data in case of any change in file.
    * Impact: If this contract changes to incremental data, we need to change logic to add data in existing object
      rather than full replacement.

3. Accommodation price can be available with multiple partners
    * Example: accommodation id 4567 is present in data files shared by advertiser 100, 200 and 300.

## Getting Started

## Build and run

For running this application there are the following options:

* Build and run using docker file
* Build and run using local tools:
* Build and run using IntelliJ/Eclipse IDE (dev setup)

### Simple build and run with docker

This option uses Java and Maven to build the application jar, and then Docker to build the Docker image.

* Requirements:
    * docker ([Docker Installation guide](https://docs.docker.com/engine/install))
    * Java 17 ([JDK Installation Guide](https://docs.oracle.com/en/java/javase/17/install/index.html))
    * Maven 3.9.7 ([Maven Installation Guide](https://maven.apache.org/install.html) )

* Build and run commands:
    * `mvn clean install`
    * `docker build -t priceservice:0.1 -f docker/Dockerfile . `
    * `docker run -p 8080:8080 priceservice:0.1`
    * `curl localhost:8080/priceservice/prices/13478`

### Build and run using local tools

In this option, Java and Maven are used to build the executable application jar, which is then run as a Java program.

* Requirements
    * java (17) jdk + jvm (for running the partner service)
    * Maven (installed locally)

* Compile and test commands:
    * Simple jar generation: `mvn clean install`
    * To run tests : `mvn test`

* Run the application
    * `java -jar target/priceservice-0.0.1-SNAPSHOT.jar -Dspring.profiles.active=dev`

### Build and run using IntelliJ/Eclipse IDE

This approach required the import the codebase into the IDE as maven project.

* Requirements
    * java (17) jdk + jvm (for running the partner service)
    * Maven (installed locally)
    * IntelliJ/Eclipse


* Compile and test commands:
    * Import the project in IDE as maven project.
    * To run tests : `mvn test`
    * To build project : `project build`


* Run the application
    * Run the `com.trivago.casestudy.priceservice.PriceServiceApplication` class as Java Program

### Run Test for Code Coverage

* Run `mvn test `
* code coverage report will be available under path `target/site/jacoco/index.html`

### Swagger

* http://localhost:8080/swagger-ui/index.html#/priceservice-controller

### Spring Actuator

* http://localhost:8080/actuator/

## Project Structure

This structure of this project is based on multi-tier architecture where we have below components.

* `controller`: This layer is responsible for exposing the REST-based API that the user will call, and it is the
  entry point for the service.
* `service`: The service layer holds the classes responsible for having business logic.
* `config`: Application level configuration.
* `dataloaders`: contains the different types of file loaders that are used to read the data files shared by partners.
* `deserializer`: contains the classes that are used by Jackson Library to deserialize the file content.
* `exception`: contains Custom Exception classes and `GlobalExceptionHandler` to handle any exception in the
  application.
* `model`: contains the POJO classes used in the application.

![price-service-design.png](price-service-design.png)

* Request received by PriceServiceController
* PriceServiceController calls PriceService to fetch the price list.
* PriceService use the DataLoader service to get data.
* The DataLoader service loads the price list of accommodations in memory objects.
* The AdvertiseFileSystem watcher will watch the data file directory to detect any price updates in the shared file.
* If any file is updated, FileSystemWater will generate an event that is listened to by the AdvertiseFileChangeLister.
* AdvertseFileChangeListner will call the dataloader to update the in-memory object with the latest file data.

### Code Coverage

* Mockito and SpringBoot test framework are used for Junit to achieve `85%+` code coverage.
* Unit test cases at controller cover all possible scenario of the request path attribute like empty,valid and invalid
  inputs.
* JsonFileLoaderTest & YamlFileLoaderTest cover below possible scenario of the advertiser data file.
    * [empty.yaml](src%2Ftest%2Fresources%2Fstatic%2Fprices%2Fempty.yaml)
    * [valid.json](src%2Ftest%2Fresources%2Fstatic%2Fprices%2Fvalid.json)
    * [invalid_data_types.yaml](src%2Ftest%2Fresources%2Fstatic%2Fprices%2Finvalid_data_types.yaml)
    * [valid_data_with_float_price.json](src%2Ftest%2Fresources%2Fstatic%2Fprices%2Fvalid_data_with_float_price.json)
    * [valid_data_with_double_price.json](src%2Ftest%2Fresources%2Fstatic%2Fprices%2Fvalid_data_with_double_price.json)

### Swagger Link

- http://localhost:8080/swagger-ui/index.html#/priceservice-controller

### Curl Request

`curl --location 'localhost:8080/priceservice/prices/13478'`
` [{"id":100,"partnerId":13478,"currency":"EUR","price":1351.85}]`  
`

## Questions

### How could a partner with a potentially slow REST interface be integrated?

* Possible Solutions
    * By increasing the connect and read timeout properties to avoid any connection drop.
    * Split data in small chunk files, Currently, one file contains all the data resulting in fatty
      files and can cause some network delay while transfer, splitting into mulitple small data
      would help to share the data.
    * By implementing the retry mechanism.
    * We can also use circuit breaker feature to avoid any cascading effects.
    * Schedule the file sharing activity in off-peak hours.

### How could your solution scale for multiple thousand requests per second?

A service can be scaled, either horizontally or vertically to support huge load.

Theoretically, vertical scaling can touch a limit at some point of time. Considering this fact, it's preferable to
choose horizontal scaling where servers can be added in parallel.

To scale the service horizontally, we need to consider below aspects.

* Make the service stateless by moving the advertiser data from local objects to persistent storage like relational
  database such as MySQL.
* Use docker to create application container and kubernetes to orchestrate containers for easy scaling and management.
* Deploy multiple instances of the services.
* Use load balances to distribute the incoming traffic across multiple containers.
* Querying the database for each client request is not a good approach, so we need a distributed cache(Redis) and local
  cache (LRU).
* Introduce LRU caching solutions to optimize the API response time, which is backed by Redis.
* Create a new service, Offline Processor to process the data files from the partner and update in the database.
* The partner should send the incremental files on a regular basis and the full file once per day in off-peak hours to
  eliminate any data consistency issues.
