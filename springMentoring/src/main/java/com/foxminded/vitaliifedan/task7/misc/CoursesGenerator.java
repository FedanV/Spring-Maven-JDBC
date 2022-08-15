package com.foxminded.vitaliifedan.task7.misc;

import com.foxminded.vitaliifedan.task7.dao.CourseDao;
import com.foxminded.vitaliifedan.task7.models.Course;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class CoursesGenerator {

    private final CourseDao courseDao;

    public CoursesGenerator(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    public void generateCourses(Connection connection) throws SQLException {
        for(int i = 0; i < generateCourseName().size(); i++) {
            courseDao.create(connection, new Course(i, generateCourseName().get(i),
                    generateCourseName().get(i) + " course"));
        }
    }

    public List<String > generateCourseName() {
        return Arrays.asList(
                "Math", "History", "Biology", "Social", "Fashion",
                "Management", "Business", "Travel", "Tourism", "Performing"
        );
    }

}
