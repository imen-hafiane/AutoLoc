package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.ContratMapper;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.web.dto.ContratRequestDTO;
import tn.esprit.autoloc.web.dto.ContratResponseDTO;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;
    private final ContratMapper contratMapper;

    @Override
    public ContratResponseDTO create(ContratRequestDTO dto) {
        Contrat contrat = contratMapper.toEntity(dto);
        return contratMapper.toDto(contratRepository.save(contrat));
    }

    @Override
    public ContratResponseDTO findById(Long id) {
        return contratMapper.toDto(getContrat(id));
    }

    @Override
    public List<ContratResponseDTO> findAll() {
        return contratRepository.findAll().stream().map(contratMapper::toDto).toList();
    }

    @Override
    public ContratResponseDTO update(Long id, ContratRequestDTO dto) {
        Contrat existant = getContrat(id);
        contratMapper.updateEntity(existant, dto);
        return contratMapper.toDto(contratRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        if (!contratRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contrat", id);
        }
        contratRepository.deleteById(id);
    }

    private Contrat getContrat(Long id) {
        return contratRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
    }
}
