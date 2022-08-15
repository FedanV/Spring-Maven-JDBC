package com.foxminded.vitaliifedan.task7;

import com.foxminded.vitaliifedan.task7.dao.CourseDao;
import com.foxminded.vitaliifedan.task7.dao.GroupDao;
import com.foxminded.vitaliifedan.task7.dao.StudentCourseDao;
import com.foxminded.vitaliifedan.task7.dao.StudentDao;
import com.foxminded.vitaliifedan.task7.dao.datasource.DataSource;
import com.foxminded.vitaliifedan.task7.dao.implementations.CourseDaoImpl;
import com.foxminded.vitaliifedan.task7.dao.implementations.GroupDaoImpl;
import com.foxminded.vitaliifedan.task7.dao.implementations.StudentCourseDaoImpl;
import com.foxminded.vitaliifedan.task7.dao.implementations.StudentDaoImpl;
import com.foxminded.vitaliifedan.task7.misc.CoursesGenerator;
import com.foxminded.vitaliifedan.task7.misc.GroupsGenerator;
import com.foxminded.vitaliifedan.task7.misc.StudentsCoursesGenerator;
import com.foxminded.vitaliifedan.task7.misc.StudentsGenerator;
import com.foxminded.vitaliifedan.task7.models.Group;
import com.foxminded.vitaliifedan.task7.models.Student;
import com.foxminded.vitaliifedan.task7.models.StudentCourse;
import com.foxminded.vitaliifedan.task7.utils.SqlUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.foxminded.vitaliifedan.task7.utils.TransactionUtils.*;

public class App implements AutoCloseable {

    private final StudentDao studentDao;
    private final CourseDao courseDao;
    private final StudentCourseDao studentCourseDao;
    private final GroupDao groupDao;

    public App() throws SQLException, IOException {
        SqlUtils.executeSqlScriptFile("task7/init_schema.sql");
        this.studentDao = new StudentDaoImpl();
        this.courseDao = new CourseDaoImpl();
        this.studentCourseDao = new StudentCourseDaoImpl();
        this.groupDao = new GroupDaoImpl();
    }

    public void run() throws SQLException, IOException {
        transaction(connection -> new CoursesGenerator(courseDao).generateCourses(connection));
        transaction(connection -> new GroupsGenerator(groupDao).generateGroups(connection, 10));
        transaction(connection -> new StudentsGenerator(studentDao, groupDao).generateStudents(connection, 200));
        transaction(connection -> new StudentsCoursesGenerator(studentDao, courseDao, studentCourseDao).generateStudentsCourses(connection));
    }

    public StudentDao getStudentDao() {
        return studentDao;
    }

    public CourseDao getCourseDao() {
        return courseDao;
    }

    public StudentCourseDao getStudentCourseDao() {
        return studentCourseDao;
    }

    public GroupDao getGroupDao() {
        return groupDao;
    }

    @Override
    public void close() throws Exception {
        SqlUtils.executeSqlScriptFile("task7/drop_schema.sql");
    }

    public static void main(String[] args) throws Exception {
        App app = new App();
        app.run();

//      Find all groups with less or equals student count
        List<Group> allGroupsWithLessOrEqualStudentCount = app.getGroupDao()
                .findAllGroupsWithLessOrEqualStudentCount(DataSource.getConnection(), 20);
        System.out.println(allGroupsWithLessOrEqualStudentCount);

//      Find all students related to course with given name
        List<Student> fashion = app.getStudentDao().findAllStudentsByCourseName(DataSource.getConnection(), "Fashion");
        System.out.println(fashion);

//      Add new student
        Student student = new Student(4, "Nick", "Johny");
        transaction(connection -> app.getStudentDao().create(connection, student));

//      Delete student by STUDENT_ID
        transaction(connection -> app.getStudentDao().deleteById(connection, 200));

//      Add a student to the course (from a list)
        transaction(connection -> app.getStudentCourseDao().createStudentCourse(connection, new StudentCourse(199, 9)));

//      Remove the student from one of his or her courses
        transaction(connection -> app.getStudentCourseDao().removeStudentByCourseName(connection, 190, "Math"));
    }
}
