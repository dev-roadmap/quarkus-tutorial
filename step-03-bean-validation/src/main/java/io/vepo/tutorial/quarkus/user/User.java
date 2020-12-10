package io.vepo.tutorial.quarkus.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "tb_users", uniqueConstraints = {
    @UniqueConstraint(name = "uq_users_username", columnNames = {
        "username" }),
    @UniqueConstraint(name = "uq_users_email", columnNames = {
        "email" })
})
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
