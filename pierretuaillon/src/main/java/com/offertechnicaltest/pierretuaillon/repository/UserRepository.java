package com.offertechnicaltest.pierretuaillon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.offertechnicaltest.pierretuaillon.model.User;

/**
 * UserRepository extends JpaRepository
 * Contains all User
 * @author pierretuaillon
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {

}
