package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.web.dto.AgenceRequestDTO;
import tn.esprit.autoloc.web.dto.AgenceResponseDTO;

public interface IAgenceService {

    AgenceResponseDTO create(AgenceRequestDTO dto);

    AgenceResponseDTO findById(Long id);

    List<AgenceResponseDTO> findAll();

    AgenceResponseDTO update(Long id, AgenceRequestDTO dto);

    void deleteById(Long id);
}
