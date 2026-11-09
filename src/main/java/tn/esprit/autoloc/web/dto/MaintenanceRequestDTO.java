package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceRequestDTO {

    @NotNull(message = "La date de début est obligatoire")
    private LocalDate dateDebut;

    // facultatif : maintenance encore en cours
    private LocalDate dateFin;

    @Size(max = 500, message = "La description ne doit pas dépasser 500 caractères")
    private String description;

    @NotNull(message = "Le véhicule est obligatoire")
    private Long idVehicule;
}
