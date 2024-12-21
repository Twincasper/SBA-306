package sba.sms.services;

import lombok.extern.java.Log;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import sba.sms.dao.StudentI;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * StudentService is a concrete class. This class implements the
 * StudentI interface, overrides all abstract service methods and
 * provides implementation for each method. Lombok @Log used to
 * generate a logger file.
 */

public class StudentService implements StudentI {

    @Override
    public List<Student> getAllStudents() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<Student> students = new ArrayList<>();
        Query<Student> studentQuery = session.createQuery("from Student", Student.class);
        students = studentQuery.getResultList();
        tx.commit();
        return students;
    }

    @Override
    public void createStudent(Student student) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(student);
        tx.commit();
        session.close();
    }

    @Override
    public Student getStudentByEmail(String email) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        return (Student) session.get(Student.class, email);
    }

    @Override
    public boolean validateStudent(String email, String password) {
        return Optional.ofNullable(getStudentByEmail(email))
                .map(s -> s.getPassword().equals(password))
                .orElse(false);
    }

    @Override
    public void registerStudentToCourse(String email, int courseId) {
        // find the student first and then find the course by id, then use lombok getter and add method to add that course
        // Maybe check first if the course is already assigned
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Student studentRegistering = getStudentByEmail(email);
        Course courseRegistering = session.get(Course.class, courseId);
        if (!studentRegistering.getCourses().contains(courseRegistering)) {
            studentRegistering.getCourses().add(courseRegistering);
        } else {
            System.out.println("You already registered to the following course silly: " + courseRegistering);
        }
        tx.commit();
        session.close();
    }

    @Override
    public List<Course> getStudentCourses(String email) {
        return List.of();
    }
}
