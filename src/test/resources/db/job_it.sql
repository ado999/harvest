INSERT INTO employee(id, code, passport_taken)
	VALUES
	(1, 'HUT11111', false), (2, 'SMI22222', false);

INSERT INTO account
	(id, employee_id, first_name, last_name, email, password, enabled)
	VALUES
	(1, 1, 'Tulley', 'Hutley', 'thutley0@blogtalkradio.com', 'bZ3AouWcINf', true),
	(2, 2, 'Marci', 'Smitherman', 'msmitherman1@example.com', 'uxlJaB', true),
	(3, null, 'Test', 'user', 'test@harvest.qp', '$2a$10$U8T5nshakO1WOfWfp/QD5u1NneOXM998iqVMpPVdjzrJLFrddyolG', true);

INSERT INTO account_roles
	(user_id, role_id)
	VALUES
	(3, 2);

INSERT INTO location
	(id, creation_date, description, account_id, disabled)
	VALUES
	(1, '2021-01-01 15:00:00', 'Location name', 3, false);

INSERT INTO job_type
	(id, default_rate, title, unit, disabled)
	VALUES
	(1, 20, 'za godzinę', 'TIME', false);

INSERT INTO job
	(id, date, job_type_id, quantity, rate, total_amount, employee_id, location_id, account_id)
	VALUES
	(1, '2022-08-07 10:02:31', 1, 9, 20, 180, 1, 1, 1),
	(2, '2022-06-27 00:02:34', 1, 10, 20, 200, 1, 1, 1),
	(3, '2022-06-20 22:47:43', 1, 8, 20, 160, 2, 1, 1);

ALTER TABLE IF EXISTS job
    ALTER COLUMN id RESTART SET START 4;