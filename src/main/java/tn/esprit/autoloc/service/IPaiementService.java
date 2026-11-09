package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.web.dto.PaiementResponseDTO;

public interface IPaiementService {

    PaiementResponseDTO findById(Long id);

    List<PaiementResponseDTO> findAll();
}
