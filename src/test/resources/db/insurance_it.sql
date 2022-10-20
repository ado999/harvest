INSERT INTO employee(id, code, passport_taken)
	VALUES
	(1, 'CODE1', false),
	(2, 'CODE2', false);

INSERT INTO insurance (id, valid_from, valid_to, employee_id)
	VALUES
	(1, '2022-05-22 12:50:24', '2023-05-22 12:50:24', 1),
	(2, '2022-08-05 21:39:08', '2023-08-05 21:39:08', 1),
	(3, '2021-08-19 23:51:32', '2022-08-19 23:51:32', 1),
	(4, '2022-08-05 21:39:08', '2023-08-05 21:39:08', 2);

ALTER TABLE IF EXISTS insurance
    ALTER COLUMN id RESTART SET START 5;