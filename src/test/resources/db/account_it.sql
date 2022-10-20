INSERT INTO account
	(id, employee_id, first_name, last_name, email, password, enabled)
	VALUES
	(1, null, 'Tulley', 'Hutley', 'thutley0@blogtalkradio.com', 'bZ3AouWcINf', true),
	(2, null, 'Marci', 'Smitherman', 'msmitherman1@example.com', 'uxlJaB', true);

ALTER TABLE IF EXISTS account
    ALTER COLUMN id RESTART SET START 3;