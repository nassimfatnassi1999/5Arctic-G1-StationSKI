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
public Registration addRegistrationAndAssignToSkierAndCourse(Registration registration, Long numSkieur, Long numCours) {
    // Retrieve the skier and course based on provided IDs
    Optional<Skier> optionalSkier = skierRepository.findById(numSkieur);
    Optional<Course> optionalCourse = courseRepository.findById(numCours);

    if (optionalSkier.isEmpty() || optionalCourse.isEmpty()) {
        throw new EntityNotFoundException("Skier or Course not found");
    }

    Skier skier = optionalSkier.get();
    Course course = optionalCourse.get();

    // Check if skier meets any required conditions, e.g., age
    if (skier.getDateOfBirth().isAfter(LocalDate.now().minusYears(16))) {
        throw new IllegalArgumentException("Skier must be at least 16 years old for this course type");
    }

    // Check if the course has available slots
    long currentRegistrations = registrationRepository.countByCourseAndNumWeek(course, registration.getNumWeek());
    if (currentRegistrations >= course.getMaxParticipants()) {
        throw new IllegalArgumentException("Course is full for the specified week");
    }

    // Assign Skier and Course to Registration
    registration.setSkier(skier);
    registration.setCourse(course);

    // Ensure no duplicate registration for the same week and skier-course combination
    long duplicateCount = registrationRepository.countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(
        registration.getNumWeek(), skier.getNumSkier(), course.getNumCourse());

    if (duplicateCount > 0) {
        throw new IllegalArgumentException("Skier is already registered for this course in the specified week");
    }

    // Save the registration
    return registrationRepository.save(registration);
}



}
