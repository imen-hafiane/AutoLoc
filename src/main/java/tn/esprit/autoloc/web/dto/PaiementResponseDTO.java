package tn.esprit.autoloc.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import tn.esprit.autoloc.domain.ModePaiement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaiementResponseDTO {

    private Long idPaiement;

    private BigDecimal montant;

    private LocalDate datePaiement;

    private ModePaiement modePaiement;

    private Long idContrat;
}
