package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.web.dto.PaiementRequestDTO;
import tn.esprit.autoloc.web.dto.PaiementResponseDTO;

@Component
public class PaiementMapper {

    public PaiementResponseDTO toDto(Paiement paiement) {
        PaiementResponseDTO dto = new PaiementResponseDTO();
        dto.setIdPaiement(paiement.getIdPaiement());
        dto.setMontant(paiement.getMontant());
        dto.setDatePaiement(paiement.getDatePaiement());
        dto.setModePaiement(paiement.getModePaiement());
        if (paiement.getContrat() != null) {
            dto.setIdContrat(paiement.getContrat().getIdContrat());
        }
        return dto;
    }

    public Paiement toEntity(PaiementRequestDTO dto) {
        Paiement paiement = new Paiement();
        paiement.setMontant(dto.getMontant());
        paiement.setDatePaiement(dto.getDatePaiement());
        paiement.setModePaiement(dto.getModePaiement());
        return paiement;
    }
}
