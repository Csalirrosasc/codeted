package com.codeted.blog.dto;

/**
 * Response DTO for a blog post card exposed to the frontend landing page.
 *
 * @param id identifier of the post
 * @param title post title
 * @param author display author name
 * @param excerpt short post summary
 */
import java.util.UUID;

public record BlogPostResponse(
        String id,
        UUID publicId,
        String title,
        String author,
        String excerpt,
        String slug,
        String content,
        boolean featured,
        boolean published
) {
}
