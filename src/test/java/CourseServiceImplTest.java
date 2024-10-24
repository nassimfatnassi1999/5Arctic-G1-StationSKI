import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.services.CourseServicesImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class CourseServiceImplTest {


    private final CourseServicesImpl courseServices = new CourseServicesImpl(null); // on ne teste pas le repo ici

    @Test
    public void testCalculateDiscountForAdvancedCourse() {
        // Given
        Course course = new Course();
        course.setLevel(3); // niveau avancé
        course.setPrice(100f); // prix original

        // When
        Float discountedPrice = courseServices.calculateDiscount(course);

        // Then
        assertEquals(90f, discountedPrice); // 10% de réduction donc 90
    }

    @Test
    public void testNoDiscountForBeginnerCourse() {
        // Given
        Course course = new Course();
        course.setLevel(1); // niveau débutant
        course.setPrice(100f);

        // When
        Float discountedPrice = courseServices.calculateDiscount(course);

        // Then
        assertEquals(100f, discountedPrice); // pas de réduction pour les débutants
    }




}
