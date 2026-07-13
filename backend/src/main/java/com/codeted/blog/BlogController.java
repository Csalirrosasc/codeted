package com.codeted.blog;

import com.codeted.blog.dto.CreateBlogPostRequest;
import com.codeted.blog.dto.BlogPostResponse;
import com.codeted.blog.dto.UpdateBlogPostRequest;
import com.codeted.blog.service.BlogService;
import com.codeted.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/blog")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @Operation(summary = "List public blog posts", description = "Returns the posts shown on the public site.")
    @GetMapping("/posts")
    public ApiResponse<List<BlogPostResponse>> getPublicPosts() {
        return ApiResponse.success("Posts obtenidos correctamente", blogService.getPublicPosts());
    }

    @GetMapping("/admin/posts")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EDITOR')")
    @Operation(summary = "List blog posts for admin", description = "Returns every post available in the blog module.")
    public ApiResponse<List<BlogPostResponse>> findAll() {
        return ApiResponse.success("Posts administrativos obtenidos correctamente", blogService.findAll());
    }

    @PostMapping("/posts")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EDITOR')")
    @Operation(summary = "Create blog post", description = "Creates a blog post for the public insights module.")
    public ApiResponse<BlogPostResponse> create(@Valid @RequestBody CreateBlogPostRequest request) {
        return ApiResponse.success("Post creado correctamente", blogService.create(request));
    }

    @PatchMapping("/posts/{publicId}")
    @PreAuthorize("hasAuthority('blog.write')")
    public ApiResponse<BlogPostResponse> update(@PathVariable UUID publicId, @Valid @RequestBody UpdateBlogPostRequest request) {
        return ApiResponse.success("Post actualizado correctamente", blogService.update(publicId, request));
    }

    @DeleteMapping("/posts/{publicId}")
    @PreAuthorize("hasAuthority('blog.write')")
    public ApiResponse<Void> delete(@PathVariable UUID publicId) {
        blogService.delete(publicId);
        return ApiResponse.success("Post eliminado correctamente");
    }
}
