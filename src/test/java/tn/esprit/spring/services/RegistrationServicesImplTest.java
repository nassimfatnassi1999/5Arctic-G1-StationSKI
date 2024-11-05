package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Registration;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IRegistrationRepository;
import tn.esprit.spring.repositories.ISkierRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static tn.esprit.spring.entities.TypeCourse.COLLECTIVE_CHILDREN;

@ExtendWith(SpringExtension.class)
class RegistrationServicesImplTest {
    @Mock
    private IRegistrationRepository registrationRepository; // Mock the repository

    @Mock
    private ISkierRepository skierRepository; // Mock the skier repository

    @Mock
    private ICourseRepository courseRepository; // Mock the course repository

    @InjectMocks
    private RegistrationServicesImpl registrationServiceImpl; // Inject the service

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testAddRegistrationAndAssignToSkier() {
        Registration registration = new Registration();
        Long numSkier = 1L;
        Skier skier = new Skier();
        skier.setNumSkier(numSkier);

        when(skierRepository.findById(numSkier)).thenReturn(Optional.of(skier));
        when(registrationRepository.save(registration)).thenReturn(registration);

        Registration result = registrationServiceImpl.addRegistrationAndAssignToSkier(registration, numSkier);

        assertNotNull(result);
        assertEquals(skier, result.getSkier());
        verify(registrationRepository, times(1)).save(registration);
    }

    @Test
    void testAssignRegistrationToCourse() {
        Long numRegistration = 1L;
        Long numCourse = 2L;
        Registration registration = new Registration();
        Course course = new Course();
        course.setNumCourse(numCourse);

        when(registrationRepository.findById(numRegistration)).thenReturn(Optional.of(registration));
        when(courseRepository.findById(numCourse)).thenReturn(Optional.of(course));
        when(registrationRepository.save(registration)).thenReturn(registration);

        Registration result = registrationServiceImpl.assignRegistrationToCourse(numRegistration, numCourse);

        assertNotNull(result);
        assertEquals(course, result.getCourse());
        verify(registrationRepository, times(1)).save(registration);
    }


   @Test
void testAddRegistrationAndAssignToSkierAndCourse() {
    Registration registration = new Registration();
    Long numSkieur = 1L;
    Long numCours = 2L;
    Skier skier = new Skier();
    skier.setNumSkier(numSkieur);
    skier.setDateOfBirth(LocalDate.of(2005, 1, 1)); // Age < 16 for testing
    Course course = new Course();
    course.setNumCourse(numCours);
    course.setTypeCourse(COLLECTIVE_CHILDREN);

    // Mocking repository methods with return values
    when(skierRepository.findById(numSkieur)).thenReturn(Optional.of(skier));
    when(courseRepository.findById(numCours)).thenReturn(Optional.of(course));
    when(registrationRepository.countByCourseAndNumWeek(course, registration.getNumWeek())).thenReturn(5); // Assuming 5 registrations
    when(registrationRepository.save(registration)).thenReturn(registration);
    when(registrationRepository.countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(anyInt(), anyLong(), anyLong())).thenReturn(0);

    // Calling the service method
    Registration result = registrationServiceImpl.addRegistrationAndAssignToSkierAndCourse(registration, numSkieur, numCours);

    // Verifying the results
    assertNotNull(result);
    assertEquals(skier, result.getSkier());
    assertEquals(course, result.getCourse());
    verify(registrationRepository, times(1)).save(registration);
}


}
