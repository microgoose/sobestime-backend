-- Расширение для генерации UUID
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

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
    title       VARCHAR(255) NOT NULL,
    status      VARCHAR(100) NOT NULL,
    description TEXT,
    creator_id  UUID         NOT NULL,
    role_id     UUID         NOT NULL,
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
    booker_id  UUID                     NOT NULL,
    FOREIGN KEY (request_id) REFERENCES interview_request (id),
    FOREIGN KEY (booker_id) REFERENCES interview_user (id)
);

CREATE TABLE IF NOT EXISTS scheduled_interview
(
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    status       VARCHAR(100) NOT NULL,
    meeting_link TEXT,
    request_id   UUID         NOT NULL,
    slot_id      UUID         NOT NULL,
    FOREIGN KEY (request_id) REFERENCES interview_request (id),
    FOREIGN KEY (slot_id) REFERENCES interview_slot (id)
);

CREATE TABLE IF NOT EXISTS interview_request_grade
(
    request_id UUID NOT NULL,
    grade_id   UUID NOT NULL,
    PRIMARY KEY (request_id, grade_id),
    FOREIGN KEY (request_id) REFERENCES interview_request (id),
    FOREIGN KEY (grade_id) REFERENCES grade (id)
);

CREATE TABLE IF NOT EXISTS interview_request_skill
(
    request_id UUID NOT NULL,
    skill_id   UUID NOT NULL,
    PRIMARY KEY (request_id, skill_id),
    FOREIGN KEY (request_id) REFERENCES interview_request (id),
    FOREIGN KEY (skill_id) REFERENCES skill (id)
);