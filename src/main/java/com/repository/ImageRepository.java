package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
