DROP TABLE IF EXISTS students_courses;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS groups;
DROP TABLE IF EXISTS courses;

CREATE TABLE IF NOT EXISTS groups
(
    group_id   int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    group_name varchar(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS students
(
    student_id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    group_id   int REFERENCES groups (group_id) ON DELETE SET NULL ,
    first_name varchar(100) NOT NULL,
    last_name  varchar(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS courses
(
    course_id          int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    course_name        varchar(100) NOT NULL UNIQUE,
    course_description varchar(200)
);

CREATE TABLE IF NOT EXISTS students_courses
(
    student_id int REFERENCES students (student_id) ON DELETE CASCADE,
    course_id  int REFERENCES courses (course_id) ON DELETE CASCADE,
    PRIMARY KEY (student_id, course_id)
);