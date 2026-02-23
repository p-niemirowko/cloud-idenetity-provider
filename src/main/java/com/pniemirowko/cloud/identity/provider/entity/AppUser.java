package com.pniemirowko.cloud.identity.provider.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "users")
public class AppUser {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;

    @Column(unique = true)
    private String username;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;

    @Builder
    private AppUser(String username, String password, Set<String> roles) {
        this.password = password;
        this.username = username;
        this.roles = roles;
    }
}
