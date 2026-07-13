-- Flyway schema baseline initialization

CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Insert a default administrator user for demo/testing (password is bcrypt hash for 'admin123')
INSERT INTO users (username, email, password, role)
VALUES ('admin', 'admin@codeted.com', '$2a$10$r8O7kOphAexvFv59mHqWbePpxTswZlO6HlGq0dZ5303U.c8KexGaq', 'ROLE_ADMIN')
ON CONFLICT (username) DO NOTHING;
