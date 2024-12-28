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
    private static final CourseService courseService = new CourseService();

    @Override
    public List<Student> getAllStudents() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query<Student> studentQuery = session.createQuery("from Student", Student.class);
        List<Student> students = studentQuery.getResultList(); // directly get the results here
        tx.commit();
        session.close();
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
        Student student = (Student) session.get(Student.class, email);
        tx.commit();
        session.close();
        return student;
    }

    @Override
    public boolean validateStudent(String email, String password) {
        return Optional.ofNullable(getStudentByEmail(email))
                .map(studentInstance -> studentInstance.getPassword().equals(password))
                .orElse(false);
    }

    @Override
    public void registerStudentToCourse(String email, int courseId) {

        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Student student = getStudentByEmail(email);

            Course course = courseService.getCourseById(courseId);

            if (!student.getCourses().contains(course)) {
                student.addCourse(course);

                session.merge(student);
            } else {
                System.out.println("You are already registered for this course: " + course);
            }

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }


    @Override
    public List<Course> getStudentCourses(String email) {
        // They want a List but courses is a set, so we need to either cast or fill up a new List with what's in the set
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Student currentStudent = getStudentByEmail(email);
        List<Course> courses = new ArrayList<>(currentStudent.getCourses());
        tx.commit();
        session.close();
        return courses;
    }
}
