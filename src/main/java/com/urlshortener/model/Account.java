package com.urlshortener.model;

import com.urlshortener.util.Constants;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

import static com.urlshortener.util.Constants.PASSWORD_LENGTH;

@Entity
@Table(name = "accounts")
public class Account extends BaseEntity {

    @NotEmpty
    @Pattern(regexp = Constants.ACCOUNT_NAME_REGEX)
    @Column(name = "name", unique = true, nullable = false, updatable = false)
    private String name;

    @NotEmpty
    @Size(max = PASSWORD_LENGTH, min = PASSWORD_LENGTH, message = "Password length should be exactly " + PASSWORD_LENGTH + " symbols")
    @Column(name = "password", nullable = false, updatable = false)
    private String password;

    public Account() {
    }

    public Account(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }


    //name is a business key
    //see https://docs.jboss.org/hibernate/stable/core.old/reference/en/html/persistent-classes-equalshashcode.html
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(name, account.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Account{");
        sb.append("id=").append(getId());
        sb.append(", name='").append(name).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
