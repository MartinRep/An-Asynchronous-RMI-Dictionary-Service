# An-Asynchronous-RMI-Dictionary-Service
Java EE project for Distributed systems module.

## Technologies
- [TomCat](https://tomcat.apache.org/) The Apache Tomcat® software is an open source implementation of the Java Servlet, JavaServer Pages, Java Expression Language and Java WebSocket technologies.
- [Java RMI](http://www.oracle.com/technetwork/java/javase/tech/index-jsp-136424.html#close) Java Remote Method Invocation (Java RMI) enables the programmer to create distributed Java technology-based to Java technology-based applications, in which the methods of remote Java objects can be invoked from other Java virtual machines*, possibly on different hosts. 

## Architecture
Project File Structure

### RMI Server

File `dictionary-service.java` contains runnable main method. This invokes RMI register and bind `DbController` class to `dictionaryServer` through custom Remote interface `DictionaryService`.
The DbController then initialize `DbService` class and calls `getDefinition` method. After retreiving data it waits for 10 seconds to simulate heavy server usage.
Class DbServer contains method `ProcessFile` which will process text file `dict.txt` into hashmap.  
This method will populate HashMap with about 700 word to definition. **This will run pior to initial use.** otherwise all the queries will return "String not found".

### RMI Client

File `Dictionary.war` contains Dynamic Web aplication. `Index.jsp` navigate to 4 options. Each with own servlet. Each will add job to inQueue Blocking queue. 10 workers are running in ThreadPool will procsess the request with RMI Server and return result to outQueue Concurrent Hash Map.


## Instalation

### RMI Server

Run `java -jar dictionary-service.java` in console.

### RMI Client

copy `Dictionary.war` file into Tomcat's `webapps` folder. Tomcat, if running, will expand war file into folder.

## Usage

After Instalation navigate your browser to `http://localhost:8080/Dictionary/` and start using the service. Note the port number, your Tomcat service can be running on different one.

![Demo](https://github.com/MartinRep/An-Asynchronous-RMI-Dictionary-Service/blob/master/git-Assets/Dictionary-Demo.gif)
