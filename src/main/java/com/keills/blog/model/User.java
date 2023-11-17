package com.keills.blog.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_table")
@Getter
@Setter
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 15, message = "Имя не менее 2 символов и не больше 15")
    private String username;

    @Size(min = 6, message = "Пароль не менее 6 символов")
    private String password;

    @Email
    private String email;

    private Set<Role> roles;

    @OneToOne
    @JoinColumn(name="blog_table.id")
    private Blog blog;

    @CreatedDate
    @DateTimeFormat(pattern = "dd/mm/yyyy")
    private Date registration_date;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = roles.stream()
                                            .map(role->new SimpleGrantedAuthority(role.name()))
                                            .collect(Collectors.toSet());
        return authorities;
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
