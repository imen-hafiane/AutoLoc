package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class PaiementRequestDTO {

    @NotNull(message = "Le montant du paiement est obligatoire")
    @Positive(message = "Le montant du paiement doit être strictement positif")
    private BigDecimal montant;

    @NotNull(message = "La date du paiement est obligatoire")
    private LocalDate datePaiement;

    @NotNull(message = "Le mode de paiement est obligatoire (CARTE, ESPECES ou VIREMENT)")
    private ModePaiement modePaiement;
}
