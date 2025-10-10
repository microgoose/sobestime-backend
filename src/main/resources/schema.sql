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


-- Языки программирования
CREATE TABLE IF NOT EXISTS programming_language
(
    id   UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(50) NOT NULL UNIQUE
);

-- Заявки на интервью
CREATE TABLE IF NOT EXISTS interview_request
(
    id                      UUID PRIMARY KEY         DEFAULT uuid_generate_v4(),
    creator_id              UUID NOT NULL,
    programming_language_id UUID NOT NULL REFERENCES programming_language (id) ON DELETE RESTRICT,
    title                   VARCHAR(255),
    description             TEXT,
    created_at              TIMESTAMP WITH TIME ZONE DEFAULT now()
);

-- Свободные слоты для интервью
CREATE TABLE IF NOT EXISTS interview_slot
(
    id                   UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    interview_request_id UUID                     NOT NULL REFERENCES interview_request (id) ON DELETE CASCADE,
    start_time           TIMESTAMP WITH TIME ZONE NOT NULL,
    end_time             TIMESTAMP WITH TIME ZONE NOT NULL,
    is_booked            BOOLEAN          DEFAULT FALSE
);

-- Бронирование слота (выбор интервьюером)
CREATE TABLE IF NOT EXISTS slot_booking
(
    id             UUID PRIMARY KEY         DEFAULT uuid_generate_v4(),
    slot_id        UUID NOT NULL REFERENCES interview_slot (id) ON DELETE CASCADE,
    interviewer_id UUID NOT NULL,
    booked_at      TIMESTAMP WITH TIME ZONE DEFAULT now(),
    UNIQUE (slot_id) -- чтобы один слот мог быть забронирован только один раз
);

-- Сессии интервью (ссылка на видеозвонок)
CREATE TABLE IF NOT EXISTS interview_session
(
    id              UUID PRIMARY KEY         DEFAULT uuid_generate_v4(),
    slot_booking_id UUID NOT NULL REFERENCES slot_booking (id) ON DELETE CASCADE,
    session_link    TEXT NOT NULL,
    created_at      TIMESTAMP WITH TIME ZONE DEFAULT now()
);
