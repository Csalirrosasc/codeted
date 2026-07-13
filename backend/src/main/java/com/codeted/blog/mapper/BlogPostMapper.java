package com.codeted.blog.mapper;

import com.codeted.blog.dto.BlogPostResponse;
import com.codeted.blog.entity.BlogPost;
import org.springframework.stereotype.Component;

@Component
public class BlogPostMapper {

    public BlogPostResponse toResponse(BlogPost blogPost) {
        return new BlogPostResponse(
                blogPost.getId().toString(),
                blogPost.getPublicId(),
                blogPost.getTitle(),
                blogPost.getAuthor(),
                blogPost.getExcerpt(),
                blogPost.getSlug(),
                blogPost.getContent(),
                blogPost.isFeatured(),
                blogPost.isPublished()
        );
    }
}
