package com.personal.project.api.models.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.personal.project.api.models.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Table(name= User.TABLE_NAME, schema = User.TABLE_SCHEMA)
@Entity(name="user")


@Setter(AccessLevel.NONE)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {

    public static final String TABLE_NAME = "tb_user";
    public static final String TABLE_SCHEMA = "sch_application";

    @Id
    @Getter
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Getter
    @Column(name = "login", unique = true)
    @NotBlank()
    private String login;

    @Getter
    @Column(name = "password")
    @NotBlank()
    private String password;

    @Column(name = "role")
    private UserRole role;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    //@JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Product> product;

    public User(String login, String password, UserRole role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority(UserRole.ROLE_ADMIN.getRole()), new SimpleGrantedAuthority(UserRole.ROLE_USER.getRole()));
        else return List.of(new SimpleGrantedAuthority(UserRole.ROLE_USER.getRole()));
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

