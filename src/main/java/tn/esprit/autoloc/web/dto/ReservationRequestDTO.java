package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import tn.esprit.autoloc.domain.StatutReservation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequestDTO {

    @NotNull(message = "La date de début est obligatoire")
    @FutureOrPresent(message = "La date de début doit être aujourd'hui ou dans le futur")
    private LocalDate dateDebut;

    @NotNull(message = "La date de fin est obligatoire")
    private LocalDate dateFin;

    // facultatif : EN_ATTENTE si absent à la création
    private StatutReservation statut;

    @NotNull(message = "Le client est obligatoire")
    private Long idClient;

    @NotNull(message = "Le véhicule est obligatoire")
    private Long idVehicule;
}
