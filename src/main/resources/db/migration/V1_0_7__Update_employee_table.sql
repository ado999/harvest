ALTER TABLE IF EXISTS employee
    ADD COLUMN balance numeric(19, 2) NOT NULL DEFAULT 0;

UPDATE employee
	SET balance = tab1.balance
	FROM (
		SELECT tab.employee_id, SUM(tab.total_amount) AS balance
		FROM (
			SELECT employee_id, total_amount
			FROM job
			UNION
			SELECT employee_id, amount
			FROM payment
		) tab
		GROUP BY employee_id
	) AS tab1
	WHERE employee.id = tab1.employee_id;