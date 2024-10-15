package tn.esprit.spring.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.TypeSubscription;
import tn.esprit.spring.repositories.ISkierRepository;
import tn.esprit.spring.repositories.ISubscriptionRepository;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class SubscriptionServicesImplDiffblueTest {
    @MockBean
    private IRegistrationServices iRegistrationServices;

    @MockBean
    private ISkierRepository iSkierRepository;

    @MockBean
    private ISubscriptionRepository iSubscriptionRepository;

    @Autowired
    private SubscriptionServicesImpl subscriptionServicesImpl;

    /**
     * Method under test:
     * {@link SubscriptionServicesImpl#addSubscription(Subscription)}
     */
    @Test
    public void testAddSubscription() {
        // Arrange
        Subscription subscription = new Subscription();
        subscription.setEndDate(LocalDate.of(1970, 1, 1));
        subscription.setNumSub(1L);
        subscription.setPrice(10.0f);
        subscription.setStartDate(LocalDate.of(1970, 1, 1));
        subscription.setTypeSub(TypeSubscription.ANNUAL);
        when(iSubscriptionRepository.save(Mockito.<Subscription>any())).thenReturn(subscription);

        Subscription subscription2 = new Subscription();
        subscription2.setEndDate(LocalDate.of(1970, 1, 1));
        subscription2.setNumSub(1L);
        subscription2.setPrice(10.0f);
        subscription2.setStartDate(LocalDate.of(1970, 1, 1));
        subscription2.setTypeSub(TypeSubscription.ANNUAL);

        // Act
        Subscription actualAddSubscriptionResult = subscriptionServicesImpl.addSubscription(subscription2);

        // Assert
        verify(iSubscriptionRepository).save(isA(Subscription.class));
        assertEquals("1971-01-01", subscription2.getEndDate().toString());
        assertSame(subscription, actualAddSubscriptionResult);
    }

    /**
     * Method under test:
     * {@link SubscriptionServicesImpl#addSubscription(Subscription)}
     */
    @Test
    public void testAddSubscription2() {
        // Arrange
        Subscription subscription = new Subscription();
        subscription.setEndDate(LocalDate.of(1970, 1, 1));
        subscription.setNumSub(1L);
        subscription.setPrice(10.0f);
        subscription.setStartDate(LocalDate.of(1970, 1, 1));
        subscription.setTypeSub(TypeSubscription.ANNUAL);
        when(iSubscriptionRepository.save(Mockito.<Subscription>any())).thenReturn(subscription);
        Subscription subscription2 = mock(Subscription.class);
        when(subscription2.getStartDate()).thenReturn(LocalDate.of(1970, 1, 1));
        when(subscription2.getTypeSub()).thenReturn(TypeSubscription.MONTHLY);
        doNothing().when(subscription2).setEndDate(Mockito.<LocalDate>any());
        doNothing().when(subscription2).setNumSub(Mockito.<Long>any());
        doNothing().when(subscription2).setPrice(Mockito.<Float>any());
        doNothing().when(subscription2).setStartDate(Mockito.<LocalDate>any());
        doNothing().when(subscription2).setTypeSub(Mockito.<TypeSubscription>any());
        subscription2.setEndDate(LocalDate.of(1970, 1, 1));
        subscription2.setNumSub(1L);
        subscription2.setPrice(10.0f);
        subscription2.setStartDate(LocalDate.of(1970, 1, 1));
        subscription2.setTypeSub(TypeSubscription.ANNUAL);

        // Act
        Subscription actualAddSubscriptionResult = subscriptionServicesImpl.addSubscription(subscription2);

        // Assert
        verify(iSubscriptionRepository).save(isA(Subscription.class));
        verify(subscription2).getStartDate();
        verify(subscription2).getTypeSub();
        verify(subscription2, atLeast(1)).setEndDate(Mockito.<LocalDate>any());
        verify(subscription2).setNumSub(eq(1L));
        verify(subscription2).setPrice(eq(10.0f));
        verify(subscription2).setStartDate(isA(LocalDate.class));
        verify(subscription2).setTypeSub(eq(TypeSubscription.ANNUAL));
        assertSame(subscription, actualAddSubscriptionResult);
    }

    /**
     * Method under test:
     * {@link SubscriptionServicesImpl#addSubscription(Subscription)}
     */
    @Test
    public void testAddSubscription3() {
        // Arrange
        Subscription subscription = new Subscription();
        subscription.setEndDate(LocalDate.of(1970, 1, 1));
        subscription.setNumSub(1L);
        subscription.setPrice(10.0f);
        subscription.setStartDate(LocalDate.of(1970, 1, 1));
        subscription.setTypeSub(TypeSubscription.ANNUAL);
        when(iSubscriptionRepository.save(Mockito.<Subscription>any())).thenReturn(subscription);
        Subscription subscription2 = mock(Subscription.class);
        when(subscription2.getStartDate()).thenReturn(LocalDate.of(1970, 1, 1));
        when(subscription2.getTypeSub()).thenReturn(TypeSubscription.SEMESTRIEL);
        doNothing().when(subscription2).setEndDate(Mockito.<LocalDate>any());
        doNothing().when(subscription2).setNumSub(Mockito.<Long>any());
        doNothing().when(subscription2).setPrice(Mockito.<Float>any());
        doNothing().when(subscription2).setStartDate(Mockito.<LocalDate>any());
        doNothing().when(subscription2).setTypeSub(Mockito.<TypeSubscription>any());
        subscription2.setEndDate(LocalDate.of(1970, 1, 1));
        subscription2.setNumSub(1L);
        subscription2.setPrice(10.0f);
        subscription2.setStartDate(LocalDate.of(1970, 1, 1));
        subscription2.setTypeSub(TypeSubscription.ANNUAL);

        // Act
        Subscription actualAddSubscriptionResult = subscriptionServicesImpl.addSubscription(subscription2);

        // Assert
        verify(iSubscriptionRepository).save(isA(Subscription.class));
        verify(subscription2).getStartDate();
        verify(subscription2).getTypeSub();
        verify(subscription2, atLeast(1)).setEndDate(Mockito.<LocalDate>any());
        verify(subscription2).setNumSub(eq(1L));
        verify(subscription2).setPrice(eq(10.0f));
        verify(subscription2).setStartDate(isA(LocalDate.class));
        verify(subscription2).setTypeSub(eq(TypeSubscription.ANNUAL));
        assertSame(subscription, actualAddSubscriptionResult);
    }

    /**
     * Method under test:
     * {@link SubscriptionServicesImpl#updateSubscription(Subscription)}
     */
    @Test
    public void testUpdateSubscription() {
        // Arrange
        Subscription subscription = new Subscription();
        LocalDate endDate = LocalDate.of(1970, 1, 1);
        subscription.setEndDate(endDate);
        subscription.setNumSub(1L);
        subscription.setPrice(10.0f);
        LocalDate startDate = LocalDate.of(1970, 1, 1);
        subscription.setStartDate(startDate);
        subscription.setTypeSub(TypeSubscription.ANNUAL);

        // Act
        Subscription actualUpdateSubscriptionResult = subscriptionServicesImpl.updateSubscription(subscription);

        // Assert
        LocalDate endDate2 = actualUpdateSubscriptionResult.getEndDate();
        assertEquals("1970-01-01", endDate2.toString());
        LocalDate startDate2 = actualUpdateSubscriptionResult.getStartDate();
        assertEquals("1970-01-01", startDate2.toString());
        assertEquals(10.0f, actualUpdateSubscriptionResult.getPrice().floatValue(), 0.0f);
        assertEquals(1L, actualUpdateSubscriptionResult.getNumSub().longValue());
        assertEquals(TypeSubscription.ANNUAL, actualUpdateSubscriptionResult.getTypeSub());
        assertSame(endDate, endDate2);
        assertSame(startDate, startDate2);
    }

    /**
     * Method under test:
     * {@link SubscriptionServicesImpl#retrieveSubscriptionById(Long)}
     */
    @Test
    public void testRetrieveSubscriptionById() {
        // Arrange, Act and Assert
        assertNull(subscriptionServicesImpl.retrieveSubscriptionById(0L));
    }

    /**
     * Method under test:
     * {@link SubscriptionServicesImpl#getSubscriptionByType(TypeSubscription)}
     */
    @Test
    public void testGetSubscriptionByType() {
        // Arrange, Act and Assert
        assertEquals(15, subscriptionServicesImpl.getSubscriptionByType(TypeSubscription.ANNUAL).size());
        assertEquals(4, subscriptionServicesImpl.getSubscriptionByType(TypeSubscription.MONTHLY).size());
        assertEquals(7, subscriptionServicesImpl.getSubscriptionByType(TypeSubscription.SEMESTRIEL).size());
    }

    /**
     * Method under test:
     * {@link SubscriptionServicesImpl#retrieveSubscriptionsByDates(LocalDate, LocalDate)}
     */
    @Test
    public void testRetrieveSubscriptionsByDates() {
        // Arrange
        ArrayList<Subscription> subscriptionList = new ArrayList<>();
        when(iSubscriptionRepository.getSubscriptionsByStartDateBetween(Mockito.<LocalDate>any(), Mockito.<LocalDate>any()))
                .thenReturn(subscriptionList);
        LocalDate startDate = LocalDate.of(1970, 1, 1);

        // Act
        List<Subscription> actualRetrieveSubscriptionsByDatesResult = subscriptionServicesImpl
                .retrieveSubscriptionsByDates(startDate, LocalDate.of(1970, 1, 1));

        // Assert
        verify(iSubscriptionRepository).getSubscriptionsByStartDateBetween(isA(LocalDate.class), isA(LocalDate.class));
        assertTrue(actualRetrieveSubscriptionsByDatesResult.isEmpty());
        assertSame(subscriptionList, actualRetrieveSubscriptionsByDatesResult);
    }

    /**
     * Method under test: {@link SubscriptionServicesImpl#retrieveSubscriptions()}
     */
    @Test
    public void testRetrieveSubscriptions() {
        // Arrange
        when(iSubscriptionRepository.findDistinctOrderByEndDateAsc()).thenReturn(new ArrayList<>());

        // Act
        subscriptionServicesImpl.retrieveSubscriptions();

        // Assert that nothing has changed
        verify(iSubscriptionRepository).findDistinctOrderByEndDateAsc();
    }

    /**
     * Method under test: {@link SubscriptionServicesImpl#retrieveSubscriptions()}
     */
    @Test
    public void testRetrieveSubscriptions2() {
        // Arrange
        Subscription subscription = new Subscription();
        subscription.setEndDate(LocalDate.of(1970, 1, 1));
        subscription.setNumSub(1L);
        subscription.setPrice(10.0f);
        subscription.setStartDate(LocalDate.of(1970, 1, 1));
        subscription.setTypeSub(TypeSubscription.ANNUAL);

        ArrayList<Subscription> subscriptionList = new ArrayList<>();
        subscriptionList.add(subscription);
        when(iSubscriptionRepository.findDistinctOrderByEndDateAsc()).thenReturn(subscriptionList);

        Subscription subscription2 = new Subscription();
        subscription2.setEndDate(LocalDate.of(1970, 1, 1));
        subscription2.setNumSub(1L);
        subscription2.setPrice(10.0f);
        subscription2.setStartDate(LocalDate.of(1970, 1, 1));
        subscription2.setTypeSub(TypeSubscription.ANNUAL);

        Skier skier = new Skier();
        skier.setCity("Oxford");
        skier.setDateOfBirth(LocalDate.of(1970, 1, 1));
        skier.setFirstName("Jane");
        skier.setLastName("Doe");
        skier.setNumSkier(1L);
        skier.setPistes(new HashSet<>());
        skier.setRegistrations(new HashSet<>());
        skier.setSubscription(subscription2);
        when(iSkierRepository.findBySubscription(Mockito.<Subscription>any())).thenReturn(skier);

        // Act
        subscriptionServicesImpl.retrieveSubscriptions();

        // Assert
        verify(iSkierRepository).findBySubscription(isA(Subscription.class));
        verify(iSubscriptionRepository).findDistinctOrderByEndDateAsc();
    }

    /**
     * Method under test:
     * {@link SubscriptionServicesImpl#showMonthlyRecurringRevenue()}
     */
    @Test
    public void testShowMonthlyRecurringRevenue() {
        // Arrange
        when(iSubscriptionRepository.recurringRevenueByTypeSubEquals(Mockito.<TypeSubscription>any())).thenReturn(10.0f);

        // Act
        subscriptionServicesImpl.showMonthlyRecurringRevenue();

        // Assert
        verify(iSubscriptionRepository, atLeast(1)).recurringRevenueByTypeSubEquals(Mockito.<TypeSubscription>any());
    }
}
