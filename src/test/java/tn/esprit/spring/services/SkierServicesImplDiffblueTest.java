package tn.esprit.spring.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tn.esprit.spring.entities.Color;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.entities.Registration;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.entities.TypeCourse;
import tn.esprit.spring.entities.TypeSubscription;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IPisteRepository;
import tn.esprit.spring.repositories.IRegistrationRepository;
import tn.esprit.spring.repositories.ISkierRepository;
import tn.esprit.spring.repositories.ISubscriptionRepository;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class SkierServicesImplDiffblueTest {
    @MockBean
    private IRegistrationServices iRegistrationServices;

    @MockBean
    private ICourseRepository iCourseRepository;

    @MockBean
    private IPisteRepository iPisteRepository;

    @MockBean
    private IRegistrationRepository iRegistrationRepository;

    @MockBean
    private ISkierRepository iSkierRepository;

    @MockBean
    private ISubscriptionRepository iSubscriptionRepository;

    @Autowired
    private SkierServicesImpl skierServicesImpl;

    /**
     * Method under test: {@link SkierServicesImpl#retrieveAllSkiers()}
     */
    @Test
    public void testRetrieveAllSkiers() {
        // Arrange
        ArrayList<Skier> skierList = new ArrayList<>();
        when(iSkierRepository.findAll()).thenReturn(skierList);

        // Act
        List<Skier> actualRetrieveAllSkiersResult = skierServicesImpl.retrieveAllSkiers();

        // Assert
        verify(iSkierRepository).findAll();
        assertTrue(actualRetrieveAllSkiersResult.isEmpty());
        assertSame(skierList, actualRetrieveAllSkiersResult);
    }

    /**
     * Method under test: {@link SkierServicesImpl#addSkier(Skier)}
     */
    @Test
    public void testAddSkier() {
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
        when(iSkierRepository.save(Mockito.<Skier>any())).thenReturn(skier);

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

        // Act
        Skier actualAddSkierResult = skierServicesImpl.addSkier(skier2);

        // Assert
        verify(iSkierRepository).save(isA(Skier.class));
        assertEquals("1971-01-01", skier2.getSubscription().getEndDate().toString());
        assertSame(skier, actualAddSkierResult);
    }

    /**
     * Method under test: {@link SkierServicesImpl#addSkier(Skier)}
     */
    @Test
    public void testAddSkier2() {
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
        when(iSkierRepository.save(Mockito.<Skier>any())).thenReturn(skier);

        Subscription subscription2 = new Subscription();
        subscription2.setEndDate(LocalDate.of(1970, 1, 1));
        subscription2.setNumSub(1L);
        subscription2.setPrice(10.0f);
        subscription2.setStartDate(LocalDate.of(1970, 1, 1));
        subscription2.setTypeSub(TypeSubscription.ANNUAL);
        Subscription subscription3 = mock(Subscription.class);
        when(subscription3.getStartDate()).thenReturn(LocalDate.of(1970, 1, 1));
        when(subscription3.getTypeSub()).thenReturn(TypeSubscription.MONTHLY);
        doNothing().when(subscription3).setEndDate(Mockito.<LocalDate>any());
        doNothing().when(subscription3).setNumSub(Mockito.<Long>any());
        doNothing().when(subscription3).setPrice(Mockito.<Float>any());
        doNothing().when(subscription3).setStartDate(Mockito.<LocalDate>any());
        doNothing().when(subscription3).setTypeSub(Mockito.<TypeSubscription>any());
        subscription3.setEndDate(LocalDate.of(1970, 1, 1));
        subscription3.setNumSub(1L);
        subscription3.setPrice(10.0f);
        subscription3.setStartDate(LocalDate.of(1970, 1, 1));
        subscription3.setTypeSub(TypeSubscription.ANNUAL);
        Skier skier2 = mock(Skier.class);
        when(skier2.getSubscription()).thenReturn(subscription3);
        doNothing().when(skier2).setCity(Mockito.<String>any());
        doNothing().when(skier2).setDateOfBirth(Mockito.<LocalDate>any());
        doNothing().when(skier2).setFirstName(Mockito.<String>any());
        doNothing().when(skier2).setLastName(Mockito.<String>any());
        doNothing().when(skier2).setNumSkier(Mockito.<Long>any());
        doNothing().when(skier2).setPistes(Mockito.<Set<Piste>>any());
        doNothing().when(skier2).setRegistrations(Mockito.<Set<Registration>>any());
        doNothing().when(skier2).setSubscription(Mockito.<Subscription>any());
        skier2.setCity("Oxford");
        skier2.setDateOfBirth(LocalDate.of(1970, 1, 1));
        skier2.setFirstName("Jane");
        skier2.setLastName("Doe");
        skier2.setNumSkier(1L);
        skier2.setPistes(new HashSet<>());
        skier2.setRegistrations(new HashSet<>());
        skier2.setSubscription(subscription2);

        // Act
        Skier actualAddSkierResult = skierServicesImpl.addSkier(skier2);

        // Assert
        verify(iSkierRepository).save(isA(Skier.class));
        verify(skier2, atLeast(1)).getSubscription();
        verify(skier2).setCity(eq("Oxford"));
        verify(skier2).setDateOfBirth(isA(LocalDate.class));
        verify(skier2).setFirstName(eq("Jane"));
        verify(skier2).setLastName(eq("Doe"));
        verify(skier2).setNumSkier(eq(1L));
        verify(skier2).setPistes(isA(Set.class));
        verify(skier2).setRegistrations(isA(Set.class));
        verify(skier2).setSubscription(isA(Subscription.class));
        verify(subscription3).getStartDate();
        verify(subscription3).getTypeSub();
        verify(subscription3, atLeast(1)).setEndDate(Mockito.<LocalDate>any());
        verify(subscription3).setNumSub(eq(1L));
        verify(subscription3).setPrice(eq(10.0f));
        verify(subscription3).setStartDate(isA(LocalDate.class));
        verify(subscription3).setTypeSub(eq(TypeSubscription.ANNUAL));
        assertSame(skier, actualAddSkierResult);
    }

    /**
     * Method under test: {@link SkierServicesImpl#addSkier(Skier)}
     */
    @Test
    public void testAddSkier3() {
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
        when(iSkierRepository.save(Mockito.<Skier>any())).thenReturn(skier);

        Subscription subscription2 = new Subscription();
        subscription2.setEndDate(LocalDate.of(1970, 1, 1));
        subscription2.setNumSub(1L);
        subscription2.setPrice(10.0f);
        subscription2.setStartDate(LocalDate.of(1970, 1, 1));
        subscription2.setTypeSub(TypeSubscription.ANNUAL);
        Subscription subscription3 = mock(Subscription.class);
        when(subscription3.getStartDate()).thenReturn(LocalDate.of(1970, 1, 1));
        when(subscription3.getTypeSub()).thenReturn(TypeSubscription.SEMESTRIEL);
        doNothing().when(subscription3).setEndDate(Mockito.<LocalDate>any());
        doNothing().when(subscription3).setNumSub(Mockito.<Long>any());
        doNothing().when(subscription3).setPrice(Mockito.<Float>any());
        doNothing().when(subscription3).setStartDate(Mockito.<LocalDate>any());
        doNothing().when(subscription3).setTypeSub(Mockito.<TypeSubscription>any());
        subscription3.setEndDate(LocalDate.of(1970, 1, 1));
        subscription3.setNumSub(1L);
        subscription3.setPrice(10.0f);
        subscription3.setStartDate(LocalDate.of(1970, 1, 1));
        subscription3.setTypeSub(TypeSubscription.ANNUAL);
        Skier skier2 = mock(Skier.class);
        when(skier2.getSubscription()).thenReturn(subscription3);
        doNothing().when(skier2).setCity(Mockito.<String>any());
        doNothing().when(skier2).setDateOfBirth(Mockito.<LocalDate>any());
        doNothing().when(skier2).setFirstName(Mockito.<String>any());
        doNothing().when(skier2).setLastName(Mockito.<String>any());
        doNothing().when(skier2).setNumSkier(Mockito.<Long>any());
        doNothing().when(skier2).setPistes(Mockito.<Set<Piste>>any());
        doNothing().when(skier2).setRegistrations(Mockito.<Set<Registration>>any());
        doNothing().when(skier2).setSubscription(Mockito.<Subscription>any());
        skier2.setCity("Oxford");
        skier2.setDateOfBirth(LocalDate.of(1970, 1, 1));
        skier2.setFirstName("Jane");
        skier2.setLastName("Doe");
        skier2.setNumSkier(1L);
        skier2.setPistes(new HashSet<>());
        skier2.setRegistrations(new HashSet<>());
        skier2.setSubscription(subscription2);

        // Act
        Skier actualAddSkierResult = skierServicesImpl.addSkier(skier2);

        // Assert
        verify(iSkierRepository).save(isA(Skier.class));
        verify(skier2, atLeast(1)).getSubscription();
        verify(skier2).setCity(eq("Oxford"));
        verify(skier2).setDateOfBirth(isA(LocalDate.class));
        verify(skier2).setFirstName(eq("Jane"));
        verify(skier2).setLastName(eq("Doe"));
        verify(skier2).setNumSkier(eq(1L));
        verify(skier2).setPistes(isA(Set.class));
        verify(skier2).setRegistrations(isA(Set.class));
        verify(skier2).setSubscription(isA(Subscription.class));
        verify(subscription3).getStartDate();
        verify(subscription3).getTypeSub();
        verify(subscription3, atLeast(1)).setEndDate(Mockito.<LocalDate>any());
        verify(subscription3).setNumSub(eq(1L));
        verify(subscription3).setPrice(eq(10.0f));
        verify(subscription3).setStartDate(isA(LocalDate.class));
        verify(subscription3).setTypeSub(eq(TypeSubscription.ANNUAL));
        assertSame(skier, actualAddSkierResult);
    }

    /**
     * Method under test:
     * {@link SkierServicesImpl#assignSkierToSubscription(Long, Long)}
     */
    @Test
    public void testAssignSkierToSubscription() {
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
        when(iSkierRepository.save(Mockito.<Skier>any())).thenReturn(skier2);
        when(iSkierRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Subscription subscription3 = new Subscription();
        subscription3.setEndDate(LocalDate.of(1970, 1, 1));
        subscription3.setNumSub(1L);
        subscription3.setPrice(10.0f);
        subscription3.setStartDate(LocalDate.of(1970, 1, 1));
        subscription3.setTypeSub(TypeSubscription.ANNUAL);
        Optional<Subscription> ofResult2 = Optional.of(subscription3);
        when(iSubscriptionRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        // Act
        Skier actualAssignSkierToSubscriptionResult = skierServicesImpl.assignSkierToSubscription(1L, 1L);

        // Assert
        verify(iSkierRepository).findById(eq(1L));
        verify(iSubscriptionRepository).findById(eq(1L));
        verify(iSkierRepository).save(isA(Skier.class));
        assertSame(skier2, actualAssignSkierToSubscriptionResult);
    }

    /**
     * Method under test:
     * {@link SkierServicesImpl#addSkierAndAssignToCourse(Skier, Long)}
     */
    @Test
    public void testAddSkierAndAssignToCourse() {
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
        when(iSkierRepository.save(Mockito.<Skier>any())).thenReturn(skier);

        Course course = new Course();
        course.setLevel(1);
        course.setNumCourse(1L);
        course.setPrice(10.0f);
        course.setRegistrations(new HashSet<>());
        course.setSupport(Support.SKI);
        course.setTimeSlot(1);
        course.setTypeCourse(TypeCourse.COLLECTIVE_CHILDREN);
        when(iCourseRepository.getById(Mockito.<Long>any())).thenReturn(course);

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

        // Act
        Skier actualAddSkierAndAssignToCourseResult = skierServicesImpl.addSkierAndAssignToCourse(skier2, 1L);

        // Assert
        verify(iCourseRepository).getById(eq(1L));
        verify(iSkierRepository).save(isA(Skier.class));
        assertSame(skier, actualAddSkierAndAssignToCourseResult);
    }

    /**
     * Method under test:
     * {@link SkierServicesImpl#addSkierAndAssignToCourse(Skier, Long)}
     */
    @Test
    public void testAddSkierAndAssignToCourse2() {
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

        HashSet<Registration> registrations = new HashSet<>();
        registrations.add(registration);

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
        skier2.setRegistrations(registrations);
        skier2.setSubscription(subscription2);
        when(iSkierRepository.save(Mockito.<Skier>any())).thenReturn(skier2);

        Course course2 = new Course();
        course2.setLevel(1);
        course2.setNumCourse(1L);
        course2.setPrice(10.0f);
        course2.setRegistrations(new HashSet<>());
        course2.setSupport(Support.SKI);
        course2.setTimeSlot(1);
        course2.setTypeCourse(TypeCourse.COLLECTIVE_CHILDREN);
        when(iCourseRepository.getById(Mockito.<Long>any())).thenReturn(course2);

        Course course3 = new Course();
        course3.setLevel(1);
        course3.setNumCourse(1L);
        course3.setPrice(10.0f);
        course3.setRegistrations(new HashSet<>());
        course3.setSupport(Support.SKI);
        course3.setTimeSlot(1);
        course3.setTypeCourse(TypeCourse.COLLECTIVE_CHILDREN);

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
        registration2.setCourse(course3);
        registration2.setNumRegistration(1L);
        registration2.setNumWeek(10);
        registration2.setSkier(skier3);
        when(iRegistrationRepository.save(Mockito.<Registration>any())).thenReturn(registration2);

        Subscription subscription4 = new Subscription();
        subscription4.setEndDate(LocalDate.of(1970, 1, 1));
        subscription4.setNumSub(1L);
        subscription4.setPrice(10.0f);
        subscription4.setStartDate(LocalDate.of(1970, 1, 1));
        subscription4.setTypeSub(TypeSubscription.ANNUAL);

        Skier skier4 = new Skier();
        skier4.setCity("Oxford");
        skier4.setDateOfBirth(LocalDate.of(1970, 1, 1));
        skier4.setFirstName("Jane");
        skier4.setLastName("Doe");
        skier4.setNumSkier(1L);
        skier4.setPistes(new HashSet<>());
        skier4.setRegistrations(new HashSet<>());
        skier4.setSubscription(subscription4);

        // Act
        Skier actualAddSkierAndAssignToCourseResult = skierServicesImpl.addSkierAndAssignToCourse(skier4, 1L);

        // Assert
        verify(iCourseRepository).getById(eq(1L));
        verify(iRegistrationRepository).save(isA(Registration.class));
        verify(iSkierRepository).save(isA(Skier.class));
        assertSame(skier2, actualAddSkierAndAssignToCourseResult);
    }

    /**
     * Method under test: {@link SkierServicesImpl#removeSkier(Long)}
     */
    @Test
    public void testRemoveSkier() {
        // Arrange
        doNothing().when(iSkierRepository).deleteById(Mockito.<Long>any());

        // Act
        skierServicesImpl.removeSkier(1L);

        // Assert that nothing has changed
        verify(iSkierRepository).deleteById(eq(1L));
    }

    /**
     * Method under test: {@link SkierServicesImpl#retrieveSkier(Long)}
     */
    @Test
    public void testRetrieveSkier() {
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

        // Act
        Skier actualRetrieveSkierResult = skierServicesImpl.retrieveSkier(1L);

        // Assert
        verify(iSkierRepository).findById(eq(1L));
        assertSame(skier, actualRetrieveSkierResult);
    }

    /**
     * Method under test: {@link SkierServicesImpl#assignSkierToPiste(Long, Long)}
     */
    @Test
    public void testAssignSkierToPiste() {
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
        when(iSkierRepository.save(Mockito.<Skier>any())).thenReturn(skier2);
        when(iSkierRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Piste piste = new Piste();
        piste.setColor(Color.GREEN);
        piste.setLength(3);
        piste.setNamePiste("Name Piste");
        piste.setNumPiste(1L);
        piste.setSkiers(new HashSet<>());
        piste.setSlope(1);
        Optional<Piste> ofResult2 = Optional.of(piste);
        when(iPisteRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        // Act
        Skier actualAssignSkierToPisteResult = skierServicesImpl.assignSkierToPiste(1L, 1L);

        // Assert
        verify(iPisteRepository).findById(eq(1L));
        verify(iSkierRepository).findById(eq(1L));
        verify(iSkierRepository).save(isA(Skier.class));
        assertSame(skier2, actualAssignSkierToPisteResult);
    }

    /**
     * Method under test:
     * {@link SkierServicesImpl#retrieveSkiersBySubscriptionType(TypeSubscription)}
     */
    @Test
    public void testRetrieveSkiersBySubscriptionType() {
        // Arrange, Act and Assert
        assertTrue(skierServicesImpl.retrieveSkiersBySubscriptionType(TypeSubscription.MONTHLY).isEmpty());
        assertTrue(skierServicesImpl.retrieveSkiersBySubscriptionType(TypeSubscription.SEMESTRIEL).isEmpty());
    }
}
