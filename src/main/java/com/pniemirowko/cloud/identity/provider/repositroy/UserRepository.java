package com.pniemirowko.cloud.identity.provider.repositroy;

import com.pniemirowko.cloud.identity.provider.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<AppUser, UUID> {

    Optional<AppUser> findUserByUsername(String username);
}
