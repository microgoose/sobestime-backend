-- 1. Сначала добавляем администратора в user_principal (security)
INSERT INTO user_principal (
    id,
    email,
    username,
    password,
    enabled,
    account_non_expired,
    account_non_locked,
    credentials_non_expired,
    created_at,
    updated_at
)
VALUES (
    'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11'::uuid,
    'admin@example.com',
    'admin',
    '$2a$12$GZv/Z5ld8.6KiBSGIPT5VeuxeV6HRmZeyS8tQWeSjdr8rt0qhWx5W',
    true,
    true,
    true,
    true,
    now(),
    now()
)
ON CONFLICT (email) DO NOTHING;

-- 2. Назначаем роль ROLE_ADMIN пользователю
INSERT INTO user_principal_role (user_principal_id, role_id)
SELECT
    'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11'::uuid,
    (SELECT id FROM role WHERE name = 'ROLE_ADMIN')
WHERE NOT EXISTS (
    SELECT 1 FROM user_principal_role
    WHERE user_principal_id = 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11'::uuid
    AND role_id = (SELECT id FROM role WHERE name = 'ROLE_ADMIN')
);

-- 3. Добавляем того же пользователя в interview_user для функционала собеседований
INSERT INTO interview_user (
    id,
    username,
    avatar_url
)
VALUES (
    'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11'::uuid,
    'admin',
    NULL
)
ON CONFLICT (id) DO NOTHING;