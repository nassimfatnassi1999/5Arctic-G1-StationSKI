import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.services.CourseServicesImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
public class CourseServiceImplTestMock {
    @InjectMocks
    private CourseServicesImpl courseServices;

    @Mock
    private ICourseRepository courseRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRetrieveAllCourses() {
        Course course1 = new Course(); // Initialize with necessary properties
        Course course2 = new Course(); // Initialize with necessary properties
        when(courseRepository.findAll()).thenReturn(Arrays.asList(course1, course2));

        List<Course> courses = courseServices.retrieveAllCourses();

        assertEquals(2, courses.size());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    public void testAddCourse() {
        Course course = new Course(); // Initialize with necessary properties
        when(courseRepository.save(course)).thenReturn(course);

        Course addedCourse = courseServices.addCourse(course);

        assertNotNull(addedCourse);
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    public void testUpdateCourse() {
        Course course = new Course(); // Initialize with necessary properties
        when(courseRepository.save(course)).thenReturn(course);

        Course updatedCourse = courseServices.updateCourse(course);

        assertNotNull(updatedCourse);
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    public void testRetrieveCourse() {
        Long courseId = 1L;
        Course course = new Course(); // Initialize with necessary properties
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        Course retrievedCourse = courseServices.retrieveCourse(courseId);

        assertNotNull(retrievedCourse);
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    public void testCalculateDiscount() {
        Course course = new Course();
        course.setPrice(100f);
        course.setLevel(3);

        Float discountedPrice = courseServices.calculateDiscount(course);

        assertEquals(90f, discountedPrice);
    }

    @Test
    public void testCalculateDiscountForLowerLevel() {
        Course course = new Course();
        course.setPrice(100f);
        course.setLevel(2);

        Float discountedPrice = courseServices.calculateDiscount(course);

        assertEquals(100f, discountedPrice);
    }
}