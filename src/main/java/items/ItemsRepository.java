package com.example.demo.items;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemsRepository extends JpaRepository<Items, Long> {
    Optional<Items> findItemsByName(String name);
}
