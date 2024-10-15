package tn.esprit.spring.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;

import org.hibernate.collection.internal.PersistentSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.entities.TypeCourse;
import tn.esprit.spring.repositories.ICourseRepository;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CourseServicesImplDiffblueTest {
    @MockBean
    private IRegistrationServices iRegistrationServices;

    @Autowired
    private CourseServicesImpl courseServicesImpl;

    @MockBean
    private ICourseRepository iCourseRepository;

    /**
     * Method under test: {@link CourseServicesImpl#retrieveAllCourses()}
     */
    @Test
    public void testRetrieveAllCourses() {
        // Arrange and Act
        List<Course> actualRetrieveAllCoursesResult = courseServicesImpl.retrieveAllCourses();

        // Assert
        assertEquals(9, actualRetrieveAllCoursesResult.size());
        Course getResult = actualRetrieveAllCoursesResult.get(0);
        assertTrue(getResult.getRegistrations() instanceof PersistentSet);
        Course getResult2 = actualRetrieveAllCoursesResult.get(1);
        assertTrue(getResult2.getRegistrations() instanceof PersistentSet);
        Course getResult3 = actualRetrieveAllCoursesResult.get(2);
        assertTrue(getResult3.getRegistrations() instanceof PersistentSet);
        Course getResult4 = actualRetrieveAllCoursesResult.get(3);
        assertTrue(getResult4.getRegistrations() instanceof PersistentSet);
        Course getResult5 = actualRetrieveAllCoursesResult.get(4);
        assertTrue(getResult5.getRegistrations() instanceof PersistentSet);
        Course getResult6 = actualRetrieveAllCoursesResult.get(5);
        assertTrue(getResult6.getRegistrations() instanceof PersistentSet);
        Course getResult7 = actualRetrieveAllCoursesResult.get(6);
        assertTrue(getResult7.getRegistrations() instanceof PersistentSet);
        Course getResult8 = actualRetrieveAllCoursesResult.get(7);
        assertTrue(getResult8.getRegistrations() instanceof PersistentSet);
        Course getResult9 = actualRetrieveAllCoursesResult.get(8);
        assertTrue(getResult9.getRegistrations() instanceof PersistentSet);
        assertEquals(-0.5f, getResult.getPrice().floatValue(), 0.0f);
        assertEquals(1, getResult.getLevel());
        assertEquals(1, getResult2.getLevel());
        assertEquals(1, getResult3.getLevel());
        assertEquals(1, getResult4.getLevel());
        assertEquals(1, getResult5.getLevel());
        assertEquals(1, getResult6.getLevel());
        assertEquals(1, getResult7.getLevel());
        assertEquals(1, getResult8.getLevel());
        assertEquals(1, getResult9.getLevel());
        assertEquals(1, getResult.getTimeSlot());
        assertEquals(1, getResult2.getTimeSlot());
        assertEquals(1, getResult3.getTimeSlot());
        assertEquals(1, getResult4.getTimeSlot());
        assertEquals(1, getResult5.getTimeSlot());
        assertEquals(1, getResult6.getTimeSlot());
        assertEquals(1, getResult7.getTimeSlot());
        assertEquals(1, getResult8.getTimeSlot());
        assertEquals(1, getResult9.getTimeSlot());
        assertEquals(10.0f, getResult2.getPrice().floatValue(), 0.0f);
        assertEquals(10.0f, getResult3.getPrice().floatValue(), 0.0f);
        assertEquals(10.0f, getResult4.getPrice().floatValue(), 0.0f);
        assertEquals(10.0f, getResult5.getPrice().floatValue(), 0.0f);
        assertEquals(10.0f, getResult6.getPrice().floatValue(), 0.0f);
        assertEquals(10.0f, getResult7.getPrice().floatValue(), 0.0f);
        assertEquals(10.0f, getResult8.getPrice().floatValue(), 0.0f);
        assertEquals(10.0f, getResult9.getPrice().floatValue(), 0.0f);
        assertEquals(1L, getResult.getNumCourse().longValue());
        assertEquals(2L, getResult2.getNumCourse().longValue());
        assertEquals(3L, getResult3.getNumCourse().longValue());
        assertEquals(4L, getResult4.getNumCourse().longValue());
        assertEquals(5L, getResult5.getNumCourse().longValue());
        assertEquals(6L, getResult6.getNumCourse().longValue());
        assertEquals(7L, getResult7.getNumCourse().longValue());
        assertEquals(8L, getResult8.getNumCourse().longValue());
        assertEquals(9L, getResult9.getNumCourse().longValue());
        assertEquals(Support.SKI, getResult.getSupport());
        assertEquals(Support.SKI, getResult2.getSupport());
        assertEquals(Support.SKI, getResult3.getSupport());
        assertEquals(Support.SKI, getResult4.getSupport());
        assertEquals(Support.SKI, getResult5.getSupport());
        assertEquals(Support.SKI, getResult6.getSupport());
        assertEquals(Support.SKI, getResult7.getSupport());
        assertEquals(Support.SKI, getResult8.getSupport());
        assertEquals(Support.SKI, getResult9.getSupport());
        assertEquals(TypeCourse.COLLECTIVE_CHILDREN, getResult.getTypeCourse());
        assertEquals(TypeCourse.COLLECTIVE_CHILDREN, getResult2.getTypeCourse());
        assertEquals(TypeCourse.COLLECTIVE_CHILDREN, getResult3.getTypeCourse());
        assertEquals(TypeCourse.COLLECTIVE_CHILDREN, getResult4.getTypeCourse());
        assertEquals(TypeCourse.COLLECTIVE_CHILDREN, getResult5.getTypeCourse());
        assertEquals(TypeCourse.COLLECTIVE_CHILDREN, getResult6.getTypeCourse());
        assertEquals(TypeCourse.COLLECTIVE_CHILDREN, getResult7.getTypeCourse());
        assertEquals(TypeCourse.COLLECTIVE_CHILDREN, getResult8.getTypeCourse());
        assertEquals(TypeCourse.COLLECTIVE_CHILDREN, getResult9.getTypeCourse());
    }

    /**
     * Method under test: {@link CourseServicesImpl#addCourse(Course)}
     */
    @Test
    public void testAddCourse() {
        // Arrange
        Course course = new Course();
        course.setLevel(1);
        course.setNumCourse(1L);
        course.setPrice(10.0f);
        course.setRegistrations(new HashSet<>());
        course.setSupport(Support.SKI);
        course.setTimeSlot(1);
        course.setTypeCourse(TypeCourse.COLLECTIVE_CHILDREN);
        when(iCourseRepository.save(Mockito.<Course>any())).thenReturn(course);

        Course course2 = new Course();
        course2.setLevel(1);
        course2.setNumCourse(1L);
        course2.setPrice(10.0f);
        course2.setRegistrations(new HashSet<>());
        course2.setSupport(Support.SKI);
        course2.setTimeSlot(1);
        course2.setTypeCourse(TypeCourse.COLLECTIVE_CHILDREN);

        // Act
        Course actualAddCourseResult = courseServicesImpl.addCourse(course2);

        // Assert
        verify(iCourseRepository).save(isA(Course.class));
        assertSame(course, actualAddCourseResult);
    }

    /**
     * Method under test: {@link CourseServicesImpl#updateCourse(Course)}
     */
    @Test
    public void testUpdateCourse() {
        // Arrange
        Course course = new Course();
        course.setLevel(1);
        course.setNumCourse(1L);
        course.setPrice(10.0f);
        course.setRegistrations(new HashSet<>());
        course.setSupport(Support.SKI);
        course.setTimeSlot(1);
        course.setTypeCourse(TypeCourse.COLLECTIVE_CHILDREN);

        // Act
        Course actualUpdateCourseResult = courseServicesImpl.updateCourse(course);

        // Assert
        assertTrue(actualUpdateCourseResult.getRegistrations() instanceof PersistentSet);
        assertEquals(1, actualUpdateCourseResult.getLevel());
        assertEquals(1, actualUpdateCourseResult.getTimeSlot());
        assertEquals(10.0f, actualUpdateCourseResult.getPrice().floatValue(), 0.0f);
        assertEquals(1L, actualUpdateCourseResult.getNumCourse().longValue());
        assertEquals(Support.SKI, actualUpdateCourseResult.getSupport());
        assertEquals(TypeCourse.COLLECTIVE_CHILDREN, actualUpdateCourseResult.getTypeCourse());
    }

    /**
     * Method under test: {@link CourseServicesImpl#retrieveCourse(Long)}
     */
    @Test
    public void testRetrieveCourse() {
        // Arrange and Act
        Course actualRetrieveCourseResult = courseServicesImpl.retrieveCourse(1L);

        // Assert
        assertTrue(actualRetrieveCourseResult.getRegistrations() instanceof PersistentSet);
        assertEquals(-0.5f, actualRetrieveCourseResult.getPrice().floatValue(), 0.0f);
        assertEquals(1, actualRetrieveCourseResult.getLevel());
        assertEquals(1, actualRetrieveCourseResult.getTimeSlot());
        assertEquals(1L, actualRetrieveCourseResult.getNumCourse().longValue());
        assertEquals(Support.SKI, actualRetrieveCourseResult.getSupport());
        assertEquals(TypeCourse.COLLECTIVE_CHILDREN, actualRetrieveCourseResult.getTypeCourse());
    }
}
