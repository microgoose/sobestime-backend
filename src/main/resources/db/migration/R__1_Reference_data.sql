-- TODO continuous execution?

-- Вставляем роли для безопасности
INSERT INTO role (id, name, description)
VALUES ('21e20abf-c8b9-4345-8a77-18d4dce34108'::uuid, 'ROLE_ADMIN', 'Администратор'),
       ('21e20abf-c8b9-4345-8a77-18d4dce34109'::uuid, 'ROLE_USER', 'Пользователь')
ON CONFLICT (name) DO NOTHING;

-- Вставляем базовые роли для собеседований
INSERT INTO interview_role (id, name)
VALUES ('21e20abf-c8b9-4345-8a77-18d4dce34110'::uuid, 'Кандидат'),
       ('21e20abf-c8b9-4345-8a77-18d4dce34111'::uuid, 'Интервьюер')
ON CONFLICT DO NOTHING;

-- Вставляем базовые навыки
INSERT INTO skill (id, name)
VALUES ('21e20abf-c8b9-4345-8a77-18d4dce34112'::uuid, 'Java'),
       ('21e20abf-c8b9-4345-8a77-18d4dce34113'::uuid, 'Spring Boot'),
       ('21e20abf-c8b9-4345-8a77-18d4dce34114'::uuid, 'PostgreSQL'),
       ('21e20abf-c8b9-4345-8a77-18d4dce34115'::uuid, 'Docker'),
       ('21e20abf-c8b9-4345-8a77-18d4dce34116'::uuid, 'Kubernetes'),
       ('21e20abf-c8b9-4345-8a77-18d4dce34117'::uuid, 'React'),
       ('21e20abf-c8b9-4345-8a77-18d4dce34118'::uuid, 'TypeScript')
ON CONFLICT DO NOTHING;

-- Вставляем грейды
INSERT INTO grade (id, name)
VALUES ('21e20abf-c8b9-4345-8a77-18d4dce34119'::uuid, 'Junior'),
       ('21e20abf-c8b9-4345-8a77-18d4dce34120'::uuid, 'Middle'),
       ('21e20abf-c8b9-4345-8a77-18d4dce34121'::uuid, 'Senior'),
       ('21e20abf-c8b9-4345-8a77-18d4dce34122'::uuid, 'Team Lead')
ON CONFLICT DO NOTHING;