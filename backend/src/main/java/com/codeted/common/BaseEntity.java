package com.codeted.common;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

/**
 * Abstract base class for all JPA entities in the CodeTED project.
 *
 * <p>Provides the standard primary key ({@code id}) and audit timestamps
 * ({@code createdAt}, {@code updatedAt}) that every entity must have.
 *
 * <p>All entity classes must extend this class instead of duplicating
 * these fields (DRY principle).
 *
 * <p>Architecture note: Never access a repository directly from a
 * Controller. Always go through the Service layer.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(name = "public_id", nullable = false, unique = true, updatable = false)
    private UUID publicId;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    /**
     * Sets {@code createdAt} and {@code updatedAt} before the entity is first persisted.
     */
    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        if (this.publicId == null) {
            this.publicId = UUID.randomUUID();
        }
        this.createdAt = now;
        this.updatedAt = now;
    }

    /**
     * Updates {@code updatedAt} whenever the entity is modified.
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }
}
