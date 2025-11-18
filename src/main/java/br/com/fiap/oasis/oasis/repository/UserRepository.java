package br.com.fiap.oasis.oasis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.oasis.oasis.domain.ritual.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}