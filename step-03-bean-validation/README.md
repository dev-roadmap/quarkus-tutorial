# Step 03 - Bean Validation

[Jakarta Bean Validation](https://beanvalidation.org/latest-draft/spec/) is a very useful specification. I can't find any reason to do not use it. If you know, please share with me.

## The worst way to do it

Validation is a boring feature. It is important, but most of the time it pollutes the code. You have to check function by function if all values are according to the expected.

So, let's imagine the in the code of our Step 02 we need to validate that the `username` is a String not empty, with a minimum size of 4 and a maximum of 15 and no whitespace. How can we do it?

```java
@POST
@Produces(MediaType.APPLICATION_JSON)
public User create(CreateUserRequest request) {
    Objects.requireNonNull(request.getUsername(), "\"username\" cannot be null!");
    if (request.getUsername().isBlank()) {
        throw new BadRequestException("\"username\" may not be blank");
    } else if (!request.getUsername().matches("[a-zA-Z][a-zA-Z0-9]")) {
        throw new BadRequestException("\"username\" should start with a letter and should only accept letters and numbers");
    } else if (request.getUsername().length() < 4 || request.getUsername().length() > 15) {
        throw new BadRequestException("\"username\" should have size [4,15]");
    }
    return users.create(User.builder()
                            .email(request.getEmail())
                            .username(request.getUsername())
                            .firstName(request.getFirstName())
                            .lastName(request.getLastName()).admin(request.isAdmin())
                            .hashedPassword(request.getHashedPassword())
                            .build());
}
```

Jakarta Bean Validation allow us to remove all this code and replace with simple annotations.

# Configuring JPA in an existent Quarkus Project

To enable Jakarta Bean Validation, you should add it's implementation to Quarkus, that is [Hibernate Validator](https://hibernate.org/validator/).

```xml
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-hibernate-validator</artifactId>
</dependency>
```