package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.web.dto.ContratRequestDTO;
import tn.esprit.autoloc.web.dto.ContratResponseDTO;

public interface IContratService {

    ContratResponseDTO create(ContratRequestDTO dto);

    ContratResponseDTO findById(Long id);

    List<ContratResponseDTO> findAll();

    ContratResponseDTO update(Long id, ContratRequestDTO dto);

    void deleteById(Long id);
}
