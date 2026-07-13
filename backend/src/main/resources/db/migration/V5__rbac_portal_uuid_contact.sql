CREATE EXTENSION IF NOT EXISTS pgcrypto;

ALTER TABLE users ADD COLUMN IF NOT EXISTS public_id UUID;
UPDATE users SET public_id = gen_random_uuid() WHERE public_id IS NULL;
ALTER TABLE users ALTER COLUMN public_id SET NOT NULL;
ALTER TABLE users ADD CONSTRAINT uq_users_public_id UNIQUE (public_id);

ALTER TABLE refresh_tokens ADD COLUMN IF NOT EXISTS public_id UUID;
UPDATE refresh_tokens SET public_id = gen_random_uuid() WHERE public_id IS NULL;
ALTER TABLE refresh_tokens ALTER COLUMN public_id SET NOT NULL;
ALTER TABLE refresh_tokens ADD CONSTRAINT uq_refresh_tokens_public_id UNIQUE (public_id);

ALTER TABLE customers ADD COLUMN IF NOT EXISTS public_id UUID;
UPDATE customers SET public_id = gen_random_uuid() WHERE public_id IS NULL;
ALTER TABLE customers ALTER COLUMN public_id SET NOT NULL;
ALTER TABLE customers ADD CONSTRAINT uq_customers_public_id UNIQUE (public_id);

ALTER TABLE projects ADD COLUMN IF NOT EXISTS public_id UUID;
UPDATE projects SET public_id = gen_random_uuid() WHERE public_id IS NULL;
ALTER TABLE projects ALTER COLUMN public_id SET NOT NULL;
ALTER TABLE projects ADD CONSTRAINT uq_projects_public_id UNIQUE (public_id);

ALTER TABLE quotes ADD COLUMN IF NOT EXISTS public_id UUID;
UPDATE quotes SET public_id = gen_random_uuid() WHERE public_id IS NULL;
ALTER TABLE quotes ALTER COLUMN public_id SET NOT NULL;
ALTER TABLE quotes ADD CONSTRAINT uq_quotes_public_id UNIQUE (public_id);

ALTER TABLE service_offerings ADD COLUMN IF NOT EXISTS public_id UUID;
UPDATE service_offerings SET public_id = gen_random_uuid() WHERE public_id IS NULL;
ALTER TABLE service_offerings ALTER COLUMN public_id SET NOT NULL;
ALTER TABLE service_offerings ADD CONSTRAINT uq_service_offerings_public_id UNIQUE (public_id);

ALTER TABLE portfolio_items ADD COLUMN IF NOT EXISTS public_id UUID;
UPDATE portfolio_items SET public_id = gen_random_uuid() WHERE public_id IS NULL;
ALTER TABLE portfolio_items ALTER COLUMN public_id SET NOT NULL;
ALTER TABLE portfolio_items ADD CONSTRAINT uq_portfolio_items_public_id UNIQUE (public_id);

ALTER TABLE blog_posts ADD COLUMN IF NOT EXISTS public_id UUID;
UPDATE blog_posts SET public_id = gen_random_uuid() WHERE public_id IS NULL;
ALTER TABLE blog_posts ALTER COLUMN public_id SET NOT NULL;
ALTER TABLE blog_posts ADD CONSTRAINT uq_blog_posts_public_id UNIQUE (public_id);

