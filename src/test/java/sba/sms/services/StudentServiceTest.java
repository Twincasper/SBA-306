package sba.sms.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sba.sms.models.Student;
import sba.sms.utils.CommandLine;
import sba.sms.utils.HibernateUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
class StudentServiceTest {

    static StudentService studentService;
    static List<Student> testStudents; // Static list to track test students

    @BeforeAll
    static void beforeAll() {
        studentService = new StudentService();
        CommandLine.addData(); // Assuming this adds initial data

        // Initialize test students
        testStudents = Arrays.asList(
                new Student("naruto.uzumaki@gmail.com", "Naruto Uzumaki", "password"),
                new Student("sasuke.uchiha@gmail.com", "Sasuke Uchiha", "password"),
                new Student("sakura.haruno@gmail.com", "Sakura Haruno", "password"),
                new Student("kakashi.hatake@gmail.com", "Kakashi Hatake", "password"),
                new Student("shikamaru.nara@gmail.com", "Shikamaru Nara", "password"),
                new Student("hinata.hyuga@gmail.com", "Hinata Hyuga", "password")
        );

        // Save all test students to the database using a stream
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            testStudents.stream().forEach(session::persist); // Stream and save each student
            tx.commit();
        }
    }

    @Test
    void getAllStudents() {
        // Ensure all students are present in the database
        assertThat(studentService.getAllStudents()).hasSameElementsAs(testStudents);
    }

    @AfterAll
    static void afterAll() {
        // Remove all test students from the database using a stream
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            testStudents.stream().forEach(session::remove); // Stream and delete each student
            tx.commit();
        }
    }
}
