-- Вставляем роли для безопасности
INSERT INTO role (name, description)
VALUES ('ROLE_ADMIN', 'Администратор'),
       ('ROLE_USER', 'Пользователь')
ON CONFLICT (name) DO NOTHING;

-- Вставляем базовые роли для собеседований
INSERT INTO interview_role (name)
VALUES ('Кандидат'),
       ('Интервьюер')
ON CONFLICT DO NOTHING;

-- Вставляем базовые навыки
INSERT INTO skill (name)
VALUES ('Java'),
       ('Spring Boot'),
       ('PostgreSQL'),
       ('Docker'),
       ('Kubernetes'),
       ('React'),
       ('TypeScript')
ON CONFLICT DO NOTHING;

-- Вставляем грейды
INSERT INTO grade (name)
VALUES ('Junior'),
       ('Middle'),
       ('Senior'),
       ('Team Lead')
ON CONFLICT DO NOTHING;