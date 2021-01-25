package io.vepo.tutorial.quarkus.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.vepo.tutorial.quarkus.infra.ReservedWord;

public class CreateUserRequest {

    @Email
    @NotBlank(message = "email may not be blank")
    private String email;

    @Size(min = 4, max = 15, message = "username should have size [{min},{max}]")
    @NotBlank(message = "username may not be blank")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]+$", message = "\"username\" should start with a letter and should only accept letters and numbers")
    @ReservedWord("admin")
    @ReservedWord("root")
    private String username;

    @NotBlank(message = "firstName may not be blank")
    private String firstName;

    @NotBlank(message = "lastName may not be blank")
    private String lastName;

    private boolean admin;

    @NotBlank(message = "hashedPassword may not be blank")
    private String hashedPassword;

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (admin ? 1231 : 1237);
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((hashedPassword == null) ? 0 : hashedPassword.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
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
        CreateUserRequest other = (CreateUserRequest) obj;
        if (admin != other.admin) {
            return false;
        }
        if (email == null) {
            if (other.email != null) {
                return false;
            }
        } else if (!email.equals(other.email)) {
            return false;
        }
        if (firstName == null) {
            if (other.firstName != null) {
                return false;
            }
        } else if (!firstName.equals(other.firstName)) {
            return false;
        }
        if (hashedPassword == null) {
            if (other.hashedPassword != null) {
                return false;
            }
        } else if (!hashedPassword.equals(other.hashedPassword)) {
            return false;
        }
        if (lastName == null) {
            if (other.lastName != null) {
                return false;
            }
        } else if (!lastName.equals(other.lastName)) {
            return false;
        }
        if (username == null) {
            if (other.username != null) {
                return false;
            }
        } else if (!username.equals(other.username)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CreateUserRequest [email=" + email + ", username=" + username + ", firstName=" + firstName
                + ", lastName=" + lastName + ", admin=" + admin + ", hashedPassword=" + hashedPassword + "]";
    }

}
