package com.abdur.testing.repository;

import com.abdur.testing.entity.manytomany.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {


}
