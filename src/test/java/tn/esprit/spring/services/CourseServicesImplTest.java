package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.repositories.ICourseRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CourseServicesImplTest {

    @InjectMocks
    private CourseServicesImpl courseServices;

    @MockBean
    private ICourseRepository courseRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllCourses() {
        Course course1 = new Course();
        course1.setNumCourse(1L);
        course1.setLevel(3);
        course1.setPrice(100f);

        Course course2 = new Course();
        course2.setNumCourse(2L);
        course2.setLevel(1);
        course2.setPrice(200f);

        when(courseRepository.findAll()).thenReturn(Arrays.asList(course1, course2));

        List<Course> courses = courseServices.retrieveAllCourses();

        assertEquals(2, courses.size());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void testAddCourse() {
        Course course = new Course();
        course.setNumCourse(1L);
        course.setLevel(3);
        course.setPrice(100f);

        when(courseRepository.save(course)).thenReturn(course);

        Course addedCourse = courseServices.addCourse(course);

        assertNotNull(addedCourse);
        assertEquals(course.getNumCourse(), addedCourse.getNumCourse());
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void testUpdateCourse() {
        Course course = new Course();
        course.setNumCourse(1L);
        course.setLevel(3);
        course.setPrice(100f);

        when(courseRepository.save(course)).thenReturn(course);

        Course updatedCourse = courseServices.updateCourse(course);

        assertNotNull(updatedCourse);
        assertEquals(course.getNumCourse(), updatedCourse.getNumCourse());
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void testRetrieveCourse() {
        Long courseId = 1L;
        Course course = new Course();
        course.setNumCourse(courseId);
        course.setLevel(3);
        course.setPrice(100f);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        Course retrievedCourse = courseServices.retrieveCourse(courseId);

        assertNotNull(retrievedCourse);
        assertEquals(courseId, retrievedCourse.getNumCourse());
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    void testCalculateDiscount() {
        Course course = new Course();
        course.setPrice(100f);
        course.setLevel(3);

        Float discountedPrice = courseServices.calculateDiscount(course);

        assertEquals(90f, discountedPrice);
    }

    @Test
    void testCalculateDiscountForLowerLevel() {
        Course course = new Course();
        course.setPrice(100f);
        course.setLevel(2);

        Float discountedPrice = courseServices.calculateDiscount(course);

        assertEquals(100f, discountedPrice);
    }
}