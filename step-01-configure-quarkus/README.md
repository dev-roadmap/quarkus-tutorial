# Step 01 - Configure Quarkus

To build a project using Quarkus, what do you need?

# 1. Configure the dependencies

As Quarkus is a Jakarta EE, we will use the Jakarta EE annotations on the code. But, for the `pom.xml` we should point to Quarkus dependencies because quarkus has native support for GraalVM.

First we need add all dependencies to Quarkus, this can be done using dependencyManagement:

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-universe-bom</artifactId>
            <version>1.9.2.Final</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

The for that project we will need:

  1. Create REST API
  2. Add JSON Support
  3. Add Reactive Support

For that we will need the following dependencies:

  1. `io.quarkus:io.quarkus` for creating the REST API
  2. `io.quarkus:quarkus-resteasy-jsonb` for adding JSON serializer to REST API
  3. `io.quarkus:quarkus-resteasy-mutiny` for adding reactive support for REST API

# 2. Configuring the build

The next step we should configure Quarkus build. As we know, Quarkus creates a fat jar with all dependencies.

To enable the Quarkus builder on Maven, just add the following plugin:

```xml
<plugin>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-maven-plugin</artifactId>
    <version>1.9.2.Final</version>
    <executions>
        <execution>
            <goals>
                <goal>generate-code</goal>
                <goal>generate-code-tests</goal>
                <goal>build</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

In this example, I'm compiling as Java 11, but I'm using Java 15 to test. It will work for any version of Java newer than 11. If you need to execute it on Java 8, just change the compiler options.

We can make the build just executing:

```bash
mvn clean package
```

This will create two `jars` inside the target folder, the one terminating with `-runner.jar` can be executed with no dependencies.

```bash
$ java -jar target\quarkus-tutorial-runner.jar
__  ____  __  _____   ___  __ ____  ______
 --/ __ \/ / / / _ | / _ \/ //_/ / / / __/
 -/ /_/ / /_/ / __ |/ , _/ ,< / /_/ /\ \
--\___\_\____/_/ |_/_/|_/_/|_|\____/___/
2020-11-09 11:16:53,416 INFO  [io.quarkus] (main) quarkus-tutorial 0.0.1-SNAPSHOT on JVM (powered by Quarkus 1.9.2.Final) started in 4.706s. Listening on: http://0.0.0.0:8080
2020-11-09 11:16:53,470 INFO  [io.quarkus] (main) Profile prod activated.
2020-11-09 11:16:53,475 INFO  [io.quarkus] (main) Installed features: [cdi, mutiny, resteasy, resteasy-jsonb, resteasy-mutiny, smallrye-context-propagation]
2020-11-09 11:16:58,790 INFO  [io.quarkus] (Shutdown thread) quarkus-tutorial stopped in 0.024s
```

This is the way we should execute for production environments, for development we can use Quarkus Maven plugin. It already does the deploy of any change on the running server:

```bash
mvn quarkus:dev
```

# 3. Adding the REST API Endpoint

The latest step for creating an API is creating the code that will handle the requests. Using JAX-RS is easy, just create a class and add the annotations. 

The most simple example is: 

```java
@Path("/hello")
@ApplicationScoped
public class HelloEndpoint {
    @GET
    public String sayHello() {
        return "Hello World!";
    }
}
```

JAX-RS automatically generate a JSON representation for any object returned by this method, you have just to inform the MIME Type.

```java
@Path("/hello")
@ApplicationScoped
public class HelloEndpoint {
    private HelloResponse generateResponse() {
        HelloResponse response = new HelloResponse();
        response.setCode(new Random().nextInt());
        response.setMessage("Hello World!");
        return response;
    }

    @GET
    @Path("/json")
    @Produces(MediaType.APPLICATION_JSON)
    public HelloResponse sayHelloWithJson() {
        return generateResponse();
    }
}
```

Quarkus also have support for reactive programming. For JAX-RS, you have just to return a `Uni` or a `CompletableFuture`.

```java
@Path("/hello")
@ApplicationScoped
public class HelloEndpoint {
    private HelloResponse generateResponse() {
        HelloResponse response = new HelloResponse();
        response.setCode(new Random().nextInt());
        response.setMessage("Hello World!");
        return response;
    }

    @GET
    @Path("/json/reactive")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<HelloResponse> sayHelloWithJsonReactively() {
        return Uni.createFrom().item(this::generateResponse);
    }
}
```

# Conclusion

With Quarkus you can build quickly a REST API using JAX-RS. As JAX-RS is a Jakarta EE specification, you can migrate your code with few changes to another existing implementation, but Quarkus is the lighter implementation. 

Quarkus is a good choice! 