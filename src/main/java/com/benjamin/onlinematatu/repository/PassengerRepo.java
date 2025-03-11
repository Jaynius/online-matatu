package com.benjamin.onlinematatu.repository;

import com.benjamin.onlinematatu.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepo extends JpaRepository<Passenger, Integer> {
}
