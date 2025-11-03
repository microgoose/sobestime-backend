-- Расширение для генерации UUID
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Зарегистрированные пользователи (security)
CREATE TABLE IF NOT EXISTS user_principal
(
    id                      UUID PRIMARY KEY         DEFAULT uuid_generate_v4(),
    email                   VARCHAR(100) NOT NULL UNIQUE,
    username                VARCHAR(100) NOT NULL,
    password                VARCHAR(255) NOT NULL,
    enabled                 BOOLEAN                  DEFAULT TRUE,
    account_non_expired     BOOLEAN                  DEFAULT TRUE,
    account_non_locked      BOOLEAN                  DEFAULT TRUE,
    credentials_non_expired BOOLEAN                  DEFAULT TRUE,
    created_at              TIMESTAMP WITH TIME ZONE DEFAULT now(),
    updated_at              TIMESTAMP WITH TIME ZONE DEFAULT now()
);

-- Роли (security)
CREATE TABLE IF NOT EXISTS role
(
    id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name        VARCHAR(50) UNIQUE NOT NULL,
    description VARCHAR(255)
);

-- Пользовательские роли (security)
CREATE TABLE IF NOT EXISTS user_principal_role
(
    user_principal_id UUID,
    role_id           UUID,
    PRIMARY KEY (user_principal_id, role_id),
    FOREIGN KEY (user_principal_id) REFERENCES user_principal (id),
    FOREIGN KEY (role_id) REFERENCES role (id)
);