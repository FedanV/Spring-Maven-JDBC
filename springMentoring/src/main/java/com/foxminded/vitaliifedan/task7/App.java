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
import com.foxminded.vitaliifedan.task7.models.Course;
import com.foxminded.vitaliifedan.task7.models.Group;
import com.foxminded.vitaliifedan.task7.models.Student;

import com.foxminded.vitaliifedan.task7.models.StudentCourse;
import com.foxminded.vitaliifedan.task7.utils.ResourcesUtils;
import com.foxminded.vitaliifedan.task7.utils.SqlUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Scanner;

import static com.foxminded.vitaliifedan.task7.utils.TransactionUtils.*;

public class App implements AutoCloseable {

    private final StudentDao studentDao;
    private final CourseDao courseDao;
    private final StudentCourseDao studentCourseDao;
    private final GroupDao groupDao;
    private final DataSource dataSource;

    public App(DataSource dataSource) throws SQLException, IOException {
        this.dataSource = dataSource;
        SqlUtils.executeSqlScriptFile(dataSource, "task7/init_schema.sql");
        this.studentDao = new StudentDaoImpl();
        this.courseDao = new CourseDaoImpl();
        this.studentCourseDao = new StudentCourseDaoImpl();
        this.groupDao = new GroupDaoImpl();
    }

    public void run() throws SQLException, IOException {
        transaction(dataSource, connection -> {
            new CoursesGenerator(courseDao).generateCourses(connection);
            new GroupsGenerator(groupDao).generateGroups(connection, 10);
            new StudentsGenerator(studentDao, groupDao).generateStudents(connection, 200);
            new StudentsCoursesGenerator(studentDao, courseDao, studentCourseDao).generateStudentsCourses(connection);
        });
        startMenu();
        try (Scanner sc = new Scanner(System.in)) {
            int point = checkInputNumber(sc);
            while (point != 7) {
                switch (point) {
                    case 1 -> point = getViewFindAllGroupsWithLessOrEqualsStudentsCount(sc);
                    case 2 -> point = getViewFindAllStudentsRelatedToCourseWithName(sc);
                    case 3 -> point = getViewAddNewStudent(sc);
                    case 4 -> point = getViewDeleteStudentById(sc);
                    case 5 -> point = getViewAddStudentToCourse(sc);
                    case 6 -> point = getViewRemoveStudentFromCourse(sc);
                    default -> {
                        System.out.println("You have entered a non-existent menu item");
                        point = getPoint(sc);
                    }
                }
            }
        }
    }

    private int getViewRemoveStudentFromCourse(Scanner sc) throws SQLException {
        System.out.println("You choose -> Remove the student from one of his or her courses");
        System.out.println("Enter student id: ");
        long studentId = checkInputNumber(sc);
        if (!checkStudentIdInDatabase(studentId)) {
            System.out.println("This student doesn't exist");
        } else {
            System.out.println("Choose course name from the list: ");
            String courseName = getCourseName(sc, getCourseNameList());
            studentCourseDao.removeStudentByCourseName(dataSource.getConnection(), studentId, courseName);
            System.out.println("Student with id " + studentId + " was removed from course " + courseName);
        }
        return getPoint(sc);
    }

    private int getViewAddStudentToCourse(Scanner sc) throws SQLException {
        System.out.println("You choose -> Add a student to the course (from a list)");
        System.out.println("Enter enter student id: ");
        long studentId = checkInputNumber(sc);
        if (!checkStudentIdInDatabase(studentId)) {
            System.out.println("This student doesn't exist");
        } else {
            System.out.println("Choose course id from list: ");
            getCourseNameList();
            int courseId = checkInputNumber(sc);
            if (checkCourseIdInDatabase(courseId)) {
                studentCourseDao.createStudentCourse(dataSource.getConnection(),
                        new StudentCourse(studentId, courseId));
                System.out.println("Course was added to student");
            } else {
                System.out.println("You choose course not from list");
            }
        }
        return getPoint(sc);
    }

    private int getViewDeleteStudentById(Scanner sc) throws SQLException {
        System.out.println("You choose -> Delete student by STUDENT_ID");
        System.out.println("Enter enter student id: ");
        long studentId = checkInputNumber(sc);
        if (!checkStudentIdInDatabase(studentId)) {
            System.out.println("This student doesn't exist");
        } else {
            studentDao.deleteById(dataSource.getConnection(), studentId);
            System.out.println("Student was deleted");
        }
        return getPoint(sc);
    }

