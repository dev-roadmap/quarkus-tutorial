package io.vepo.tutorial.quarkus.user;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/user")
@ApplicationScoped
public class UserEndpoint {

    @Inject
    Users users;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> findAll() {
        return users.list();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public User create(@Valid CreateUserRequest request) {
        return users.create(User.builder()
                                .email(request.getEmail())
                                .username(request.getUsername())
                                .firstName(request.getFirstName())
                                .lastName(request.getLastName()).admin(request.isAdmin())
                                .hashedPassword(request.getHashedPassword())
                                .build());
    }

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public User findByUsername(@PathParam("username") String username) {
        return users.findByUsername(username)
                    .orElseThrow(() -> new NotFoundException("User not found! username=" + username));
    }
}
