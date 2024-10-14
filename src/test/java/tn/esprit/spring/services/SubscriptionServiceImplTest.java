package tn.esprit.spring.services;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.TypeSubscription;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static tn.esprit.spring.entities.TypeSubscription.ANNUAL;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.class)
@SpringBootTest
class SubscriptionServiceImplTest {
    ISubscriptionServices subscriptionServices;


    @Test
    @BeforeEach
      void testaddSubscription(){
Subscription addSubscription= Subscription.builder().startDate(LocalDate.parse("2024-10-10")).endDate(LocalDate.parse("2024-11-11")).typeSub(ANNUAL).price(1.11f).build();
Subscription saveSubscription=subscriptionServices.addSubscription(addSubscription);
        assertNotNull(saveSubscription.getNumSub());
    }
    @Test
    void testupdateSubscription(){
        Subscription addSubscription= Subscription.builder().startDate(LocalDate.parse("2024-10-10")).endDate(LocalDate.parse("2024-11-11")).typeSub(ANNUAL).price(1.11f).build();
        Subscription saveSubscription=subscriptionServices.addSubscription(addSubscription);
        saveSubscription.setPrice(22.3f);
        subscriptionServices.updateSubscription(saveSubscription);
        assertNotNull(saveSubscription.getNumSub());
        assertEquals(22.3f,saveSubscription.getPrice());
    }
    @Test
     void testRetrieveSubscriptionById() {
        Long subscriptionId = 1L;

        // Call the retrieve method
        Subscription retrievedSubscription = subscriptionServices.retrieveSubscriptionById(subscriptionId);

        // Assert that the retrieved subscription is not null and has the expected values
        assertNotNull(retrievedSubscription);
        assertEquals(subscriptionId, retrievedSubscription.getNumSub());
    }
    @Test
    void testgetSubscriptionByType() {
        TypeSubscription subscriptionType = ANNUAL;

        // Call the retrieve method
        Set<Subscription> retrievedSubscription = subscriptionServices.getSubscriptionByType(subscriptionType);

        // Assert that the retrieved subscription is not null and has the expected values
        assertNotNull(retrievedSubscription);
        for (Subscription subscription:retrievedSubscription){
        assertEquals(subscriptionType, subscription.getTypeSub());}
    }
}