CREATE TABLE IF NOT EXISTS roles (
    id BIGSERIAL PRIMARY KEY,
    public_id UUID NOT NULL UNIQUE DEFAULT gen_random_uuid(),
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(180),
    system_role BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS permissions (
    id BIGSERIAL PRIMARY KEY,
    public_id UUID NOT NULL UNIQUE DEFAULT gen_random_uuid(),
    code VARCHAR(80) NOT NULL UNIQUE,
    module VARCHAR(60) NOT NULL,
    description VARCHAR(180),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS role_permissions (
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    CONSTRAINT fk_role_permissions_role FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE,
    CONSTRAINT fk_role_permissions_permission FOREIGN KEY (permission_id) REFERENCES permissions (id) ON DELETE CASCADE
);

ALTER TABLE users ADD COLUMN IF NOT EXISTS full_name VARCHAR(120);
ALTER TABLE users ADD COLUMN IF NOT EXISTS status VARCHAR(20) NOT NULL DEFAULT 'active';
ALTER TABLE users ADD COLUMN IF NOT EXISTS customer_id BIGINT;
ALTER TABLE users ADD CONSTRAINT fk_users_customer FOREIGN KEY (customer_id) REFERENCES customers (id) ON DELETE SET NULL;

CREATE TABLE IF NOT EXISTS contact_leads (
    id BIGSERIAL PRIMARY KEY,
    public_id UUID NOT NULL UNIQUE DEFAULT gen_random_uuid(),
    full_name VARCHAR(120) NOT NULL,
    email VARCHAR(120) NOT NULL,
    phone VARCHAR(30),
    company VARCHAR(120),
    message TEXT NOT NULL,
    source VARCHAR(40) NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'new',
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO roles (name, description, system_role)
SELECT 'ROLE_ADMIN', 'Control total del sistema', TRUE
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_ADMIN');

INSERT INTO roles (name, description, system_role)
SELECT 'ROLE_EDITOR', 'Administrador de contenido comercial', TRUE
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_EDITOR');

INSERT INTO roles (name, description, system_role)
SELECT 'ROLE_CLIENT', 'Cliente con acceso al portal', TRUE
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_CLIENT');

INSERT INTO permissions (code, module, description)
VALUES
('users.read', 'users', 'Ver usuarios'),
('users.write', 'users', 'Gestionar usuarios'),
('roles.read', 'roles', 'Ver roles'),
('roles.write', 'roles', 'Gestionar roles'),
('leads.read', 'leads', 'Ver leads'),
('leads.write', 'leads', 'Gestionar leads'),
('customers.read', 'customers', 'Ver clientes'),
('customers.write', 'customers', 'Gestionar clientes'),
('projects.read', 'projects', 'Ver proyectos'),
('projects.write', 'projects', 'Gestionar proyectos'),
('quotes.read', 'quotes', 'Ver cotizaciones'),
('quotes.write', 'quotes', 'Gestionar cotizaciones'),
('blog.read', 'blog', 'Ver blog'),
('blog.write', 'blog', 'Gestionar blog'),
('services.read', 'services', 'Ver servicios'),
('services.write', 'services', 'Gestionar servicios'),
('portfolio.read', 'portfolio', 'Ver portafolio'),
('portfolio.write', 'portfolio', 'Gestionar portafolio'),
('dashboard.read', 'dashboard', 'Ver dashboard'),
('portal.read.own', 'portal', 'Ver portal propio')
ON CONFLICT (code) DO NOTHING;

INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
JOIN permissions p ON p.code IN (
    'users.read','users.write','roles.read','roles.write','leads.read','leads.write',
    'customers.read','customers.write','projects.read','projects.write','quotes.read',
    'quotes.write','blog.read','blog.write','services.read','services.write',
    'portfolio.read','portfolio.write','dashboard.read','portal.read.own'
)
WHERE r.name = 'ROLE_ADMIN'
ON CONFLICT DO NOTHING;

INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
JOIN permissions p ON p.code IN ('blog.read','blog.write','services.read','services.write','portfolio.read','portfolio.write','leads.read','dashboard.read')
WHERE r.name = 'ROLE_EDITOR'
ON CONFLICT DO NOTHING;

INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
JOIN permissions p ON p.code IN ('portal.read.own')
WHERE r.name = 'ROLE_CLIENT'
ON CONFLICT DO NOTHING;

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
JOIN roles r ON r.name = u.role
ON CONFLICT DO NOTHING;
