INSERT INTO employee(id, code, passport_taken)
	VALUES
	(1, 'EMP11111', false),
	(2, 'EMP22222', false);

INSERT INTO account
	(id, employee_id, first_name, last_name, email, password, enabled)
	VALUES
	(1, 1, '1st', 'Employee', '1st@employee.qp', null, true),
	(2, 2, '2nd', 'Employee', '2nd@employee.qp', null, true),
    (3, null, 'Staff', 'Test', 'staff@test.qp', null, true);

INSERT INTO account_roles
	(user_id, role_id)
	VALUES
	(3, 2);

INSERT INTO payment
	(id, employee_id, account_id, amount, created_date)
	VALUES
	(1, 1, 3, -100.00, '2022-08-21 20:29:35.422117'),
	(2, 1, 3, -200.00, '2022-08-22 20:29:35.422117'),
	(3, 2, 3, -300.00, '2022-08-23 20:29:35.422117'),
	(4, 2, 3, -400.00, '2022-08-24 20:29:35.422117'),
	(5, 2, 3, -500.00, '2022-08-24 20:29:35.422117');

ALTER TABLE IF EXISTS payment
    ALTER COLUMN id RESTART SET START 6;