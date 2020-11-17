# Step 02 - Configure JPA _Jakarta Persistence_

JPA is a specification from Jakarta EE that controls the Data Access Layer. The most common implementation for it is [Hibernate ORM](https://hibernate.org/orm/).

# Trade-offs

Choose technology involves a trade-off. I always thought that if I choose JPA, I will lose control of any generated SQL. And that is true!

JPA is an ORM, so most of the SQL is generated from a JPQL. If you do a bad design of your code, you will have SQL been generated at execution time, that is a bad design. In truth, horrible design.

But you will boost your coding speed. You will no more care about writing SQL or modelling the database. You can think in terms of objects, not tables. This does not mean that you will not care about modelling. In the first time is better to create the database model before, than design the objects according to what you want. This will avoid you creating some very commons Anti Patterns from JPA. I, have worked on a project where if you get a user from the database, you can load all objects associated with that user on the memory crashing the application.

It is important to know well the specification to create an optimal service, balancing database performance with development speed.

| Advantages | Aisadvantages |
|------------|---------------|
| Fast Coding | SQL is generated |
| Easy to change the model | Lack of support for NoSQL ¹ |
| Database agnostic | |

_¹ Use [JNoSQL](https://www.jnosql.org/) instead of JPA_

# Configuring JPA in an existent Quarkus Project

## Configure dependencies

So the first step is to configure the dependencies. For adding JPA you will have to add the Hibernate Plugin and the Database JDBC plugin. 

_Why we have two dependencies?_ This is one of the code ideas of JPA, it is database agnostic. You can change your database with almost no change on your code.

```xml
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-hibernate-orm</artifactId>
</dependency>
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-jdbc-postgresql</artifactId>
</dependency>
```

If you want to know which database you can use, just search on [mvnrepository.com](https://mvnrepository.com/search?q=jdbc&d=io.quarkus).

## Modeling the database

The next step we need to model the database. You probably have your database model using SQL, but you need to create Java POJOs that will map your data in your Java code.

For this example we will create a backend CRUD for User. That means we will create the Create, Read, Update and Delete endpoints for it.

So, to model we have to add the annotations:

| Annotation | Description |
|------------|-------------|
| `javax.persistence.Entity` | |
| `javax.persistence.Table` | |
| `javax.persistence.Id` | |
| `javax.persistence.Column` | |

Not even all annotations are required, but you should do a good design of your code.

So I'm defining the User adding all required annotations, and some NamedQuery and a Builder. The builder is just a fancy code to improve the readability and the NamedQuery it will be used later to search for users.

```java
package io.vepo.tutorial.quarkus.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "tb_users")
@NamedQuery(name = "User.findByUsernameAndHashedPassword", query = "SELECT usr FROM User usr WHERE usr.username = :username AND usr.hashedPassword = :hashedPassword AND usr.enabled = true")
@NamedQuery(name = "User.findByUsername", query = "SELECT usr FROM User usr WHERE usr.username = :username")
public class User {
    public static final class UserBuilder {
        private String email;
        private String username;
        private String firstName;
        private String lastName;
        private boolean admin;
        private String hashedPassword;

        private UserBuilder() {
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder admin(boolean admin) {
            this.admin = admin;
            return this;
        }

        public UserBuilder hashedPassword(String hashedPassword) {
            this.hashedPassword = hashedPassword;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    public static final UserBuilder builder() {
        return new UserBuilder();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String email;

    @Column
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private boolean admin;

    @Column(name = "hashed_password")
    private String hashedPassword;

    @Column(name = "enabled")
    private boolean enabled;

    public User() {
    }

    private User(UserBuilder builder) {
        email = builder.email;
        username = builder.username;
        firstName = builder.firstName;
        lastName = builder.lastName;
        admin = builder.admin;
        hashedPassword = builder.hashedPassword;
        enabled = true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        User other = (User) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + ", username=" + username + ", firstName=" + firstName
                + ", lastName=" + lastName + ", admin=" + admin + ", hashedPassword=" + hashedPassword + ", enabled="
                + enabled + "]";
    }
}
```

I want to point out some important things on this code. The `@Column` annotation is not required, if you omit does not mean that JPA will ignore, it will only read the column with the same name from the field. If you want a not persisted column, you should use create a `transient` field. If you have a compound name for the field, for example, `lastName`, if you do not specify the name in `@Column`, JPA will search for `lastName`, not `last_name`. I don't know if you have understood, but naming is important! All applications should follow naming conventions and the [SQL Naming Convetions](https://dzone.com/articles/a-guide-to-sql-naming-conventions) is different from [Java Naming Convetions](https://www.oracle.com/java/technologies/javase/codeconventions-namingconventions.html).

With the `@Column` you have more options, like `required` and `unique`. You can see all options on [Jakarta Documentation](https://jakarta.ee/specifications/persistence/2.2/apidocs/javax/persistence/Column.html).

For the `id` column, I have added two important annotations. `@Id` and `@GeneratedValue`are required to create a good design, but if you choose a different database probably it will have different values!