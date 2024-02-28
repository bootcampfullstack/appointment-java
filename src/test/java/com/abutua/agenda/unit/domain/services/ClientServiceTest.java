package com.abutua.agenda.unit.domain.services;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.EnabledIf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.abutua.agenda.domain.entities.Client;
import com.abutua.agenda.domain.repositories.ClientRepository;
import com.abutua.agenda.domain.services.ClientService;
import com.abutua.agenda.dto.ClientRequest;
import com.abutua.agenda.dto.ClientResponse;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepositoryMock;

    @Spy
    @InjectMocks
    private ClientService clientService;

    @Test
    void saveShouldPersistClient() {

        // Mock to client repository save method.
        Client newClient = new Client("Ana", "1599999999", LocalDate.of(2000, 10, 18));
        Client savedClient = new Client(1L, "Ana", "1599999999", LocalDate.of(2000, 10, 18));
        given(clientRepositoryMock.save(newClient)).willReturn(savedClient);

        ClientRequest newClientRequest = new ClientRequest("Ana", "1599999999", LocalDate.of(2000, 10, 18));
        ClientResponse expectClientResponse = new ClientResponse(1L, "Ana", "1599999999", LocalDate.of(2000, 10, 18));

        var foundClientResponse = clientService.save(newClientRequest);

        verify(clientRepositoryMock).save(any(Client.class));

        assertNotNull(foundClientResponse);
        assertNotNull(foundClientResponse.id());

        assertEquals(expectClientResponse.id(), foundClientResponse.id());
        assertEquals(expectClientResponse.name(), foundClientResponse.name());
        assertEquals(expectClientResponse.phone(), foundClientResponse.phone());
        assertEquals(expectClientResponse.dateOfBirth(), foundClientResponse.dateOfBirth());

    }

    @Test
    void updateShouldPersistClient() {
        // Mock
        Client client = mock(Client.class);
        given(clientRepositoryMock.getReferenceById(1l)).willReturn(client);
        given(clientRepositoryMock.save(client)).willReturn(client);

        //Input
        ClientRequest clientRequest = new ClientRequest("Ana", "1599999999", LocalDate.of(2000, 10, 18));

        clientService.update(1L, clientRequest);
        
        // Verificando se os métodos corretos foram chamados no mock do Client
        verify(client).setName("Ana");
        verify(client).setPhone("1599999999");
        verify(client).setDateOfBirth(LocalDate.of(2000, 10, 18));

        verify(clientRepositoryMock).save((any(Client.class)));
        verify(clientRepositoryMock).getReferenceById((any(Long.class)));
    }

    @Test
    void updateShouldThrowsEntityNotFoundExceptiont() {

        // Mock
        given(clientRepositoryMock.getReferenceById(1l)).willThrow(EntityNotFoundException.class);

        ClientRequest clientRequest = new ClientRequest("Ana", "1599999999", LocalDate.of(2000, 10, 18));

        assertThrows(EntityNotFoundException.class, () -> clientService.update(1L, clientRequest));

        verify(clientRepositoryMock).getReferenceById((any(Long.class)));
    }

}
