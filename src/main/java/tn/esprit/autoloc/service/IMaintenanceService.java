package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.web.dto.MaintenanceRequestDTO;
import tn.esprit.autoloc.web.dto.MaintenanceResponseDTO;

public interface IMaintenanceService {

    MaintenanceResponseDTO create(MaintenanceRequestDTO dto);

    MaintenanceResponseDTO findById(Long id);

    List<MaintenanceResponseDTO> findAll();

    MaintenanceResponseDTO update(Long id, MaintenanceRequestDTO dto);

    void deleteById(Long id);
}
