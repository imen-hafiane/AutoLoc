package tn.esprit.autoloc.web.dto;

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
public class ReservationResponseDTO {

    private Long idReservation;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    private StatutReservation statut;

    private Long idClient;

    private String nomClient;

    private Long idVehicule;

    private String immatriculation;

    private Long idContrat;
}
