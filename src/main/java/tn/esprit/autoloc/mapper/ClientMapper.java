package tn.esprit.autoloc.mapper;

import java.time.LocalDate;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.web.dto.ClientRequestDTO;
import tn.esprit.autoloc.web.dto.ClientResponseDTO;

@Component
public class ClientMapper {

    // numPermis n'est pas recopié dans la réponse (donnée sensible)
    public ClientResponseDTO toDto(Client client) {
        ClientResponseDTO dto = new ClientResponseDTO();
        dto.setIdClient(client.getIdClient());
        dto.setNom(client.getNom());
        dto.setPrenom(client.getPrenom());
        dto.setEmail(client.getEmail());
        dto.setTelephone(client.getTelephone());
        dto.setDateInscription(client.getDateInscription());
        return dto;
    }

    public Client toEntity(ClientRequestDTO dto) {
        Client client = new Client();
        updateEntity(client, dto);
        if (client.getDateInscription() == null) {
            client.setDateInscription(LocalDate.now());
        }
        return client;
    }

    public void updateEntity(Client client, ClientRequestDTO dto) {
        client.setNom(dto.getNom());
        client.setPrenom(dto.getPrenom());
        client.setEmail(dto.getEmail());
        client.setTelephone(dto.getTelephone());
        client.setNumPermis(dto.getNumPermis());
        if (dto.getDateInscription() != null) {
            client.setDateInscription(dto.getDateInscription());
        }
    }
}
