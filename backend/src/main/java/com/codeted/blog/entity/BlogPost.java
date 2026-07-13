package com.codeted.blog.entity;

import com.codeted.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "blog_posts")
public class BlogPost extends BaseEntity {

    @Column(nullable = false, length = 160)
    private String title;

    @Column(nullable = false, unique = true, length = 180)
    private String slug;

    @Column(nullable = false, length = 100)
    private String author;

    @Column(nullable = false, length = 320)
    private String excerpt;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private boolean featured;

    @Column(nullable = false)
    private boolean published;
}
