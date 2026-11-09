package tn.esprit.autoloc.mapper;

import java.util.List;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.web.dto.VehiculeRequestDTO;
import tn.esprit.autoloc.web.dto.VehiculeResponseDTO;

@Component
public class VehiculeMapper {

    public VehiculeResponseDTO toDto(Vehicule vehicule) {
        VehiculeResponseDTO dto = new VehiculeResponseDTO();
        dto.setIdVehicule(vehicule.getIdVehicule());
        dto.setImmatriculation(vehicule.getImmatriculation());
        dto.setMarque(vehicule.getMarque());
        dto.setModele(vehicule.getModele());
        dto.setCategorie(vehicule.getCategorie());
        dto.setTarifJournalier(vehicule.getTarifJournalier());
        dto.setStatut(vehicule.getStatut());
        if (vehicule.getAgence() != null) {
            dto.setIdAgence(vehicule.getAgence().getIdAgence());
            dto.setNomAgence(vehicule.getAgence().getNom());
        }
        // on renvoie seulement les libellés des équipements
        dto.setEquipements(vehicule.getEquipements() == null ? List.of()
                : vehicule.getEquipements().stream().map(Equipement::getLibelle).toList());
        return dto;
    }

    // l'agence et les équipements sont rattachés par le service, à partir de leurs identifiants
    public Vehicule toEntity(VehiculeRequestDTO dto) {
        Vehicule vehicule = new Vehicule();
        updateEntity(vehicule, dto);
        return vehicule;
    }

    public void updateEntity(Vehicule vehicule, VehiculeRequestDTO dto) {
        vehicule.setImmatriculation(dto.getImmatriculation());
        vehicule.setMarque(dto.getMarque());
        vehicule.setModele(dto.getModele());
        vehicule.setCategorie(dto.getCategorie());
        vehicule.setTarifJournalier(dto.getTarifJournalier());
        vehicule.setStatut(dto.getStatut());
    }
}
