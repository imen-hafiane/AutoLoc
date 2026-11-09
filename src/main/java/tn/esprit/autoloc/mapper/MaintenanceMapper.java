package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.web.dto.MaintenanceRequestDTO;
import tn.esprit.autoloc.web.dto.MaintenanceResponseDTO;

@Component
public class MaintenanceMapper {

    public MaintenanceResponseDTO toDto(Maintenance maintenance) {
        MaintenanceResponseDTO dto = new MaintenanceResponseDTO();
        dto.setIdMaintenance(maintenance.getIdMaintenance());
        dto.setDateDebut(maintenance.getDateDebut());
        dto.setDateFin(maintenance.getDateFin());
        dto.setDescription(maintenance.getDescription());
        if (maintenance.getVehicule() != null) {
            dto.setIdVehicule(maintenance.getVehicule().getIdVehicule());
            dto.setImmatriculation(maintenance.getVehicule().getImmatriculation());
        }
        return dto;
    }

    // le véhicule est rattaché par le service, à partir de idVehicule
    public Maintenance toEntity(MaintenanceRequestDTO dto) {
        Maintenance maintenance = new Maintenance();
        updateEntity(maintenance, dto);
        return maintenance;
    }

    public void updateEntity(Maintenance maintenance, MaintenanceRequestDTO dto) {
        maintenance.setDateDebut(dto.getDateDebut());
        maintenance.setDateFin(dto.getDateFin());
        maintenance.setDescription(dto.getDescription());
    }
}
