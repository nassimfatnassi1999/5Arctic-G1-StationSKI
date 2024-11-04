package tn.esprit.spring.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;

import java.util.Optional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class InstructorServicesImplTest {

    @InjectMocks
    private InstructorServicesImpl instructorServices;

    @Mock
    private IInstructorRepository instructorRepository;

    @Mock
    private ICourseRepository courseRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddInstructor() {
        // Arrange
        Instructor instructor = new Instructor();
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        // Act
        Instructor result = instructorServices.addInstructor(instructor);

        // Assert
        assertNotNull(result);
        verify(instructorRepository, times(1)).save(instructor);
    }
/*
    @Test
    public void testUpdateInstructor() {
        // Arrange
        Instructor instructor = new Instructor();
        instructor.setId(1L); // Exemple d'ID
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        // Act
        Instructor result = instructorServices.updateInstructor(instructor);

        // Assert
        assertNotNull(result);
        verify(instructorRepository, times(1)).save(instructor);
    }*/

    @Test
    public void testRetrieveInstructor() {
        // Arrange
        Long instructorId = 1L;
        Instructor instructor = new Instructor();
        when(instructorRepository.findById(instructorId)).thenReturn(Optional.of(instructor));

        // Act
        Instructor result = instructorServices.retrieveInstructor(instructorId);

        // Assert
        assertNotNull(result);
        assertEquals(instructor, result);
        verify(instructorRepository, times(1)).findById(instructorId);
    }

    @Test
    public void testAddInstructorAndAssignToCourse() {
        // Arrange
        Instructor instructor = new Instructor();
        Course course = new Course();
        Long courseId = 1L;

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        // Act
        Instructor result = instructorServices.addInstructorAndAssignToCourse(instructor, courseId);

        // Assert
        assertNotNull(result);
        verify(courseRepository, times(1)).findById(courseId);
        verify(instructorRepository, times(1)).save(instructor);

        Set<Course> expectedCourses = new HashSet<>();
        expectedCourses.add(course);
        assertEquals(expectedCourses, instructor.getCourses());
    }
}
