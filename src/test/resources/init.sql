INSERT INTO role(id, name)
	VALUES
	(1, 'ROLE_ADMIN'), (2, 'ROLE_STAFF'), (3, 'ROLE_USER'), (4, 'ROLE_TEST');

INSERT INTO account(id, email, enabled, first_name, last_name, password)
    VALUES
    (1, 'staff@harvest.qp', true, 'Staff', 'Test', '$2a$10$U8T5nshakO1WOfWfp/QD5u1NneOXM998iqVMpPVdjzrJLFrddyolG');

INSERT INTO account_roles (user_id, role_id)
	VALUES
	(1, 2);