    private int getViewAddNewStudent(Scanner sc) throws SQLException {
        System.out.println("You choose -> Add new student");
        System.out.println("Enter firstName: ");
        String firstName = checkInputString(sc);
        sc.nextLine();
        System.out.println("Enter lastName: ");
        String lastName = checkInputString(sc);
        System.out.println("Enter group id from list: ");
        showAllGroupsFromDatabase();
        int groupId = checkInputNumber(sc);
        studentDao.create(dataSource.getConnection(), new Student(groupId, firstName, lastName));
        System.out.println("Student was created");
        return getPoint(sc);
    }

    private int getViewFindAllStudentsRelatedToCourseWithName(Scanner sc) throws SQLException {
        System.out.println("You choose -> Find all students related to course with given name");
        System.out.println("Enter course name from the list: ");
        String courseName = getCourseName(sc, getCourseNameList());
        List<Student> students = studentDao.findAllStudentsByCourseName(dataSource.getConnection(), courseName);
        System.out.println("Your result: ");
        for (Student s : students) {
            System.out.println("Id: " + s.getStudentId() + "; FirstName: " + s.getFirstName() + "; LastName: " + s.getLastName());
        }
        return getPoint(sc);
    }

    private int getViewFindAllGroupsWithLessOrEqualsStudentsCount(Scanner sc) throws SQLException {
        System.out.println("You choose -> Find all groups with less or equals student count");
        System.out.println("Enter count of students:");
        int groupCount = checkInputNumber(sc);
        List<Group> groups = groupDao.findAllGroupsWithLessOrEqualStudentCount(dataSource.getConnection(), groupCount);
        System.out.println("Your result: ");
        for (Group group : groups) {
            System.out.println(group.getGroupName());
        }
        return getPoint(sc);
    }

    private void showAllGroupsFromDatabase() throws SQLException {
        List<Group> allGroups = groupDao.getAll(dataSource.getConnection());
        for (Group group : allGroups) {
            System.out.println(group.getGroupId() + ". " + group.getGroupName());
        }
    }

    private List<String> getCourseNameList() throws SQLException {
        List<String> getAllCoursesName = courseDao.getAll(dataSource.getConnection())
                .stream()
                .map(Course::getCourseName)
                .toList();
        for (int i = 0; i < getAllCoursesName.size(); i++) {
            System.out.println(i + 1 + ". " + getAllCoursesName.get(i));
        }
        return getAllCoursesName;
    }

    private boolean checkCourseIdInDatabase(long id) throws SQLException {
        Optional<Course> existingCourse = courseDao.getAll(dataSource.getConnection())
                .stream()
                .filter(course -> course.getCourseId() == id)
                .findFirst();
        return existingCourse.isPresent();
    }

    private boolean checkStudentIdInDatabase(long id) throws SQLException {
        List<Long> studentIds = studentDao.getAll(dataSource.getConnection()).stream().map(Student::getStudentId).toList();
        return studentIds.contains(id);
    }

    private int getPoint(Scanner sc) {
        System.out.println();
        System.out.println("What do you want to do following?");
        startMenu();
        return checkInputNumber(sc);
    }

    private int checkInputNumber(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.println("Enter the number");
            sc.nextLine();
        }
        return sc.nextInt();
    }

    private String checkInputString(Scanner sc) {
        while (!sc.hasNextLine()) {
            System.out.println("Enter Name");
            sc.nextLine();
        }
        return sc.nextLine().trim();
    }

    private String getCourseName(Scanner sc, List<String> coursesName) {
        String course = checkInputString(sc);
        while (!coursesName.contains(course)) {
            System.out.println("You need to enter course from the list!");
            course = checkInputString(sc);
        }
        return course;
    }

    private void startMenu() {
        System.out.println(
                "Choose the number of the item you want to do:\n" +
                        "1. Find all groups with less or equals student count\n" +
                        "2. Find all students related to course with given name\n" +
                        "3. Add new student\n" +
                        "4. Delete student by STUDENT_ID\n" +
                        "5. Add a student to the course (from a list)\n" +
                        "6. Remove the student from one of his or her courses\n" +
                        "7. Exit"
        );
    }

    @Override
    public void close() throws Exception {
        SqlUtils.executeSqlScriptFile(dataSource, "task7/drop_schema.sql");
    }

    public static void main(String[] args) throws Exception {
        Properties properties = ResourcesUtils.loadPropertiesFromResources("task7/database.properties");
        DataSource dataSource = new DataSource(properties);
        try(App app = new App(dataSource)){
            app.run();
        }
    }
}
