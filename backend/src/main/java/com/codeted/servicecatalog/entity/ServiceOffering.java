package com.codeted.servicecatalog.entity;

import com.codeted.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "service_offerings")
public class ServiceOffering extends BaseEntity {

    @Column(nullable = false, length = 140)
    private String title;

    @Column(nullable = false, unique = true, length = 160)
    private String slug;

    @Column(nullable = false, length = 280)
    private String summary;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private boolean featured;

    @Column(nullable = false)
    private boolean active;
}
