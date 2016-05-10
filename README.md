# sparktemplate

Simple Web App with Database connection template using SparkJava and EclipseLink. 

## Getting Started

This template required [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html) and [Maven](https://maven.apache.org/). It's meant to be used as base to start devoloping WebServices and potencially a Web application, serving also the front-end.

1. Set up your database connection using the variable in **/conf/app.properties**;
2. It has an example of an entity class called SysUser that represents a dummy user and it was provided some services around it to serve as starting point;
3. After adding new entity classes, to reflect them into the datebase, make sure you declare them in **/src/META-INF/persistence.xml** file;

## Powered by
[Ubiquity Technology](http://www.ubiquity.pt)
