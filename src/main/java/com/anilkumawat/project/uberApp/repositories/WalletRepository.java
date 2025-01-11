package com.anilkumawat.project.uberApp.repositories;

import com.anilkumawat.project.uberApp.entities.User;
import com.anilkumawat.project.uberApp.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet,Long> {
    Optional<Wallet> findByUser(User user);
}
