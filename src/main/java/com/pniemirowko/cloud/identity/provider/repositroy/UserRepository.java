package com.pniemirowko.cloud.identity.provider.repositroy;

import com.pniemirowko.cloud.identity.provider.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findUserByUsername(String username);

    Collection<Object> findByUsername(String username);
}
