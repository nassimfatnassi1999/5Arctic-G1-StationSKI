package tn.esprit.spring.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.hibernate.collection.internal.PersistentSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class InstructorServicesImplDiffblueTest {
    @MockBean
    private IRegistrationServices iRegistrationServices;

    @MockBean
    private ICourseRepository iCourseRepository;

    @MockBean
    private IInstructorRepository iInstructorRepository;

    @Autowired
    private InstructorServicesImpl instructorServicesImpl;

    /**
     * Method under test: {@link InstructorServicesImpl#addInstructor(Instructor)}
     */
    @Test
    public void testAddInstructor() {
        // Arrange
        Instructor instructor = new Instructor();
        instructor.setCourses(new HashSet<>());
        instructor.setDateOfHire(LocalDate.of(1970, 1, 1));
        instructor.setFirstName("Jane");
        instructor.setLastName("Doe");
        instructor.setNumInstructor(1L);
        when(iInstructorRepository.save(Mockito.<Instructor>any())).thenReturn(instructor);

        Instructor instructor2 = new Instructor();
        instructor2.setCourses(new HashSet<>());
        instructor2.setDateOfHire(LocalDate.of(1970, 1, 1));
        instructor2.setFirstName("Jane");
        instructor2.setLastName("Doe");
        instructor2.setNumInstructor(1L);

        // Act
        Instructor actualAddInstructorResult = instructorServicesImpl.addInstructor(instructor2);

        // Assert
        verify(iInstructorRepository).save(isA(Instructor.class));
        assertSame(instructor, actualAddInstructorResult);
    }

    /**
     * Method under test: {@link InstructorServicesImpl#retrieveAllInstructors()}
     */
    @Test
    public void testRetrieveAllInstructors() {
        // Arrange
        ArrayList<Instructor> instructorList = new ArrayList<>();
        when(iInstructorRepository.findAll()).thenReturn(instructorList);

        // Act
        List<Instructor> actualRetrieveAllInstructorsResult = instructorServicesImpl.retrieveAllInstructors();

        // Assert
        verify(iInstructorRepository).findAll();
        assertTrue(actualRetrieveAllInstructorsResult.isEmpty());
        assertSame(instructorList, actualRetrieveAllInstructorsResult);
    }

    /**
     * Method under test:
     * {@link InstructorServicesImpl#updateInstructor(Instructor)}
     */
    @Test
    public void testUpdateInstructor() {
        // Arrange
        Instructor instructor = new Instructor();
        instructor.setCourses(new HashSet<>());
        LocalDate dateOfHire = LocalDate.of(1970, 1, 1);
        instructor.setDateOfHire(dateOfHire);
        instructor.setFirstName("Jane");
        instructor.setLastName("Doe");
        instructor.setNumInstructor(1L);

        // Act
        Instructor actualUpdateInstructorResult = instructorServicesImpl.updateInstructor(instructor);

        // Assert
        Set<Course> courses = actualUpdateInstructorResult.getCourses();
        assertTrue(courses instanceof PersistentSet);
        LocalDate dateOfHire2 = actualUpdateInstructorResult.getDateOfHire();
        assertEquals("1970-01-01", dateOfHire2.toString());
        assertEquals("Doe", actualUpdateInstructorResult.getLastName());
        assertEquals("Jane", actualUpdateInstructorResult.getFirstName());
        assertEquals(1L, actualUpdateInstructorResult.getNumInstructor().longValue());
        assertTrue(courses.isEmpty());
        assertSame(dateOfHire, dateOfHire2);
    }

    /**
     * Method under test: {@link InstructorServicesImpl#retrieveInstructor(Long)}
     */
    @Test
    public void testRetrieveInstructor() {
        // Arrange
        Instructor instructor = new Instructor();
        instructor.setCourses(new HashSet<>());
        instructor.setDateOfHire(LocalDate.of(1970, 1, 1));
        instructor.setFirstName("Jane");
        instructor.setLastName("Doe");
        instructor.setNumInstructor(1L);
        Optional<Instructor> ofResult = Optional.of(instructor);
        when(iInstructorRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        Instructor actualRetrieveInstructorResult = instructorServicesImpl.retrieveInstructor(1L);

        // Assert
        verify(iInstructorRepository).findById(eq(1L));
        assertSame(instructor, actualRetrieveInstructorResult);
    }

    /**
     * Method under test:
     * {@link InstructorServicesImpl#addInstructorAndAssignToCourse(Instructor, Long)}
     */
    @Test
    public void testAddInstructorAndAssignToCourse() {
        // Arrange
        Instructor instructor = new Instructor();
        instructor.setCourses(new HashSet<>());
        instructor.setDateOfHire(LocalDate.of(1970, 1, 1));
        instructor.setFirstName("Jane");
        instructor.setLastName("Doe");
        instructor.setNumInstructor(1L);

        // Act
        instructorServicesImpl.addInstructorAndAssignToCourse(instructor, 1L);

        // Assert
        assertEquals(1, instructor.getCourses().size());
    }
}
