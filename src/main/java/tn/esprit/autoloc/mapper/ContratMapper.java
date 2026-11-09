package tn.esprit.autoloc.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.web.dto.ContratRequestDTO;
import tn.esprit.autoloc.web.dto.ContratResponseDTO;

@Component
@RequiredArgsConstructor
public class ContratMapper {

    private final PaiementMapper paiementMapper;

    public ContratResponseDTO toDto(Contrat contrat) {
        ContratResponseDTO dto = new ContratResponseDTO();
        dto.setIdContrat(contrat.getIdContrat());
        dto.setDateSignature(contrat.getDateSignature());
        dto.setMontantTotal(contrat.getMontantTotal());
        dto.setValide(contrat.isValide());
        dto.setPaiements(contrat.getPaiements() == null ? List.of()
                : contrat.getPaiements().stream().map(paiementMapper::toDto).toList());
        return dto;
    }

    // crée le contrat et ses paiements ; chaque paiement est relié à son contrat (côté propriétaire)
    public Contrat toEntity(ContratRequestDTO dto) {
        Contrat contrat = new Contrat();
        updateEntity(contrat, dto);
        Set<Paiement> paiements = new HashSet<>();
        if (dto.getPaiements() != null) {
            dto.getPaiements().forEach(p -> {
                Paiement paiement = paiementMapper.toEntity(p);
                paiement.setContrat(contrat);
                paiements.add(paiement);
            });
        }
        contrat.setPaiements(paiements);
        return contrat;
    }

    // la collection de paiements n'est pas modifiée (orphanRemoval)
    public void updateEntity(Contrat contrat, ContratRequestDTO dto) {
        contrat.setDateSignature(dto.getDateSignature());
        contrat.setMontantTotal(dto.getMontantTotal());
        contrat.setValide(dto.isValide());
    }
}
