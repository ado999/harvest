INSERT INTO account
	(id, employee_id, first_name, last_name, email, password, enabled)
	VALUES
	(1, null, 'Test1', 'User', 'user1@test.qp', null, true),
	(2, null, 'Test2', 'User', 'user2@test.qp', null, true);

INSERT INTO password_recovery_token
    (id, expiry_date, token, user_id)
    VALUES
    (1, '2022-10-21 22:20:00', 'token1', 1),
    (2, '2022-10-22 22:20:00', 'token2', 2);

ALTER TABLE IF EXISTS password_recovery_token
    ALTER COLUMN id RESTART SET START 3;