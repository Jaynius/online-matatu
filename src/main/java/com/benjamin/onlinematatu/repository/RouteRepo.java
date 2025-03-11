package com.benjamin.onlinematatu.repository;

import com.benjamin.onlinematatu.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepo extends JpaRepository<Route, Integer> {
}
