package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehiculeRequestDTO {

    @NotBlank(message = "L'immatriculation est obligatoire")
    @Size(max = 20, message = "L'immatriculation ne doit pas dépasser 20 caractères")
    private String immatriculation;

    @NotBlank(message = "La marque est obligatoire")
    private String marque;

    @NotBlank(message = "Le modèle est obligatoire")
    private String modele;

    @NotNull(message = "La catégorie est obligatoire")
    private CategorieVehicule categorie;

    @NotNull(message = "Le tarif journalier est obligatoire")
    @PositiveOrZero(message = "Le tarif journalier doit être positif ou nul")
    private BigDecimal tarifJournalier;

    @NotNull(message = "Le statut est obligatoire")
    private StatutVehicule statut;

    @NotNull(message = "L'agence du véhicule est obligatoire")
    private Long idAgence;

    // facultatif : identifiants des équipements du véhicule
    private List<Long> idEquipements;
}
