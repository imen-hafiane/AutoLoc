package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.web.dto.VehiculeRequestDTO;
import tn.esprit.autoloc.web.dto.VehiculeResponseDTO;

public interface IVehiculeService {

    VehiculeResponseDTO create(VehiculeRequestDTO dto);

    VehiculeResponseDTO findById(Long id);

    List<VehiculeResponseDTO> findAll();

    VehiculeResponseDTO update(Long id, VehiculeRequestDTO dto);

    void deleteById(Long id);
}
