INSERT INTO account
	(id, employee_id, first_name, last_name, email, password, enabled)
	VALUES
	(1, null, 'First', 'User', 'email1@test.qp', null, true),
	(2, null, 'Second', 'User', 'email2@test.qp', null, true),
	(3, null, 'Third', 'User', 'email3@test.qp', null, true);

INSERT INTO location
	(id, creation_date, description, account_id, disabled)
	VALUES
	(1, '2021-01-01 15:00:00', 'Location 1', 1, false),
	(2, '2021-01-01 15:00:00', 'Location 2', 2, false),
	(3, '2021-01-01 15:00:00', 'Location 3', 3, true),
	(4, '2021-01-01 15:00:00', 'Location 4', 1, false),
	(5, '2020-01-02 15:00:00', 'Location 5', 2, true);

ALTER TABLE IF EXISTS location
    ALTER COLUMN id RESTART SET START 6;