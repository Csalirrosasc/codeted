package com.codeted.blog.repository;

import com.codeted.blog.entity.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

    List<BlogPost> findByPublishedTrueOrderByFeaturedDescCreatedAtDesc();

    long countByPublishedTrue();

    Optional<BlogPost> findByPublicId(UUID publicId);
}
