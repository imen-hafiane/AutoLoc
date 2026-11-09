package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.MaintenanceMapper;
import tn.esprit.autoloc.repository.IMaintenanceRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.web.dto.MaintenanceRequestDTO;
import tn.esprit.autoloc.web.dto.MaintenanceResponseDTO;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final MaintenanceMapper maintenanceMapper;

    @Override
    public MaintenanceResponseDTO create(MaintenanceRequestDTO dto) {
        verifierDates(dto);
        Maintenance maintenance = maintenanceMapper.toEntity(dto);
        rattacher(maintenance, dto);
        return maintenanceMapper.toDto(maintenanceRepository.save(maintenance));
    }

    @Override
    public MaintenanceResponseDTO findById(Long id) {
        return maintenanceMapper.toDto(getMaintenance(id));
    }

    @Override
    public List<MaintenanceResponseDTO> findAll() {
        return maintenanceRepository.findAll().stream().map(maintenanceMapper::toDto).toList();
    }

    @Override
    public MaintenanceResponseDTO update(Long id, MaintenanceRequestDTO dto) {
        verifierDates(dto);
        Maintenance existant = getMaintenance(id);
        maintenanceMapper.updateEntity(existant, dto);
        rattacher(existant, dto);
        return maintenanceMapper.toDto(maintenanceRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        if (!maintenanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Maintenance", id);
        }
        maintenanceRepository.deleteById(id);
    }

    private Maintenance getMaintenance(Long id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));
    }

    private void verifierDates(MaintenanceRequestDTO dto) {
        if (dto.getDateFin() != null && dto.getDateFin().isBefore(dto.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin ne peut pas précéder la date de début");
        }
    }

    // charge le vrai véhicule : évite la re-création du véhicule par cascade PERSIST
    private void rattacher(Maintenance maintenance, MaintenanceRequestDTO dto) {
        Long idVehicule = dto.getIdVehicule();
        maintenance.setVehicule(vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", idVehicule)));
    }
}
