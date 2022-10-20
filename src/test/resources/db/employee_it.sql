INSERT INTO employee(id, code, passport_taken)
	VALUES
	(1, 'HUT11111', false),
	(2, 'SMI22222', false),
	(3, 'CHA33333', false),
	(4, 'SAV44444', false),
	(5, 'TOF55555', false);

ALTER TABLE IF EXISTS employee
    ALTER COLUMN id RESTART SET START 6;

INSERT INTO account
	(id, employee_id, first_name, last_name, email, password, enabled)
	VALUES
	(1, 1, 'Tulley', 'Hutley', 'thutley0@blogtalkradio.com', 'bZ3AouWcINf', true),
	(2, 2, 'Marci', 'Smitherman', 'msmitherman1@example.com', 'uxlJaB', true),
	(3, 3, 'Joelie', 'Chawner', 'jchawner2@liveinternet.ru', 'SLsW5pXW', true),
	(4, 4, 'Mollee', 'Savin', 'msavin3@oakley.com', 'G1NicOp', false),
	(5, 5, 'Jennilee', 'Tofanini', 'jtofanini4@angelfire.com', 'K8dl4sAWBfa3', false);

ALTER TABLE IF EXISTS account
    ALTER COLUMN id RESTART SET START 6;