package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.TypeSubscription;

import java.time.LocalDate;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static tn.esprit.spring.entities.TypeSubscription.ANNUAL;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class SubscriptionServiceImplTest {

    @MockBean
    private ISubscriptionServices subscriptionServices; // Mocked service

    @InjectMocks
    private SubscriptionServiceImplTest subscriptionServiceImplTest; // Test subject with mocks injected

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testaddSubscription() {
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

        when(subscriptionServices.addSubscription(addSubscription)).thenReturn(savedSubscription);

        Subscription result = subscriptionServices.addSubscription(addSubscription);
        assertNotNull(result.getNumSub());
        assertEquals(1L, result.getNumSub());
    }

    @Test
    void testupdateSubscription() {
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

        when(subscriptionServices.addSubscription(addSubscription)).thenReturn(savedSubscription);

        // Modify the subscription's price
        savedSubscription.setPrice(22.3f);

        when(subscriptionServices.updateSubscription(savedSubscription)).thenReturn(savedSubscription);

        Subscription updatedSubscription = subscriptionServices.updateSubscription(savedSubscription);
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

        when(subscriptionServices.retrieveSubscriptionById(subscriptionId)).thenReturn(retrievedSubscription);

        Subscription result = subscriptionServices.retrieveSubscriptionById(subscriptionId);
        assertNotNull(result);
        assertEquals(subscriptionId, result.getNumSub());
    }

    @Test
    void testgetSubscriptionByType() {
        TypeSubscription subscriptionType = ANNUAL;

        Set<Subscription> subscriptions = new HashSet<>();
        subscriptions.add(Subscription.builder()
                .numSub(1L)
                .startDate(LocalDate.parse("2024-10-10"))
                .endDate(LocalDate.parse("2024-11-11"))
                .typeSub(subscriptionType)
                .price(1.11f)
                .build());

        when(subscriptionServices.getSubscriptionByType(subscriptionType)).thenReturn(subscriptions);

        Set<Subscription> result = subscriptionServices.getSubscriptionByType(subscriptionType);
        assertNotNull(result);
        for (Subscription subscription : result) {
            assertEquals(subscriptionType, subscription.getTypeSub());
        }
    }
}
