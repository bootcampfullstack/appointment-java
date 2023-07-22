package com.abutua.agenda.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import com.abutua.agenda.domain.mappers.ClientMapper;
import com.abutua.agenda.domain.repositories.ClientRepository;
import com.abutua.agenda.domain.services.exceptions.DatabaseException;
import com.abutua.agenda.dto.ClientRequest;
import com.abutua.agenda.dto.ClientResponse;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Page<ClientResponse> findByNameContainingIgnoreCase(String name, int page, int size) {
        var pageRequest = PageRequest.of(page, size);
        var pageClient = clientRepository.findByNameContainingIgnoreCase(name, pageRequest);
        return pageClient.map(c -> ClientMapper.toClientResponseDTO(c));
    }

    public ClientResponse getById(long id) {
        var client = clientRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado."));

        return ClientMapper.toClientResponseDTO(client);
    }

    public ClientResponse save(ClientRequest clientRequest) {
        var client = clientRepository.save(ClientMapper.fromClientRequestDTO(clientRequest));
        return ClientMapper.toClientResponseDTO(client);
    }

    public void update(long id, ClientRequest clientRequest) {
        try {
            var client = clientRepository.getReferenceById(id);

            client.setName(clientRequest.name());
            client.setPhone(clientRequest.phone());
            client.setDateOfBirth(clientRequest.dateOfBirth());

            clientRepository.save(client);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Cliente não encontrado.");
        }
    }

    public void deleteById(long id) {
        try {
            if (clientRepository.existsById(id))
                clientRepository.deleteById(id);
            else {
                throw new EntityNotFoundException("Cliente não encontrado.");
            }
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Conflito ao remover o cliente", HttpStatus.BAD_REQUEST);
        }
    }

}
