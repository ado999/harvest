ALTER TABLE IF EXISTS employee
    ADD COLUMN phone_number character varying;

ALTER TABLE IF EXISTS employee
    ADD COLUMN passport_taken boolean NOT NULL DEFAULT False;

--UPDATE employee
--    SET passport_taken = false;