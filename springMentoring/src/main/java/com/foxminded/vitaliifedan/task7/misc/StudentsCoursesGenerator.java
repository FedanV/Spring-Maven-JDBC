package com.foxminded.vitaliifedan.task7.misc;

import com.foxminded.vitaliifedan.task7.dao.CourseDao;
import com.foxminded.vitaliifedan.task7.dao.StudentCourseDao;
import com.foxminded.vitaliifedan.task7.dao.StudentDao;
import com.foxminded.vitaliifedan.task7.models.Course;
import com.foxminded.vitaliifedan.task7.models.Student;
import com.foxminded.vitaliifedan.task7.models.StudentCourse;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class StudentsCoursesGenerator {

    private final StudentDao studentDao;
    private final CourseDao courseDao;
    private final StudentCourseDao studentCourseDao;
    private SecureRandom random;

    public StudentsCoursesGenerator(StudentDao studentDao, CourseDao courseDao, StudentCourseDao studentCourseDao) {
        this.studentDao = studentDao;
        this.courseDao = courseDao;
        this.studentCourseDao = studentCourseDao;
        this.random = new SecureRandom(new byte[]{1, 2, 3, 4});
    }

    public void generateStudentsCourses(Connection connection) throws SQLException {
        List<Long> studentIds = studentDao.getAll(connection).stream().map(Student::getStudentId).toList();
        List<Long> courseIds = courseDao.getAll(connection).stream().map(Course::getCourseId).toList();
        for (Long studentId : studentIds) {
            for (int j = 0; j < (random.nextInt(2) + 1); j++) {
                int randomCourse = random.nextInt(courseIds.size());
                Optional<StudentCourse> existingStudent = studentCourseDao.
                        findByStudentIdAndCourseId(connection, studentId, courseIds.get(randomCourse));
                if (existingStudent.isEmpty()) {
                    studentCourseDao.createStudentCourse(connection, new StudentCourse(studentId,
                            Math.toIntExact(courseIds.get(randomCourse))));
                }
            }
        }
    }
}
