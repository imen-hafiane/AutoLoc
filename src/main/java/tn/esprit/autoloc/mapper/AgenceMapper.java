package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.web.dto.AgenceRequestDTO;
import tn.esprit.autoloc.web.dto.AgenceResponseDTO;

@Component
public class AgenceMapper {

    public AgenceResponseDTO toDto(Agence agence) {
        AgenceResponseDTO dto = new AgenceResponseDTO();
        dto.setIdAgence(agence.getIdAgence());
        dto.setNom(agence.getNom());
        dto.setVille(agence.getVille());
        dto.setAdresse(agence.getAdresse());
        dto.setTelephone(agence.getTelephone());
        return dto;
    }

    public Agence toEntity(AgenceRequestDTO dto) {
        Agence agence = new Agence();
        updateEntity(agence, dto);
        return agence;
    }

    // recopie les champs modifiables, jamais l'identifiant
    public void updateEntity(Agence agence, AgenceRequestDTO dto) {
        agence.setNom(dto.getNom());
        agence.setVille(dto.getVille());
        agence.setAdresse(dto.getAdresse());
        agence.setTelephone(dto.getTelephone());
    }
}
