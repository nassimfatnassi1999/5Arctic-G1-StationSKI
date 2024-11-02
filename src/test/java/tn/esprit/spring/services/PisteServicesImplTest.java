package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.spring.entities.Color;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.repositories.IPisteRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class PisteServicesImplTest {


    @Mock
    private IPisteRepository pisteRepository; // Mock the repository

    @InjectMocks
    private PisteServicesImpl pisteServiceImpl; // Inject the service

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }


    @Test
    void testRetrieveAllPistes() {
        List<Piste> pistes = new ArrayList<>();
        pistes.add(new Piste(1L, "Blue Run", Color.BLUE, 1500, 25, null));
        pistes.add(new Piste(2L, "Red Run", Color.RED, 2000, 30, null));

        when(pisteRepository.findAll()).thenReturn(pistes);

        List<Piste> result = pisteServiceImpl.retrieveAllPistes();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Blue Run", result.get(0).getNamePiste());
    }

    @Test
    void testAddPiste() {
        Piste newPiste = new Piste();
        newPiste.setNamePiste("Green Run");
        newPiste.setColor(Color.GREEN);
        newPiste.setLength(1800);
        newPiste.setSlope(20);

        Piste savedPiste = new Piste();
        savedPiste.setNumPiste(3L);
        savedPiste.setNamePiste("Green Run");
        savedPiste.setColor(Color.GREEN);
        savedPiste.setLength(1800);
        savedPiste.setSlope(20);

        when(pisteRepository.save(newPiste)).thenReturn(savedPiste);

        Piste result = pisteServiceImpl.addPiste(newPiste);

        assertNotNull(result.getNumPiste());
        assertEquals("Green Run", result.getNamePiste());
    }

    @Test
    void testRemovePiste() {
        Long pisteId = 1L;

        // No need to return anything from the delete method, just verify it gets called
        pisteServiceImpl.removePiste(pisteId);

        verify(pisteRepository, times(1)).deleteById(pisteId);
    }
}