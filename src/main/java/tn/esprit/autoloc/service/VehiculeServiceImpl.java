package tn.esprit.autoloc.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.VehiculeMapper;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.web.dto.VehiculeRequestDTO;
import tn.esprit.autoloc.web.dto.VehiculeResponseDTO;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;
    private final IAgenceRepository agenceRepository;
    private final IEquipementRepository equipementRepository;
    private final VehiculeMapper vehiculeMapper;

    @Override
    public VehiculeResponseDTO create(VehiculeRequestDTO dto) {
        Vehicule vehicule = vehiculeMapper.toEntity(dto);
        rattacher(vehicule, dto);
        return vehiculeMapper.toDto(vehiculeRepository.save(vehicule));
    }

    @Override
    public VehiculeResponseDTO findById(Long id) {
        return vehiculeMapper.toDto(getVehicule(id));
    }

    @Override
    public List<VehiculeResponseDTO> findAll() {
        return vehiculeRepository.findAll().stream().map(vehiculeMapper::toDto).toList();
    }

    @Override
    public VehiculeResponseDTO update(Long id, VehiculeRequestDTO dto) {
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
