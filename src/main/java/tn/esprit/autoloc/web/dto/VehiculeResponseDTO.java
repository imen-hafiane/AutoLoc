package tn.esprit.autoloc.web.dto;

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
public class VehiculeResponseDTO {

    private Long idVehicule;

    private String immatriculation;

    private String marque;

    private String modele;

    private CategorieVehicule categorie;

    private BigDecimal tarifJournalier;

    private StatutVehicule statut;

    private Long idAgence;

    private String nomAgence;

    private List<String> equipements;
}
