package com.library.service;

import com.library.dto.PatronDto;
import com.library.entity.Patron;
import com.library.exception.PatronException;
import com.library.dao.PatronRepo;
import com.library.service.imp.PatronServiceimp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



public class PatronServiceimpTest {
    @Mock
    private PatronRepo patronRepo;

    @InjectMocks
    private PatronServiceimp patronServiceimp;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSavePatron() {
        PatronDto patronDto = new PatronDto();
        patronDto.setFullName("John Doe");
        patronDto.setEmail("john.doe@example.com");
        patronDto.setPhoneNumber("123-456-7890");
        patronDto.setAddress("123 Elm Street");

        Patron patron = new Patron();
        patron.setPId(1L); // Mock ID value
        patron.setFullName("John Doe");
        patron.setEmail("john.doe@example.com");
        patron.setPhoneNumber("123-456-7890");
        patron.setAddress("123 Main St, Anytown, USA");

        // Mock behavior for patronRepo.save()
        when(patronRepo.save(any(Patron.class))).thenReturn(patron);

        // Act
        Patron result = patronServiceimp.savepatron(patronDto);

        // Assert
        assertNotNull(result, "The result should not be null.");
        assertEquals("John Doe", result.getFullName(), "The full name should match.");
        assertEquals("john.doe@example.com", result.getEmail(), "The email should match.");
        assertEquals("123-456-7890", result.getPhoneNumber(), "The phone number should match.");
        assertEquals("123 Main St, Anytown, USA", result.getAddress(), "The address should match.");
    }

    @Test
    void testGetPatronById() {
        // Arrange
        Long id = 1L;
        Patron patron = new Patron();
        patron.setPId(id);
        patron.setFullName("John Doe");
        when(patronRepo.findById(anyLong())).thenReturn(Optional.of(patron));

        // Act
        PatronDto patronDto = patronServiceimp.getPatronByid(id);

        // Assert
        assertNotNull(patronDto);
        assertEquals(id, patronDto.getPId());
        assertEquals("John Doe", patronDto.getFullName());
        verify(patronRepo, times(1)).findById(anyLong());
    }

    @Test
    void testGetAllPatrons() {
        // Arrange
        Patron patron = new Patron();
        patron.setFullName("John Doe");
        when(patronRepo.findAll()).thenReturn(List.of(patron));

        // Act
        List<PatronDto> patronDtoList = patronServiceimp.getallpatrons();

        // Assert
        assertNotNull(patronDtoList);
        assertEquals(1, patronDtoList.size());
        assertEquals("John Doe", patronDtoList.get(0).getFullName());
        verify(patronRepo, times(1)).findAll();
    }

    @Test
    void testEditPatron() {
        // Arrange
        Long id = 1L;

        // Create an existing Patron with old data
        Patron existingPatron = new Patron();
        existingPatron.setPId(id);
        existingPatron.setFullName("John Doe");
        existingPatron.setEmail("john.doe@example.com");
        existingPatron.setPhoneNumber("123-456-7890");
        existingPatron.setAddress("123 Main St, Anytown, USA");

        // Create a PatronDto with new data
        PatronDto patronDto = new PatronDto();
        patronDto.setFullName("New Name");
        patronDto.setEmail("new.email@example.com");
        patronDto.setPhoneNumber("987-654-3210");
        patronDto.setAddress("456 Elm St, Othertown, USA");

        when(patronRepo.findById(id)).thenReturn(Optional.of(existingPatron));
        when(patronRepo.save(any(Patron.class))).thenAnswer(invocation -> {
            Patron savedPatron = invocation.getArgument(0);
            // Manually set fields for verification
            savedPatron.setFullName(patronDto.getFullName());
            return savedPatron;
        });

        // Act
        PatronDto updatedPatronDto = patronServiceimp.editePatron(patronDto, id);

        // Assert
        assertNotNull(updatedPatronDto, "The updated patron DTO should not be null.");
        assertEquals("New Name", updatedPatronDto.getFullName(), "The full name should be updated.");
        assertEquals("new.email@example.com", updatedPatronDto.getEmail(), "The email should be updated.");
        assertEquals("987-654-3210", updatedPatronDto.getPhoneNumber(), "The phone number should be updated.");
        assertEquals("456 Elm St, Othertown, USA", updatedPatronDto.getAddress(), "The address should be updated.");
    }

    @Test
    void testDeletePatronById() {
        // Arrange
        Long id = 1L;
        Patron patron = new Patron();
        patron.setPId(id);
        when(patronRepo.findById(anyLong())).thenReturn(Optional.of(patron));

        // Act
        patronServiceimp.deletePatronById(id);

        // Assert
        verify(patronRepo, times(1)).deleteById(anyLong());
    }

    @Test
    void testDeletePatronById_NotFound() {
        // Arrange
        Long id = 1L;
        when(patronRepo.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PatronException.class, () -> patronServiceimp.deletePatronById(id));
    }
}
