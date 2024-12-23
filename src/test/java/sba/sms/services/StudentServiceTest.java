package sba.sms.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sba.sms.models.Course;
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

        List<Student> actualStudents = studentService.getAllStudents();

        List<Student> expectedStudents = List.of(
                new Student("naruto.uzumaki@gmail.com", "Naruto Uzumaki", "password"),
                new Student("sasuke.uchiha@gmail.com", "Sasuke Uchiha", "password"),
                new Student("sakura.haruno@gmail.com", "Sakura Haruno", "password"),
                new Student("kakashi.hatake@gmail.com", "Kakashi Hatake", "password"),
                new Student("shikamaru.nara@gmail.com", "Shikamaru Nara", "password"),
                new Student("hinata.hyuga@gmail.com", "Hinata Hyuga", "password")
        );

        assertThat(actualStudents).hasSameElementsAs(expectedStudents);
    }

    @Test
    void testStudentCourseRelationship() {
        // Create a student
        Student student = new Student("rock.lee@gmail.com", "Rock Lee", "password");
        studentService.createStudent(student);

        // Create a course
        Course course = new Course("Taijutsu", "Might Guy");
        CourseService courseService = new CourseService();
        courseService.createCourse(course);

        student.addCourse(course);
        course.addStudent(student);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(student);
            session.persist(course);
            tx.commit();
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Student fetchedStudent = session.get(Student.class, "rock.lee@gmail.com");
            assertThat(fetchedStudent.getCourses()).contains(course);
        }
    }

    @AfterAll
    static void cleanup() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.createMutationQuery("DELETE FROM Course").executeUpdate();
            session.createMutationQuery("DELETE FROM Student").executeUpdate();
            tx.commit();
        }
    }
}
