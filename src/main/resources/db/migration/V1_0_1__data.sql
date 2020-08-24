INSERT INTO devcamp_db.users
(id, password, user_type, username)
VALUES('0e88d88d-76d1-4c45-9472-c624cc734a16', '12345678', 'TEACHER', 'user123'),
	('7888f423-34ef-4d11-905d-587a3b6a3cf0', 'qwerty123', 'TEACHER', 'egeorgiev'),
	('a868e5aa-081a-4a06-bbf0-093a984c73cb', 'passw0rd', 'STUDENT', 'potato'),
	('0653bd95-3794-4ce7-92ab-fc997a389455', 'hello*there', 'STUDENT', 'annetoo'),
	('7f17db5c-1d3a-4fde-aab1-bb54a6c028ea', '87654321', 'STUDENT', 'merry-becky'),
	('f8cb6c75-fc6e-4bdf-80ed-c770e0eecd4f', 'myverysecretsecret', 'STUDENT', 'greenhorn'),
	('e4b9ab8f-0fc7-4d1d-b15a-279ef3c82314', 'secret-key', 'TEACHER', 'nobleone');

INSERT INTO devcamp_db.students
(user_id, age, first_name, last_name, total_score)
VALUES('0653bd95-3794-4ce7-92ab-fc997a389455', 18, 'Anna', 'Valentinova', -1.00),
	('7f17db5c-1d3a-4fde-aab1-bb54a6c028ea', 21, 'Becky', 'Smith', -1.00),
	('a868e5aa-081a-4a06-bbf0-093a984c73cb', 21, 'Anna', 'Georgieva', -1.00),
	('f8cb6c75-fc6e-4bdf-80ed-c770e0eecd4f', 29, 'Darin', 'Marinov', -1.00);

INSERT INTO devcamp_db.teachers
(user_id, fullname, title)
VALUES('0e88d88d-76d1-4c45-9472-c624cc734a16', 'Jones', 'MR'),
	('7888f423-34ef-4d11-905d-587a3b6a3cf0', 'Elenko Georgiev', 'MR'),
	('e4b9ab8f-0fc7-4d1d-b15a-279ef3c82314', 'Ivanova', 'MRS');
