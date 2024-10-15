package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.TypeSubscription;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static tn.esprit.spring.entities.TypeSubscription.ANNUAL;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SubscriptionServiceImplTest {

    @Mock
    private ISubscriptionServices subscriptionServices; // Service mocké

    @InjectMocks
    private SubscriptionServicesImpl subscriptionServiceImpl; // Classe à tester

    @BeforeEach
    void setUp() {
        // Initialiser les mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddSubscription() {
        // Préparation des données de test
        Subscription addSubscription = Subscription.builder()
                .startDate(LocalDate.parse("2024-10-10"))
                .endDate(LocalDate.parse("2024-11-11"))
                .typeSub(ANNUAL)
                .price(1.11f)
                .build();

        Subscription savedSubscription = Subscription.builder()
                .numSub(1L)
                .startDate(LocalDate.parse("2024-10-10"))
                .endDate(LocalDate.parse("2024-11-11"))
                .typeSub(ANNUAL)
                .price(1.11f)
                .build();

        // Simulation du comportement du service
        when(subscriptionServices.addSubscription(addSubscription)).thenReturn(savedSubscription);

        // Appel de la méthode à tester
        Subscription result = subscriptionServiceImpl.addSubscription(addSubscription);

        // Vérifications
        assertNotNull(result.getNumSub());
        assertEquals(1L, result.getNumSub());
    }

    @Test
    void testUpdateSubscription() {
        // Préparation des données de test
        Subscription savedSubscription = Subscription.builder()
                .numSub(1L)
                .startDate(LocalDate.parse("2024-10-10"))
                .endDate(LocalDate.parse("2024-11-11"))
                .typeSub(ANNUAL)
                .price(1.11f)
                .build();

        // Simulation du comportement du service
        when(subscriptionServices.updateSubscription(savedSubscription)).thenReturn(savedSubscription);

        // Modification du prix
        savedSubscription.setPrice(22.3f);

        // Appel de la méthode à tester
        Subscription updatedSubscription = subscriptionServiceImpl.updateSubscription(savedSubscription);

        // Vérifications
        assertNotNull(updatedSubscription.getNumSub());
        assertEquals(22.3f, updatedSubscription.getPrice());
    }

    @Test
    void testRetrieveSubscriptionById() {
        Long subscriptionId = 1L;

        Subscription retrievedSubscription = Subscription.builder()
                .numSub(subscriptionId)
                .startDate(LocalDate.parse("2024-10-10"))
                .endDate(LocalDate.parse("2024-11-11"))
                .typeSub(ANNUAL)
                .price(1.11f)
                .build();

        // Simulation du comportement du service
        when(subscriptionServices.retrieveSubscriptionById(subscriptionId)).thenReturn(retrievedSubscription);

        // Appel de la méthode à tester
        Subscription result = subscriptionServiceImpl.retrieveSubscriptionById(subscriptionId);

        // Vérifications
        assertNotNull(result);
        assertEquals(subscriptionId, result.getNumSub());
    }

    @Test
    void testGetSubscriptionByType() {
        TypeSubscription subscriptionType = ANNUAL;

        Set<Subscription> subscriptions = new HashSet<>();
        subscriptions.add(Subscription.builder()
                .numSub(1L)
                .startDate(LocalDate.parse("2024-10-10"))
                .endDate(LocalDate.parse("2024-11-11"))
                .typeSub(subscriptionType)
                .price(1.11f)
                .build());

        // Simulation du comportement du service
        when(subscriptionServices.getSubscriptionByType(subscriptionType)).thenReturn(subscriptions);

        // Appel de la méthode à tester
        Set<Subscription> result = subscriptionServiceImpl.getSubscriptionByType(subscriptionType);

        // Vérifications
        assertNotNull(result);
        assertFalse(result.isEmpty());
        for (Subscription subscription : result) {
            assertEquals(subscriptionType, subscription.getTypeSub());
        }
    }
}