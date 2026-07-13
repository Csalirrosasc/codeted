package com.codeted.blog.service;

import com.codeted.blog.dto.CreateBlogPostRequest;
import com.codeted.blog.dto.BlogPostResponse;
import com.codeted.blog.dto.UpdateBlogPostRequest;

import java.util.List;
import java.util.UUID;

/**
 * Service contract for blog queries exposed to public pages.
 */
public interface BlogService {

    /**
     * Returns the blog posts currently available for public listing.
     *
     * @return list of blog post cards
     */
    List<BlogPostResponse> getPublicPosts();

    List<BlogPostResponse> findAll();

    BlogPostResponse create(CreateBlogPostRequest request);

    BlogPostResponse update(UUID publicId, UpdateBlogPostRequest request);

    void delete(UUID publicId);
}
