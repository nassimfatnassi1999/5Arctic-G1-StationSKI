package tn.esprit.spring.services;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Registration;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.entities.TypeCourse;
import tn.esprit.spring.entities.TypeSubscription;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IRegistrationRepository;
import tn.esprit.spring.repositories.ISkierRepository;

@ContextConfiguration(classes = {RegistrationServicesImpl.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class RegistrationServicesImplDiffblueTest {
    @MockBean
    private ICourseRepository iCourseRepository;

    @MockBean
    private IRegistrationRepository iRegistrationRepository;

    @MockBean
    private ISkierRepository iSkierRepository;

    @Autowired
    private RegistrationServicesImpl registrationServicesImpl;

    /**
     * Method under test:
     * {@link RegistrationServicesImpl#addRegistrationAndAssignToSkier(Registration, Long)}
     */
    @Test
    public void testAddRegistrationAndAssignToSkier() {
        // Arrange
        Course course = new Course();
        course.setLevel(1);
        course.setNumCourse(1L);
        course.setPrice(10.0f);
        course.setRegistrations(new HashSet<>());
        course.setSupport(Support.SKI);
        course.setTimeSlot(1);
        course.setTypeCourse(TypeCourse.COLLECTIVE_CHILDREN);

        Subscription subscription = new Subscription();
        subscription.setEndDate(LocalDate.of(1970, 1, 1));
        subscription.setNumSub(1L);
        subscription.setPrice(10.0f);
        subscription.setStartDate(LocalDate.of(1970, 1, 1));
        subscription.setTypeSub(TypeSubscription.ANNUAL);

        Skier skier = new Skier();
        skier.setCity("Oxford");
        skier.setDateOfBirth(LocalDate.of(1970, 1, 1));
        skier.setFirstName("Jane");
        skier.setLastName("Doe");
        skier.setNumSkier(1L);
        skier.setPistes(new HashSet<>());
        skier.setRegistrations(new HashSet<>());
        skier.setSubscription(subscription);

        Registration registration = new Registration();
        registration.setCourse(course);
        registration.setNumRegistration(1L);
        registration.setNumWeek(10);
        registration.setSkier(skier);
        when(iRegistrationRepository.save(Mockito.<Registration>any())).thenReturn(registration);

        Subscription subscription2 = new Subscription();
        subscription2.setEndDate(LocalDate.of(1970, 1, 1));
        subscription2.setNumSub(1L);
        subscription2.setPrice(10.0f);
        subscription2.setStartDate(LocalDate.of(1970, 1, 1));
        subscription2.setTypeSub(TypeSubscription.ANNUAL);

        Skier skier2 = new Skier();
        skier2.setCity("Oxford");
        skier2.setDateOfBirth(LocalDate.of(1970, 1, 1));
        skier2.setFirstName("Jane");
        skier2.setLastName("Doe");
        skier2.setNumSkier(1L);
        skier2.setPistes(new HashSet<>());
        skier2.setRegistrations(new HashSet<>());
        skier2.setSubscription(subscription2);
        Optional<Skier> ofResult = Optional.of(skier2);
        when(iSkierRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Course course2 = new Course();
        course2.setLevel(1);
        course2.setNumCourse(1L);
        course2.setPrice(10.0f);
        course2.setRegistrations(new HashSet<>());
        course2.setSupport(Support.SKI);
        course2.setTimeSlot(1);
        course2.setTypeCourse(TypeCourse.COLLECTIVE_CHILDREN);

        Subscription subscription3 = new Subscription();
        subscription3.setEndDate(LocalDate.of(1970, 1, 1));
        subscription3.setNumSub(1L);
        subscription3.setPrice(10.0f);
        subscription3.setStartDate(LocalDate.of(1970, 1, 1));
        subscription3.setTypeSub(TypeSubscription.ANNUAL);

        Skier skier3 = new Skier();
        skier3.setCity("Oxford");
        skier3.setDateOfBirth(LocalDate.of(1970, 1, 1));
        skier3.setFirstName("Jane");
        skier3.setLastName("Doe");
        skier3.setNumSkier(1L);
        skier3.setPistes(new HashSet<>());
        skier3.setRegistrations(new HashSet<>());
        skier3.setSubscription(subscription3);

        Registration registration2 = new Registration();
        registration2.setCourse(course2);
        registration2.setNumRegistration(1L);
        registration2.setNumWeek(10);
        registration2.setSkier(skier3);

        // Act
        Registration actualAddRegistrationAndAssignToSkierResult = registrationServicesImpl
                .addRegistrationAndAssignToSkier(registration2, 1L);

        // Assert
        verify(iSkierRepository).findById(eq(1L));
        verify(iRegistrationRepository).save(isA(Registration.class));
        assertSame(registration, actualAddRegistrationAndAssignToSkierResult);
    }

    /**
     * Method under test:
     * {@link RegistrationServicesImpl#assignRegistrationToCourse(Long, Long)}
     */
    @Test
    public void testAssignRegistrationToCourse() {
        // Arrange
        Course course = new Course();
        course.setLevel(1);
        course.setNumCourse(1L);
        course.setPrice(10.0f);
        course.setRegistrations(new HashSet<>());
        course.setSupport(Support.SKI);
        course.setTimeSlot(1);
        course.setTypeCourse(TypeCourse.COLLECTIVE_CHILDREN);

        Subscription subscription = new Subscription();
        subscription.setEndDate(LocalDate.of(1970, 1, 1));
        subscription.setNumSub(1L);
        subscription.setPrice(10.0f);
        subscription.setStartDate(LocalDate.of(1970, 1, 1));
        subscription.setTypeSub(TypeSubscription.ANNUAL);

        Skier skier = new Skier();
        skier.setCity("Oxford");
        skier.setDateOfBirth(LocalDate.of(1970, 1, 1));
        skier.setFirstName("Jane");
        skier.setLastName("Doe");
        skier.setNumSkier(1L);
        skier.setPistes(new HashSet<>());
        skier.setRegistrations(new HashSet<>());
        skier.setSubscription(subscription);

        Registration registration = new Registration();
        registration.setCourse(course);
        registration.setNumRegistration(1L);
        registration.setNumWeek(10);
        registration.setSkier(skier);
        Optional<Registration> ofResult = Optional.of(registration);

        Course course2 = new Course();
        course2.setLevel(1);
        course2.setNumCourse(1L);
        course2.setPrice(10.0f);
        course2.setRegistrations(new HashSet<>());
        course2.setSupport(Support.SKI);
        course2.setTimeSlot(1);
        course2.setTypeCourse(TypeCourse.COLLECTIVE_CHILDREN);

        Subscription subscription2 = new Subscription();
        subscription2.setEndDate(LocalDate.of(1970, 1, 1));
        subscription2.setNumSub(1L);
        subscription2.setPrice(10.0f);
        subscription2.setStartDate(LocalDate.of(1970, 1, 1));
        subscription2.setTypeSub(TypeSubscription.ANNUAL);

        Skier skier2 = new Skier();
        skier2.setCity("Oxford");
        skier2.setDateOfBirth(LocalDate.of(1970, 1, 1));
        skier2.setFirstName("Jane");
        skier2.setLastName("Doe");
        skier2.setNumSkier(1L);
        skier2.setPistes(new HashSet<>());
        skier2.setRegistrations(new HashSet<>());
        skier2.setSubscription(subscription2);

        Registration registration2 = new Registration();
        registration2.setCourse(course2);
        registration2.setNumRegistration(1L);
        registration2.setNumWeek(10);
        registration2.setSkier(skier2);
        when(iRegistrationRepository.save(Mockito.<Registration>any())).thenReturn(registration2);
        when(iRegistrationRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Course course3 = new Course();
        course3.setLevel(1);
        course3.setNumCourse(1L);
        course3.setPrice(10.0f);
        course3.setRegistrations(new HashSet<>());
        course3.setSupport(Support.SKI);
        course3.setTimeSlot(1);
        course3.setTypeCourse(TypeCourse.COLLECTIVE_CHILDREN);
        Optional<Course> ofResult2 = Optional.of(course3);
        when(iCourseRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        // Act
        Registration actualAssignRegistrationToCourseResult = registrationServicesImpl.assignRegistrationToCourse(1L, 1L);

        // Assert
        verify(iCourseRepository).findById(eq(1L));
        verify(iRegistrationRepository).findById(eq(1L));
        verify(iRegistrationRepository).save(isA(Registration.class));
        assertSame(registration2, actualAssignRegistrationToCourseResult);
    }

    /**
     * Method under test:
     * {@link RegistrationServicesImpl#addRegistrationAndAssignToSkierAndCourse(Registration, Long, Long)}
     */
    @Test
    public void testAddRegistrationAndAssignToSkierAndCourse() {
        // Arrange
        when(iRegistrationRepository.countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(anyInt(),
                Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(3L);

        Subscription subscription = new Subscription();
        subscription.setEndDate(LocalDate.of(1970, 1, 1));
        subscription.setNumSub(1L);
        subscription.setPrice(10.0f);
        subscription.setStartDate(LocalDate.of(1970, 1, 1));
        subscription.setTypeSub(TypeSubscription.ANNUAL);

        Skier skier = new Skier();
        skier.setCity("Oxford");
        skier.setDateOfBirth(LocalDate.of(1970, 1, 1));
        skier.setFirstName("Jane");
        skier.setLastName("Doe");
        skier.setNumSkier(1L);
        skier.setPistes(new HashSet<>());
        skier.setRegistrations(new HashSet<>());
        skier.setSubscription(subscription);
        Optional<Skier> ofResult = Optional.of(skier);
        when(iSkierRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Course course = new Course();
        course.setLevel(1);
        course.setNumCourse(1L);
        course.setPrice(10.0f);
        course.setRegistrations(new HashSet<>());
        course.setSupport(Support.SKI);
        course.setTimeSlot(1);
        course.setTypeCourse(TypeCourse.COLLECTIVE_CHILDREN);
        Optional<Course> ofResult2 = Optional.of(course);
        when(iCourseRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        Course course2 = new Course();
        course2.setLevel(1);
        course2.setNumCourse(1L);
        course2.setPrice(10.0f);
        course2.setRegistrations(new HashSet<>());
        course2.setSupport(Support.SKI);
        course2.setTimeSlot(1);
        course2.setTypeCourse(TypeCourse.COLLECTIVE_CHILDREN);

        Subscription subscription2 = new Subscription();
        subscription2.setEndDate(LocalDate.of(1970, 1, 1));
        subscription2.setNumSub(1L);
        subscription2.setPrice(10.0f);
        subscription2.setStartDate(LocalDate.of(1970, 1, 1));
        subscription2.setTypeSub(TypeSubscription.ANNUAL);

        Skier skier2 = new Skier();
        skier2.setCity("Oxford");
        skier2.setDateOfBirth(LocalDate.of(1970, 1, 1));
        skier2.setFirstName("Jane");
        skier2.setLastName("Doe");
        skier2.setNumSkier(1L);
        skier2.setPistes(new HashSet<>());
        skier2.setRegistrations(new HashSet<>());
        skier2.setSubscription(subscription2);

        Registration registration = new Registration();
        registration.setCourse(course2);
        registration.setNumRegistration(1L);
        registration.setNumWeek(10);
        registration.setSkier(skier2);

        // Act
        Registration actualAddRegistrationAndAssignToSkierAndCourseResult = registrationServicesImpl
                .addRegistrationAndAssignToSkierAndCourse(registration, 1L, 1L);

        // Assert
        verify(iCourseRepository).findById(eq(1L));
        verify(iSkierRepository).findById(eq(1L));
        verify(iRegistrationRepository).countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(eq(10), eq(1L), eq(1L));
        assertNull(actualAddRegistrationAndAssignToSkierAndCourseResult);
    }

    /**
     * Method under test:
     * {@link RegistrationServicesImpl#addRegistrationAndAssignToSkierAndCourse(Registration, Long, Long)}
     */
    @Test
    public void testAddRegistrationAndAssignToSkierAndCourse2() {
        // Arrange
        when(iRegistrationRepository.countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(anyInt(),
                Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(0L);

        Subscription subscription = new Subscription();
        subscription.setEndDate(LocalDate.of(1970, 1, 1));
        subscription.setNumSub(1L);
        subscription.setPrice(10.0f);
        subscription.setStartDate(LocalDate.of(1970, 1, 1));
        subscription.setTypeSub(TypeSubscription.ANNUAL);

        Skier skier = new Skier();
        skier.setCity("Oxford");
        skier.setDateOfBirth(LocalDate.of(1970, 1, 1));
        skier.setFirstName("Jane");
        skier.setLastName("Doe");
        skier.setNumSkier(1L);
        skier.setPistes(new HashSet<>());
        skier.setRegistrations(new HashSet<>());
        skier.setSubscription(subscription);
        Optional<Skier> ofResult = Optional.of(skier);
        when(iSkierRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Course course = new Course();
        course.setLevel(1);
        course.setNumCourse(1L);
        course.setPrice(10.0f);
        course.setRegistrations(new HashSet<>());
        course.setSupport(Support.SKI);
        course.setTimeSlot(1);
        course.setTypeCourse(TypeCourse.COLLECTIVE_CHILDREN);
        Optional<Course> ofResult2 = Optional.of(course);
        when(iCourseRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        Course course2 = new Course();
        course2.setLevel(1);
        course2.setNumCourse(1L);
        course2.setPrice(10.0f);
        course2.setRegistrations(new HashSet<>());
        course2.setSupport(Support.SKI);
        course2.setTimeSlot(1);
        course2.setTypeCourse(TypeCourse.COLLECTIVE_CHILDREN);

        Subscription subscription2 = new Subscription();
        subscription2.setEndDate(LocalDate.of(1970, 1, 1));
        subscription2.setNumSub(1L);
        subscription2.setPrice(10.0f);
        subscription2.setStartDate(LocalDate.of(1970, 1, 1));
        subscription2.setTypeSub(TypeSubscription.ANNUAL);

        Skier skier2 = new Skier();
        skier2.setCity("Oxford");
        skier2.setDateOfBirth(LocalDate.of(1970, 1, 1));
        skier2.setFirstName("Jane");
        skier2.setLastName("Doe");
        skier2.setNumSkier(1L);
        skier2.setPistes(new HashSet<>());
        skier2.setRegistrations(new HashSet<>());
        skier2.setSubscription(subscription2);

        Registration registration = new Registration();
        registration.setCourse(course2);
        registration.setNumRegistration(1L);
        registration.setNumWeek(10);
        registration.setSkier(skier2);

        // Act
        registrationServicesImpl.addRegistrationAndAssignToSkierAndCourse(registration, 1L, 1L);

        // Assert
        verify(iCourseRepository).findById(eq(1L));
        verify(iSkierRepository).findById(eq(1L));
        verify(iRegistrationRepository).countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(eq(10), eq(1L), eq(1L));
    }

    /**
     * Method under test:
     * {@link RegistrationServicesImpl#addRegistrationAndAssignToSkierAndCourse(Registration, Long, Long)}
     */
    @Test
    public void testAddRegistrationAndAssignToSkierAndCourse3() {
        // Arrange
        Optional<Skier> emptyResult = Optional.empty();
        when(iSkierRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        Course course = new Course();
        course.setLevel(1);
        course.setNumCourse(1L);
        course.setPrice(10.0f);
        course.setRegistrations(new HashSet<>());
        course.setSupport(Support.SKI);
        course.setTimeSlot(1);
        course.setTypeCourse(TypeCourse.COLLECTIVE_CHILDREN);
        Optional<Course> ofResult = Optional.of(course);
        when(iCourseRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Course course2 = new Course();
        course2.setLevel(1);
        course2.setNumCourse(1L);
        course2.setPrice(10.0f);
        course2.setRegistrations(new HashSet<>());
        course2.setSupport(Support.SKI);
        course2.setTimeSlot(1);
        course2.setTypeCourse(TypeCourse.COLLECTIVE_CHILDREN);

        Subscription subscription = new Subscription();
        subscription.setEndDate(LocalDate.of(1970, 1, 1));
        subscription.setNumSub(1L);
        subscription.setPrice(10.0f);
        subscription.setStartDate(LocalDate.of(1970, 1, 1));
        subscription.setTypeSub(TypeSubscription.ANNUAL);

        Skier skier = new Skier();
        skier.setCity("Oxford");
        skier.setDateOfBirth(LocalDate.of(1970, 1, 1));
        skier.setFirstName("Jane");
        skier.setLastName("Doe");
        skier.setNumSkier(1L);
        skier.setPistes(new HashSet<>());
        skier.setRegistrations(new HashSet<>());
        skier.setSubscription(subscription);

        Registration registration = new Registration();
        registration.setCourse(course2);
        registration.setNumRegistration(1L);
        registration.setNumWeek(10);
        registration.setSkier(skier);

        // Act
        Registration actualAddRegistrationAndAssignToSkierAndCourseResult = registrationServicesImpl
                .addRegistrationAndAssignToSkierAndCourse(registration, 1L, 1L);

        // Assert
        verify(iCourseRepository).findById(eq(1L));
        verify(iSkierRepository).findById(eq(1L));
        assertNull(actualAddRegistrationAndAssignToSkierAndCourseResult);
    }

    /**
     * Method under test:
     * {@link RegistrationServicesImpl#addRegistrationAndAssignToSkierAndCourse(Registration, Long, Long)}
     */
    @Test
    public void testAddRegistrationAndAssignToSkierAndCourse4() {
        // Arrange
        Subscription subscription = new Subscription();
        subscription.setEndDate(LocalDate.of(1970, 1, 1));
        subscription.setNumSub(1L);
        subscription.setPrice(10.0f);
        subscription.setStartDate(LocalDate.of(1970, 1, 1));
        subscription.setTypeSub(TypeSubscription.ANNUAL);

        Skier skier = new Skier();
        skier.setCity("Oxford");
        skier.setDateOfBirth(LocalDate.of(1970, 1, 1));
        skier.setFirstName("Jane");
        skier.setLastName("Doe");
        skier.setNumSkier(1L);
        skier.setPistes(new HashSet<>());
        skier.setRegistrations(new HashSet<>());
        skier.setSubscription(subscription);
        Optional<Skier> ofResult = Optional.of(skier);
        when(iSkierRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        Optional<Course> emptyResult = Optional.empty();
        when(iCourseRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        Course course = new Course();
        course.setLevel(1);
        course.setNumCourse(1L);
        course.setPrice(10.0f);
        course.setRegistrations(new HashSet<>());
        course.setSupport(Support.SKI);
        course.setTimeSlot(1);
        course.setTypeCourse(TypeCourse.COLLECTIVE_CHILDREN);

        Subscription subscription2 = new Subscription();
        subscription2.setEndDate(LocalDate.of(1970, 1, 1));
        subscription2.setNumSub(1L);
        subscription2.setPrice(10.0f);
        subscription2.setStartDate(LocalDate.of(1970, 1, 1));
        subscription2.setTypeSub(TypeSubscription.ANNUAL);

        Skier skier2 = new Skier();
        skier2.setCity("Oxford");
        skier2.setDateOfBirth(LocalDate.of(1970, 1, 1));
        skier2.setFirstName("Jane");
        skier2.setLastName("Doe");
        skier2.setNumSkier(1L);
        skier2.setPistes(new HashSet<>());
        skier2.setRegistrations(new HashSet<>());
        skier2.setSubscription(subscription2);

        Registration registration = new Registration();
        registration.setCourse(course);
        registration.setNumRegistration(1L);
        registration.setNumWeek(10);
        registration.setSkier(skier2);

        // Act
        Registration actualAddRegistrationAndAssignToSkierAndCourseResult = registrationServicesImpl
                .addRegistrationAndAssignToSkierAndCourse(registration, 1L, 1L);

        // Assert
        verify(iCourseRepository).findById(eq(1L));
        verify(iSkierRepository).findById(eq(1L));
        assertNull(actualAddRegistrationAndAssignToSkierAndCourseResult);
    }

    /**
     * Method under test:
     * {@link RegistrationServicesImpl#numWeeksCourseOfInstructorBySupport(Long, Support)}
     */
    @Test
    public void testNumWeeksCourseOfInstructorBySupport() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        when(iRegistrationRepository.numWeeksCourseOfInstructorBySupport(Mockito.<Long>any(), Mockito.<Support>any()))
                .thenReturn(integerList);

        // Act
        List<Integer> actualNumWeeksCourseOfInstructorBySupportResult = registrationServicesImpl
                .numWeeksCourseOfInstructorBySupport(1L, Support.SKI);

        // Assert
        verify(iRegistrationRepository).numWeeksCourseOfInstructorBySupport(eq(1L), eq(Support.SKI));
        assertTrue(actualNumWeeksCourseOfInstructorBySupportResult.isEmpty());
        assertSame(integerList, actualNumWeeksCourseOfInstructorBySupportResult);
    }

    /**
     * Method under test:
     * {@link RegistrationServicesImpl#numWeeksCourseOfInstructorBySupport(Long, Support)}
     */
    @Test
    public void testNumWeeksCourseOfInstructorBySupport2() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        when(iRegistrationRepository.numWeeksCourseOfInstructorBySupport(Mockito.<Long>any(), Mockito.<Support>any()))
                .thenReturn(integerList);

        // Act
        List<Integer> actualNumWeeksCourseOfInstructorBySupportResult = registrationServicesImpl
                .numWeeksCourseOfInstructorBySupport(1L, Support.SNOWBOARD);

        // Assert
        verify(iRegistrationRepository).numWeeksCourseOfInstructorBySupport(eq(1L), eq(Support.SNOWBOARD));
        assertTrue(actualNumWeeksCourseOfInstructorBySupportResult.isEmpty());
        assertSame(integerList, actualNumWeeksCourseOfInstructorBySupportResult);
    }
}
