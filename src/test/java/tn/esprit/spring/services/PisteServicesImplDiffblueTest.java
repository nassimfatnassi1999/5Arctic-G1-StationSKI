package tn.esprit.spring.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;

import org.hibernate.collection.internal.PersistentSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tn.esprit.spring.entities.Color;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.repositories.IPisteRepository;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class PisteServicesImplDiffblueTest {
    @MockBean
    private IRegistrationServices iRegistrationServices;

    @MockBean
    private IPisteRepository iPisteRepository;

    @Autowired
    private PisteServicesImpl pisteServicesImpl;

    /**
     * Method under test: {@link PisteServicesImpl#retrieveAllPistes()}
     */
    @Test
    public void testRetrieveAllPistes() {
        // Arrange and Act
        List<Piste> actualRetrieveAllPistesResult = pisteServicesImpl.retrieveAllPistes();

        // Assert
        assertEquals(8, actualRetrieveAllPistesResult.size());
        Piste getResult = actualRetrieveAllPistesResult.get(0);
        assertTrue(getResult.getSkiers() instanceof PersistentSet);
        Piste getResult2 = actualRetrieveAllPistesResult.get(1);
        assertTrue(getResult2.getSkiers() instanceof PersistentSet);
        Piste getResult3 = actualRetrieveAllPistesResult.get(2);
        assertTrue(getResult3.getSkiers() instanceof PersistentSet);
        Piste getResult4 = actualRetrieveAllPistesResult.get(3);
        assertTrue(getResult4.getSkiers() instanceof PersistentSet);
        Piste getResult5 = actualRetrieveAllPistesResult.get(4);
        assertTrue(getResult5.getSkiers() instanceof PersistentSet);
        Piste getResult6 = actualRetrieveAllPistesResult.get(5);
        assertTrue(getResult6.getSkiers() instanceof PersistentSet);
        Piste getResult7 = actualRetrieveAllPistesResult.get(6);
        assertTrue(getResult7.getSkiers() instanceof PersistentSet);
        Piste getResult8 = actualRetrieveAllPistesResult.get(7);
        assertTrue(getResult8.getSkiers() instanceof PersistentSet);
        assertEquals("Name Piste", getResult.getNamePiste());
        assertEquals("Name Piste", getResult2.getNamePiste());
        assertEquals("Name Piste", getResult3.getNamePiste());
        assertEquals("Name Piste", getResult4.getNamePiste());
        assertEquals("Name Piste", getResult5.getNamePiste());
        assertEquals("Name Piste", getResult6.getNamePiste());
        assertEquals("Name Piste", getResult7.getNamePiste());
        assertEquals("Name Piste", getResult8.getNamePiste());
        assertEquals(1, getResult.getSlope());
        assertEquals(1, getResult2.getSlope());
        assertEquals(1, getResult3.getSlope());
        assertEquals(1, getResult4.getSlope());
        assertEquals(1, getResult5.getSlope());
        assertEquals(1, getResult6.getSlope());
        assertEquals(1, getResult7.getSlope());
        assertEquals(1, getResult8.getSlope());
        assertEquals(2L, getResult.getNumPiste().longValue());
        assertEquals(3, getResult.getLength());
        assertEquals(3, getResult2.getLength());
        assertEquals(3, getResult3.getLength());
        assertEquals(3, getResult4.getLength());
        assertEquals(3, getResult5.getLength());
        assertEquals(3, getResult6.getLength());
        assertEquals(3, getResult7.getLength());
        assertEquals(3, getResult8.getLength());
        assertEquals(3L, getResult2.getNumPiste().longValue());
        assertEquals(4L, getResult3.getNumPiste().longValue());
        assertEquals(5L, getResult4.getNumPiste().longValue());
        assertEquals(6L, getResult5.getNumPiste().longValue());
        assertEquals(7L, getResult6.getNumPiste().longValue());
        assertEquals(8L, getResult7.getNumPiste().longValue());
        assertEquals(9L, getResult8.getNumPiste().longValue());
        assertEquals(Color.GREEN, getResult.getColor());
        assertEquals(Color.GREEN, getResult2.getColor());
        assertEquals(Color.GREEN, getResult3.getColor());
        assertEquals(Color.GREEN, getResult4.getColor());
        assertEquals(Color.RED, getResult5.getColor());
        assertEquals(Color.RED, getResult6.getColor());
        assertEquals(Color.RED, getResult7.getColor());
        assertEquals(Color.RED, getResult8.getColor());
    }

    /**
     * Method under test: {@link PisteServicesImpl#addPiste(Piste)}
     */
    @Test
    public void testAddPiste() {
        // Arrange
        Piste piste = new Piste();
        piste.setColor(Color.GREEN);
        piste.setLength(3);
        piste.setNamePiste("Name Piste");
        piste.setNumPiste(1L);
        piste.setSkiers(new HashSet<>());
        piste.setSlope(1);
        when(iPisteRepository.save(Mockito.<Piste>any())).thenReturn(piste);

        Piste piste2 = new Piste();
        piste2.setColor(Color.GREEN);
        piste2.setLength(3);
        piste2.setNamePiste("Name Piste");
        piste2.setNumPiste(1L);
        piste2.setSkiers(new HashSet<>());
        piste2.setSlope(1);

        // Act
        Piste actualAddPisteResult = pisteServicesImpl.addPiste(piste2);

        // Assert
        verify(iPisteRepository).save(isA(Piste.class));
        assertSame(piste, actualAddPisteResult);
    }

    /**
     * Method under test: {@link PisteServicesImpl#removePiste(Long)}
     */
    @Test
    public void testRemovePiste() {
        // Arrange
        doNothing().when(iPisteRepository).deleteById(Mockito.<Long>any());

        // Act
        pisteServicesImpl.removePiste(1L);

        // Assert that nothing has changed
        verify(iPisteRepository).deleteById(eq(1L));
    }

    /**
     * Method under test: {@link PisteServicesImpl#retrievePiste(Long)}
     */
    @Test
    public void testRetrievePiste() {
        // Arrange, Act and Assert
        assertNull(pisteServicesImpl.retrievePiste(1L));
    }
}
