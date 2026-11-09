package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.web.dto.ReservationRequestDTO;
import tn.esprit.autoloc.web.dto.ReservationResponseDTO;

public interface IReservationService {

    ReservationResponseDTO create(ReservationRequestDTO dto);

    ReservationResponseDTO findById(Long id);

    List<ReservationResponseDTO> findAll();

    ReservationResponseDTO update(Long id, ReservationRequestDTO dto);

    void deleteById(Long id);
}
