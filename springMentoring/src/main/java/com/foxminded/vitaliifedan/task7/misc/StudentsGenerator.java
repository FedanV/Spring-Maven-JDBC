package com.foxminded.vitaliifedan.task7.misc;

import com.foxminded.vitaliifedan.task7.dao.GroupDao;
import com.foxminded.vitaliifedan.task7.dao.StudentDao;
import com.foxminded.vitaliifedan.task7.models.Group;
import com.foxminded.vitaliifedan.task7.models.Student;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class StudentsGenerator {

    private final StudentDao studentDao;
    private final GroupDao groupDao;
    private SecureRandom random;

    private List<String> firstName = Arrays.asList(
            "Olivia", "Emma", "Charlotte", "Amelia", "Ava", "Sophia", "Isabella", "Mia", "Evelyn", "Harper",
            "Luna", "Camila", "Gianna", "Andrea", "Millie", "June", "Khloe", "Callie", "Juliette", "Sage"
    );

    private List<String> lastName = Arrays.asList(
            "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez",
            "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson", "Thomas", "Taylor", "Moore", "Jackson", "Martin"
    );

    public StudentsGenerator(StudentDao studentDao, GroupDao groupDao) {
        this.studentDao = studentDao;
        this.groupDao = groupDao;
        this.random = new SecureRandom(new byte[]{1, 2, 3, 4});
    }

    public void generateStudents(Connection connection, int count) throws SQLException {
        List<Long> groupIds = groupDao.getAll(connection).stream().map(Group::getGroupId).toList();
        for (int i = 0; i < count; i++) {
            studentDao.create(connection, new Student(
                    i,
                    groupIds.get((random.nextInt(groupIds.size()))),
                    firstName.get(random.nextInt(firstName.size())),
                    lastName.get(random.nextInt(lastName.size()))
            ));
        }
    }


}
