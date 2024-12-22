package sba.sms.utils;

import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.services.CourseService;
import sba.sms.services.StudentService;

public class CommandLine {
    private CommandLine(){
    }
    private static final String PASSWORD = "password";


    public static void addData() {
        StudentService studentService = new StudentService();
        CourseService courseService = new CourseService();

        String kakashiInstructor = "Kakashi Sensei";
        String konohamaruInstructor = "Konohamaru Sensei";
        String shinoInstructor = "Shino Aburame";

        studentService.createStudent(new Student("naruto.uzumaki@gmail.com", "Naruto Uzumaki", PASSWORD));
        studentService.createStudent(new Student("sasuke.uchiha@gmail.com", "Sasuke Uchiha", PASSWORD));
        studentService.createStudent(new Student("sakura.haruno@gmail.com", "Sakura Haruno", PASSWORD));
        studentService.createStudent(new Student("kakashi.hatake@gmail.com", "Kakashi Hatake", PASSWORD));
        studentService.createStudent(new Student("shikamaru.nara@gmail.com", "Shikamaru Nara", PASSWORD));
        studentService.createStudent(new Student("hinata.hyuga@gmail.com", "Hinata Hyuga", PASSWORD));

        courseService.createCourse(new Course("Shuriken Throwing", kakashiInstructor));
        courseService.createCourse(new Course("Chakra Expansion", konohamaruInstructor));
        courseService.createCourse(new Course("Shadow Clone Class", shinoInstructor));
        courseService.createCourse(new Course("Tree Running", kakashiInstructor));
        courseService.createCourse(new Course("Get the Bells", kakashiInstructor));
    }
}