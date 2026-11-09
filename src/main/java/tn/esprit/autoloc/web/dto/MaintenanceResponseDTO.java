package tn.esprit.autoloc.web.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceResponseDTO {

    private Long idMaintenance;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    private String description;

    private Long idVehicule;

    private String immatriculation;
}
