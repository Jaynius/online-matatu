package com.benjamin.onlinematatu.repository;

import com.benjamin.onlinematatu.entity.GpsLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GpsLocationRepo extends JpaRepository<GpsLocation, Integer> {
}
