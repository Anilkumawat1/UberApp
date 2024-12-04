package com.anilkumawat.project.uberApp.repositories;

import com.anilkumawat.project.uberApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
