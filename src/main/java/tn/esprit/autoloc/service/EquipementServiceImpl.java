package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.EquipementMapper;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.web.dto.EquipementRequestDTO;
import tn.esprit.autoloc.web.dto.EquipementResponseDTO;

@Service
@RequiredArgsConstructor
public class EquipementServiceImpl implements IEquipementService {

    private final IEquipementRepository equipementRepository;
    private final EquipementMapper equipementMapper;

    @Override
    public EquipementResponseDTO create(EquipementRequestDTO dto) {
        Equipement equipement = equipementMapper.toEntity(dto);
        return equipementMapper.toDto(equipementRepository.save(equipement));
    }

    @Override
    public EquipementResponseDTO findById(Long id) {
        return equipementMapper.toDto(getEquipement(id));
    }

    @Override
    public List<EquipementResponseDTO> findAll() {
        return equipementRepository.findAll().stream().map(equipementMapper::toDto).toList();
    }

    @Override
    public EquipementResponseDTO update(Long id, EquipementRequestDTO dto) {
        Equipement existant = getEquipement(id);
        equipementMapper.updateEntity(existant, dto);
        return equipementMapper.toDto(equipementRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        if (!equipementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Equipement", id);
        }
        equipementRepository.deleteById(id);
    }

    private Equipement getEquipement(Long id) {
        return equipementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", id));
    }
}
