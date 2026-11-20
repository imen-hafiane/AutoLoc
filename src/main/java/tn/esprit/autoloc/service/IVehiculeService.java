package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.web.dto.VehiculeDTO;
import tn.esprit.autoloc.web.dto.VehiculeRequestDTO;

public interface IVehiculeService {

    VehiculeDTO create(VehiculeRequestDTO dto);

    VehiculeDTO findById(Long id);

    List<VehiculeDTO> findAll();

    VehiculeDTO update(Long id, VehiculeRequestDTO dto);

    void deleteById(Long id);

    // ---- Atelier 7 : affectations
    VehiculeDTO affecterVehiculeAEquipement(Long idVehicule, Long idEquipement);

    VehiculeDTO affecterMaintenanceAvehicule(String description, String immatriculation);
}
