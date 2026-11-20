package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.autoloc.domain.StatutReservation;

// Une réservation ET son contrat, envoyés en une seule requête
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationWithContratRequestDTO {

    // ---- partie réservation
    @NotNull(message = "La date de début est obligatoire")
    @FutureOrPresent(message = "La date de début doit être aujourd'hui ou dans le futur")
    private LocalDate dateDebut;

    @NotNull(message = "La date de fin est obligatoire")
    private LocalDate dateFin;

    // facultatif : CONFIRMEE par défaut, puisqu'un contrat est signé
    private StatutReservation statut;

    @NotNull(message = "Le client est obligatoire")
    private Long idClient;

    @NotNull(message = "Le véhicule est obligatoire")
    private Long idVehicule;

    // ---- partie contrat (le montant total est calculé par le service)
    @NotNull(message = "La date de signature est obligatoire")
    private LocalDate dateSignature;

    private boolean valide;
}
