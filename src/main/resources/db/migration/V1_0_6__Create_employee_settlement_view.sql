CREATE OR REPLACE VIEW employee_settlement AS
    WITH jobs_payments AS (
	    SELECT employee_id, location_id, account_id, total_amount, date
	    FROM job
	    UNION
	    SELECT employee_id, null, account_id, amount, created_date
    	FROM payment
    )
    SELECT *, SUM(total_amount) over (PARTITION BY employee_id ORDER BY date) AS balance, ROW_NUMBER() OVER (ORDER BY date) AS id
    FROM jobs_payments
    ORDER BY date;