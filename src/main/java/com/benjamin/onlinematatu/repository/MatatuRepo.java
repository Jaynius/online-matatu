package com.benjamin.onlinematatu.repository;

import com.benjamin.onlinematatu.entity.Matatu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatatuRepo extends JpaRepository<Matatu, Integer> {
}
