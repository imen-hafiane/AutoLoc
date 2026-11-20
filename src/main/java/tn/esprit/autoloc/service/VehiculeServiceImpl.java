package tn.esprit.autoloc.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.VehiculeMapper;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.repository.IMaintenanceRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.web.dto.VehiculeDTO;
import tn.esprit.autoloc.web.dto.VehiculeRequestDTO;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;
    private final IAgenceRepository agenceRepository;
    private final IEquipementRepository equipementRepository;
    private final IMaintenanceRepository maintenanceRepository;
    private final VehiculeMapper vehiculeMapper;

    @Override
    public VehiculeDTO create(VehiculeRequestDTO dto) {
        Vehicule vehicule = vehiculeMapper.toEntity(dto);
        rattacher(vehicule, dto);
        return vehiculeMapper.toDto(vehiculeRepository.save(vehicule));
    }

    @Override
    public VehiculeDTO findById(Long id) {
        return vehiculeMapper.toDto(getVehicule(id));
    }

    @Override
    public List<VehiculeDTO> findAll() {
        return vehiculeRepository.findAll().stream().map(vehiculeMapper::toDto).toList();
    }

    @Override
    public VehiculeDTO update(Long id, VehiculeRequestDTO dto) {
        Vehicule existant = getVehicule(id);
        vehiculeMapper.updateEntity(existant, dto);
        rattacher(existant, dto);
        return vehiculeMapper.toDto(vehiculeRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }
        vehiculeRepository.deleteById(id);
    }

    // ================= Atelier 7 : affectations =================

    // ManyToMany Vehicule <-> Equipement : Vehicule est le côté propriétaire (@JoinTable vehicule_equipements)
    @Override
    @Transactional
    public VehiculeDTO affecterVehiculeAEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = getVehicule(idVehicule);
        Equipement equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", idEquipement));

        // éviter une collection nulle si le véhicule n'a encore aucun équipement
        Set<Equipement> equipements = new HashSet<>();
        if (vehicule.getEquipements() != null) {
            equipements = vehicule.getEquipements();
        }
        equipements.add(equipement);
        vehicule.setEquipements(equipements);
        // sauvegarde du côté propriétaire
        return vehiculeMapper.toDto(vehiculeRepository.save(vehicule));
    }

    // ManyToOne unidirectionnelle Maintenance -> Vehicule : on affecte depuis Maintenance (seul côté qui connaît l'autre)
    @Override
    @Transactional
    public VehiculeDTO affecterMaintenanceAvehicule(String description, String immatriculation) {
        Vehicule vehicule = vehiculeRepository.findByImmatriculation(immatriculation)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Aucun véhicule avec l'immatriculation " + immatriculation));

        Maintenance maintenance = new Maintenance();
        maintenance.setDescription(description);
        maintenance.setDateDebut(LocalDate.now());
        maintenance.setVehicule(vehicule);
        maintenanceRepository.save(maintenance);

        // règle métier : un véhicule en maintenance n'est plus disponible à la location
        vehicule.setStatut(StatutVehicule.MAINTENANCE);
        return vehiculeMapper.toDto(vehiculeRepository.save(vehicule));
    }

    // ================= méthodes utilitaires =================

    private Vehicule getVehicule(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    // remplace les identifiants reçus par les vraies entités chargées depuis la base
    private void rattacher(Vehicule vehicule, VehiculeRequestDTO dto) {
        Long idAgence = dto.getIdAgence();
        Agence agence = agenceRepository.findById(idAgence)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", idAgence));
        vehicule.setAgence(agence);

        Set<Equipement> equipements = new HashSet<>();
        if (dto.getIdEquipements() != null) {
            for (Long idEquipement : dto.getIdEquipements()) {
                equipements.add(equipementRepository.findById(idEquipement)
                        .orElseThrow(() -> new ResourceNotFoundException("Equipement", idEquipement)));
            }
        }
        vehicule.setEquipements(equipements);
    }
}
