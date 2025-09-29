-- Расширение для генерации UUID
CREATE
EXTENSION IF NOT EXISTS "uuid-ossp";

-- Пользователи
CREATE TABLE IF NOT EXISTS users
(
    id            UUID PRIMARY KEY         DEFAULT uuid_generate_v4(),
    username      VARCHAR(50)  NOT NULL UNIQUE,
    email         VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at    TIMESTAMP WITH TIME ZONE DEFAULT now(),
    updated_at    TIMESTAMP WITH TIME ZONE DEFAULT now()
);

-- Языки программирования
CREATE TABLE IF NOT EXISTS programming_languages
(
    id   UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(50) NOT NULL UNIQUE
);

-- Заявки на интервью
CREATE TABLE IF NOT EXISTS interview_requests
(
    id                      UUID PRIMARY KEY         DEFAULT uuid_generate_v4(),
    creator_id              UUID NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    programming_language_id UUID NOT NULL REFERENCES programming_languages (id) ON DELETE RESTRICT,
    title                   VARCHAR(255),
    description             TEXT,
    created_at              TIMESTAMP WITH TIME ZONE DEFAULT now()
);

-- Свободные слоты для интервью
CREATE TABLE IF NOT EXISTS interview_slots
(
    id                   UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    interview_request_id UUID                     NOT NULL REFERENCES interview_requests (id) ON DELETE CASCADE,
    start_time           TIMESTAMP WITH TIME ZONE NOT NULL,
    end_time             TIMESTAMP WITH TIME ZONE NOT NULL,
    is_booked            BOOLEAN          DEFAULT FALSE
);

-- Бронирование слота (выбор интервьюером)
CREATE TABLE IF NOT EXISTS slot_bookings
(
    id             UUID PRIMARY KEY         DEFAULT uuid_generate_v4(),
    slot_id        UUID NOT NULL REFERENCES interview_slots (id) ON DELETE CASCADE,
    interviewer_id UUID NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    booked_at      TIMESTAMP WITH TIME ZONE DEFAULT now(),
    UNIQUE (slot_id) -- чтобы один слот мог быть забронирован только один раз
);

-- Сессии интервью (ссылка на видеозвонок)
CREATE TABLE IF NOT EXISTS interview_sessions
(
    id              UUID PRIMARY KEY         DEFAULT uuid_generate_v4(),
    slot_booking_id UUID NOT NULL REFERENCES slot_bookings (id) ON DELETE CASCADE,
    session_link    TEXT NOT NULL,
    created_at      TIMESTAMP WITH TIME ZONE DEFAULT now()
);
