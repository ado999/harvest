INSERT INTO job_type
	(id, default_rate, title, unit, disabled)
	VALUES
	(1, 1, 'Job Type 1', 'TIME', false),
	(2, 2, 'Job Type 2', 'AREA', false),
	(3, 3, 'Job Type 3', 'QUANTITY', false),
	(4, 3, 'Job Type 4', 'WEIGHT', true);

ALTER TABLE IF EXISTS job_type
    ALTER COLUMN id RESTART SET START 5;