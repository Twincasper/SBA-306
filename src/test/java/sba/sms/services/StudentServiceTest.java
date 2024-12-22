package sba.sms.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sba.sms.models.Student;
import sba.sms.utils.CommandLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
class StudentServiceTest {

    static StudentService studentService;

    @BeforeAll
    static void beforeAll() {
        studentService = new StudentService();
        CommandLine.addData();
    }

    @Test
    void getAllStudents() {

        List<Student> expected = new ArrayList<>(Arrays.asList(
                new Student("naruto.uzumaki@gmail.com", "Naruto Uzumaki", "password"),
                new Student("sasuke.uchiha@gmail.com", "Sasuke Uchiha", "password"),
                new Student("sakura.haruno@gmail.com", "Sakura Haruno", "password"),
                new Student("kakashi.hatake@gmail.com", "Kakashi Hatake", "password"),
                new Student("shikamaru.nara@gmail.com", "Shikamaru Nara", "password"),
                new Student("hinata.hyuga@gmail.com", "Hinata Hyuga", "password")
        ));

        assertThat(studentService.getAllStudents()).hasSameElementsAs(expected);

    }
}