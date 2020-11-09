package io.vepo.tutorial.quarkus;

import java.util.Random;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.smallrye.mutiny.Uni;

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
    public String sayHello() {
        return "Hello World!";
    }

    @GET
    @Path("/json")
    @Produces(MediaType.APPLICATION_JSON)
    public HelloResponse sayHelloWithJson() {
        return generateResponse();
    }

    @GET
    @Path("/json/reactive")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<HelloResponse> sayHelloWithJsonReactively() {
        return Uni.createFrom().item(this::generateResponse);
    }
}