package com.codeted.blog.service;

import com.codeted.blog.dto.CreateBlogPostRequest;
import com.codeted.blog.dto.BlogPostResponse;
import com.codeted.blog.dto.UpdateBlogPostRequest;
import com.codeted.blog.entity.BlogPost;
import com.codeted.blog.mapper.BlogPostMapper;
import com.codeted.blog.repository.BlogPostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogPostRepository blogPostRepository;
    private final BlogPostMapper blogPostMapper;

    @Override
    public List<BlogPostResponse> getPublicPosts() {
        return blogPostRepository.findByPublishedTrueOrderByFeaturedDescCreatedAtDesc()
                .stream()
                .map(blogPostMapper::toResponse)
                .toList();
    }

    @Override
    public List<BlogPostResponse> findAll() {
        return blogPostRepository.findAll().stream()
                .sorted(Comparator.comparing(BlogPost::isFeatured).reversed()
                        .thenComparing(BlogPost::getCreatedAt, Comparator.reverseOrder()))
                .map(blogPostMapper::toResponse)
                .toList();
    }

    @Override
    public BlogPostResponse create(CreateBlogPostRequest request) {
        BlogPost blogPost = new BlogPost();
        applyChanges(blogPost, request.title(), request.slug(), request.author(), request.excerpt(), request.content(), request.featured(), request.published());
        return blogPostMapper.toResponse(blogPostRepository.save(blogPost));
    }

    @Override
    public BlogPostResponse update(UUID publicId, UpdateBlogPostRequest request) {
        BlogPost blogPost = blogPostRepository.findByPublicId(publicId)
                .orElseThrow(() -> new EntityNotFoundException("Post no encontrado"));
        applyChanges(blogPost, request.title(), request.slug(), request.author(), request.excerpt(), request.content(), request.featured(), request.published());
        return blogPostMapper.toResponse(blogPostRepository.save(blogPost));
    }

    @Override
    public void delete(UUID publicId) {
        BlogPost blogPost = blogPostRepository.findByPublicId(publicId)
                .orElseThrow(() -> new EntityNotFoundException("Post no encontrado"));
        blogPostRepository.delete(blogPost);
    }

    private void applyChanges(BlogPost blogPost, String title, String slug, String author, String excerpt, String content, boolean featured, boolean published) {
        blogPost.setTitle(title);
        blogPost.setSlug(slug);
        blogPost.setAuthor(author);
        blogPost.setExcerpt(excerpt);
        blogPost.setContent(content);
        blogPost.setFeatured(featured);
        blogPost.setPublished(published);
    }
}
