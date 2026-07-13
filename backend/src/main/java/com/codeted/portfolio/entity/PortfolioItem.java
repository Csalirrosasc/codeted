package com.codeted.portfolio.entity;

import com.codeted.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "portfolio_items")
public class PortfolioItem extends BaseEntity {

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, unique = true, length = 170)
    private String slug;

    @Column(nullable = false, length = 80)
    private String category;

    @Column(nullable = false, length = 280)
    private String summary;

    @Column(columnDefinition = "TEXT")
    private String challenge;

    @Column(columnDefinition = "TEXT")
    private String solution;

    @Column(columnDefinition = "TEXT")
    private String result;

    @Column(nullable = false)
    private boolean featured;

    @Column(nullable = false)
    private boolean published;
}
