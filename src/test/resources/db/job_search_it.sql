INSERT INTO employee(id, code, passport_taken)
	VALUES
	(1, 'HUT11111', false), (2, 'SMI22222', false), (3, 'CHA33333', false);

INSERT INTO account
	(id, employee_id, first_name, last_name, email, password, enabled)
	VALUES
	(1, 1, 'Tulley', 'Hutley', 'thutley0@blogtalkradio.com', 'bZ3AouWcINf', true),
    (2, 2, 'Marci', 'Smitherman', 'msmitherman1@example.com', 'uxlJaB', true),
    (3, 3, 'Joelie', 'Chawner', 'jchawner2@liveinternet.ru', 'SLsW5pXW', true),
	(4, null, 'First', 'User', 'user_first@harvest.qp', null, true),
	(5, null, 'Second', 'User', 'user_second@harvest.qp', null, true);

INSERT INTO account_roles
	(user_id, role_id)
	VALUES
	(4, 2), (5, 2);

INSERT INTO location
	(id, creation_date, description, account_id, disabled)
	VALUES
	(1, '2021-01-01 15:00:00', 'Location of First User', 4, false),
	(2, '2021-01-01 15:00:00', 'Location of Second User', 5, false);

INSERT INTO job_type
	(id, default_rate, title, unit, disabled)
	VALUES
	(1, 20, 'za godzinę', 'TIME', false),
	(2, 1.5, 'za opakowanie', 'QUANTITY', false);

INSERT INTO job
	(id, date, job_type_id, quantity, rate, total_amount, employee_id, location_id, account_id)
	VALUES
	(1, '2022-06-07 18:02:31', 1, 8, 20, 160, 1, 1, 4),
	(2, '2022-06-07 18:03:34', 1, 7, 20, 140, 2, 1, 4),
	(3, '2022-06-08 18:02:31', 1, 10, 20, 200, 1, 1, 4),
    (4, '2022-06-08 18:03:34', 1, 9, 20, 180, 2, 2, 4),
	(5, '2022-06-08 15:47:43', 2, 100, 1.5, 150, 3, 1, 5),
	(6, '2022-06-10 15:47:43', 2, 200, 1.5, 300, 3, 1, 4),
	(7, '2022-06-11 15:47:43', 1, 10, 21, 210, 3, 2, 4),
	(8, '2022-06-12 15:47:43', 2, 100, 1.5, 150, 1, 2, 5),
	(9, '2022-06-12 15:47:43', 2, 110, 1.5, 165, 2, 2, 5),
	(10, '2022-06-12 15:47:43', 2, 120, 1.5, 180, 3, 2, 5),
	(11, '2022-06-13 15:47:43', 1, 5, 25, 125, 2, 1, 5),
	(12, '2022-06-13 15:47:43', 1, 4, 25, 100, 3, 2, 4);