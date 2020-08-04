package com.example.hgeapp.repository;

import com.example.hgeapp.models.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {

    Optional<City> findByName(String name);
}
