package com.abutua.agenda.domain.mappers;

import com.abutua.agenda.domain.entities.Client;
import com.abutua.agenda.dto.ClientRequest;
import com.abutua.agenda.dto.ClientResponse;

public class ClientMapper {

    public static ClientResponse toClientResponseDTO(Client client) {

        return new ClientResponse(
                client.getId(),
                client.getName(),
                client.getPhone(),
                client.getDateOfBirth(),
                client.getComments()
                );
    }

    public static Client fromClientRequestDTO(ClientRequest clientRequest) {
        return new Client(
                clientRequest.name(),
                clientRequest.phone(),
                clientRequest.dateOfBirth(),
                clientRequest.comments()
                );
    }
}
