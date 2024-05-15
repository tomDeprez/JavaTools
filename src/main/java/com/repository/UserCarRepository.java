package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.entity.UserCar;

public interface UserCarRepository extends JpaRepository<UserCar, Long> {
}
