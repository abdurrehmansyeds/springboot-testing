package com.abdur.testing.repository;

import com.abdur.testing.entity.manytomany.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag,Long> {
}
