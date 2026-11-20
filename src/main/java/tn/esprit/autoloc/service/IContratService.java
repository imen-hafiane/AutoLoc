package tn.esprit.autoloc.service;

import java.time.LocalDate;
import java.util.List;
import tn.esprit.autoloc.web.dto.ContratDTO;
import tn.esprit.autoloc.web.dto.ContratRequestDTO;

public interface IContratService {

    ContratDTO create(ContratRequestDTO dto);

    ContratDTO findById(Long id);

    List<ContratDTO> findAll();

    ContratDTO update(Long id, ContratRequestDTO dto);

    void deleteById(Long id);

    // ---- Atelier 7 : affectation
    ContratDTO affecterContratAReservation(LocalDate dateSignature, LocalDate dateDebut, LocalDate dateFin);
}
