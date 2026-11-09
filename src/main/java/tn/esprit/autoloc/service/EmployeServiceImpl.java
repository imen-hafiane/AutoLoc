package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.EmployeMapper;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IEmployeRepository;
import tn.esprit.autoloc.web.dto.EmployeRequestDTO;
import tn.esprit.autoloc.web.dto.EmployeResponseDTO;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {

    private final IEmployeRepository employeRepository;
    private final IAgenceRepository agenceRepository;
    private final EmployeMapper employeMapper;

    @Override
    public EmployeResponseDTO create(EmployeRequestDTO dto) {
        Employe employe = employeMapper.toEntity(dto);
        employe.setAgence(getAgence(dto.getIdAgence()));
        return employeMapper.toDto(employeRepository.save(employe));
    }

    @Override
    public EmployeResponseDTO findById(Long id) {
        return employeMapper.toDto(getEmploye(id));
    }

    @Override
    public List<EmployeResponseDTO> findAll() {
        return employeRepository.findAll().stream().map(employeMapper::toDto).toList();
    }

    @Override
    public EmployeResponseDTO update(Long id, EmployeRequestDTO dto) {
        Employe existant = getEmploye(id);
        employeMapper.updateEntity(existant, dto);
        existant.setAgence(getAgence(dto.getIdAgence()));
        return employeMapper.toDto(employeRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        if (!employeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employe", id);
        }
        employeRepository.deleteById(id);
    }

    private Employe getEmploye(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employe", id));
    }

    private Agence getAgence(Long idAgence) {
        return agenceRepository.findById(idAgence)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", idAgence));
    }
}
