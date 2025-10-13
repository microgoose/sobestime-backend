-- Расширение для генерации UUID
CREATE
    EXTENSION IF NOT EXISTS "uuid-ossp";

-- Зарегистрированные пользователи
CREATE TABLE IF NOT EXISTS user_principal
(
    id                      UUID PRIMARY KEY         DEFAULT uuid_generate_v4(),
    email                   VARCHAR(100) NOT NULL UNIQUE,
    password                VARCHAR(255) NOT NULL,
    enabled                 BOOLEAN                  DEFAULT TRUE,
    account_non_expired     BOOLEAN                  DEFAULT TRUE,
    account_non_locked      BOOLEAN                  DEFAULT TRUE,
    credentials_non_expired BOOLEAN                  DEFAULT TRUE,
    created_at              TIMESTAMP WITH TIME ZONE DEFAULT now(),
    updated_at              TIMESTAMP WITH TIME ZONE DEFAULT now()
);

-- Роли
CREATE TABLE IF NOT EXISTS role
(
    id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name        VARCHAR(50) UNIQUE NOT NULL,
    description VARCHAR(255)
);

-- Пользовательские роли
CREATE TABLE IF NOT EXISTS user_principal_role
(
    user_principal_id UUID,
    role_id           UUID,
    PRIMARY KEY (user_principal_id, role_id),
    FOREIGN KEY (user_principal_id) REFERENCES user_principal (id),
    FOREIGN KEY (role_id) REFERENCES role (id)
);


-- Таблица пользователей
CREATE TABLE IF NOT EXISTS interview_user
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username   VARCHAR(255) NOT NULL,
    avatar_url VARCHAR(500)
);

-- Таблица ролей
CREATE TABLE IF NOT EXISTS interview_role
(
    id   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL
);

-- Таблица навыков
CREATE TABLE IF NOT EXISTS skill
(
    id   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL
);

-- Таблица грейдов
CREATE TABLE IF NOT EXISTS grade
(
    id   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL
);

-- Основная таблица заявок на собеседование
CREATE TABLE IF NOT EXISTS interview_request
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    description TEXT,
    creator_id  UUID NOT NULL,
    role_id     UUID NOT NULL,
    FOREIGN KEY (creator_id) REFERENCES interview_user (id),
    FOREIGN KEY (role_id) REFERENCES interview_role (id)
);

-- Таблица слотов собеседований
CREATE TABLE IF NOT EXISTS interview_slot
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    status     VARCHAR(100)             NOT NULL,
    start_time TIMESTAMP WITH TIME ZONE NOT NULL,
    request_id UUID                     NOT NULL,
    FOREIGN KEY (request_id) REFERENCES interview_request (id)
);

-- Связующая таблица для слотов и бронирующих
CREATE TABLE IF NOT EXISTS interview_slot_booker
(
    slot_id   UUID NOT NULL,
    booker_id UUID NOT NULL,
    PRIMARY KEY (slot_id, booker_id),
    FOREIGN KEY (slot_id) REFERENCES interview_slot (id),
    FOREIGN KEY (booker_id) REFERENCES interview_user (id)
);

-- Связующая таблица для заявок и грейдов
CREATE TABLE IF NOT EXISTS interview_request_grade
(
    request_id UUID NOT NULL,
    grade_id   UUID NOT NULL,
    PRIMARY KEY (request_id, grade_id),
    FOREIGN KEY (request_id) REFERENCES interview_request (id),
    FOREIGN KEY (grade_id) REFERENCES grade (id)
);

-- Связующая таблица для заявок и навыков
CREATE TABLE IF NOT EXISTS interview_request_skill
(
    request_id UUID NOT NULL,
    skill_id   UUID NOT NULL,
    PRIMARY KEY (request_id, skill_id),
    FOREIGN KEY (request_id) REFERENCES interview_request (id),
    FOREIGN KEY (skill_id) REFERENCES skill (id)
);

-- Связующая таблица для заявок и слотов
CREATE TABLE IF NOT EXISTS interview_request_slot
(
    request_id UUID NOT NULL,
    slot_id    UUID NOT NULL,
    PRIMARY KEY (request_id, slot_id),
    FOREIGN KEY (request_id) REFERENCES interview_request (id),
    FOREIGN KEY (slot_id) REFERENCES interview_slot (id)
);