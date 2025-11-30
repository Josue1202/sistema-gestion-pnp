-- Actualizar contraseñas con BCrypt hash
-- Hash BCrypt de "admin123" y "test123"
UPDATE usuario 
SET password_hash = '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy'
WHERE username IN ('admin', 'test001', 'test002', 'test003');
