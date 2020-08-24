USE devcamp_db;

-- devcamp_db.users definition

CREATE TABLE `users` (
  `id` varchar(255) NOT NULL,
  `password` varchar(20) NOT NULL,
  `user_type` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- devcamp_db.students definition

CREATE TABLE `students` (
  `user_id` varchar(255) NOT NULL,
  `age` int DEFAULT '25',
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `total_score` decimal(3,2) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `FKdt1cjx5ve5bdabmuuf3ibrwaq` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- devcamp_db.teachers definition

CREATE TABLE `teachers` (
  `user_id` varchar(255) NOT NULL,
  `fullname` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `FKb8dct7w2j1vl1r2bpstw5isc0` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- devcamp_db.subjects definition

CREATE TABLE `subjects` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `teacher_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsjy6ghvvelraa2w9mhv3bbnys` (`teacher_id`),
  CONSTRAINT `FKsjy6ghvvelraa2w9mhv3bbnys` FOREIGN KEY (`teacher_id`) REFERENCES `teachers` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- devcamp_db.subjects_students definition

CREATE TABLE `subjects_students` (
  `subject_id` bigint NOT NULL,
  `student_id` varchar(255) NOT NULL,
  PRIMARY KEY (`subject_id`,`student_id`),
  KEY `FKfwe627h9l4w4o56kssl2ijf06` (`student_id`),
  CONSTRAINT `FK8b0cg3t7c7wj4g3udq0g4wqm5` FOREIGN KEY (`subject_id`) REFERENCES `subjects` (`id`),
  CONSTRAINT `FKfwe627h9l4w4o56kssl2ijf06` FOREIGN KEY (`student_id`) REFERENCES `students` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;