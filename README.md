# An-Asynchronous-RMI-Dictionary-Service
Java EE project for Distributed systems module.

## Technologies
- [TomCat](https://tomcat.apache.org/) The Apache Tomcat® software is an open source implementation of the Java Servlet, JavaServer Pages, Java Expression Language and Java WebSocket technologies.
- [Java RMI](http://www.oracle.com/technetwork/java/javase/tech/index-jsp-136424.html#close) Java Remote Method Invocation (Java RMI) enables the programmer to create distributed Java technology-based to Java technology-based applications, in which the methods of remote Java objects can be invoked from other Java virtual machines*, possibly on different hosts. 

## Architecture
Project File Structure

### Server

File `DictionaryServer.java` contains runnable main method. This invokes RMI register and bind `DbController` class to `dictionaryServer` through custom Remote interface `DictionaryService`.
The DbController then initialize `DbService` class and calls `getDefinition` method. After retreiving data it waits for 10 seconds to simulate heavy server usage.
Class DbServer contains method 'ProcessFile'.  
This method will populate HashMap with about 700 word to definition. **This must run pior to initial use.** otherwise all the queries will return "String not found".


