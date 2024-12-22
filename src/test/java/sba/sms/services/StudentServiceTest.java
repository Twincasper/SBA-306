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
    static List<Student> testStudents;

    @BeforeAll
    static void beforeAll() {
        studentService = new StudentService();

        CommandLine.addData();
    }

    @Test
    void getAllStudents() {
        // Fetch all students
        List<Student> actualStudents = studentService.getAllStudents();

        // Ensure the database contains the expected students
        List<Student> expectedStudents = List.of(
                new Student("naruto.uzumaki@gmail.com", "Naruto Uzumaki", "password"),
                new Student("sasuke.uchiha@gmail.com", "Sasuke Uchiha", "password"),
                new Student("sakura.haruno@gmail.com", "Sakura Haruno", "password"),
                new Student("kakashi.hatake@gmail.com", "Kakashi Hatake", "password"),
                new Student("shikamaru.nara@gmail.com", "Shikamaru Nara", "password"),
                new Student("hinata.hyuga@gmail.com", "Hinata Hyuga", "password")
        );

        // Assert that the fetched students match the expected ones
        assertThat(actualStudents).hasSameElementsAs(expectedStudents);
    }

    @AfterAll
    static void afterAll() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var tx = session.beginTransaction();
            session.createMutationQuery("DELETE FROM Student").executeUpdate();
            tx.commit();
        }
    }
}
