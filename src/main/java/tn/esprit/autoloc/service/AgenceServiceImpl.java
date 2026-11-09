package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.AgenceMapper;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.web.dto.AgenceRequestDTO;
import tn.esprit.autoloc.web.dto.AgenceResponseDTO;

@Service
@RequiredArgsConstructor
public class AgenceServiceImpl implements IAgenceService {

    private final IAgenceRepository agenceRepository;
    private final AgenceMapper agenceMapper;

    @Override
    public AgenceResponseDTO create(AgenceRequestDTO dto) {
        Agence agence = agenceMapper.toEntity(dto);
        return agenceMapper.toDto(agenceRepository.save(agence));
    }

    @Override
    public AgenceResponseDTO findById(Long id) {
        return agenceMapper.toDto(getAgence(id));
    }

    @Override
    public List<AgenceResponseDTO> findAll() {
        return agenceRepository.findAll().stream().map(agenceMapper::toDto).toList();
    }

    @Override
    public AgenceResponseDTO update(Long id, AgenceRequestDTO dto) {
        Agence existant = getAgence(id);
        agenceMapper.updateEntity(existant, dto);
        return agenceMapper.toDto(agenceRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        if (!agenceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Agence", id);
        }
        agenceRepository.deleteById(id);
    }

    private Agence getAgence(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", id));
    }
}
