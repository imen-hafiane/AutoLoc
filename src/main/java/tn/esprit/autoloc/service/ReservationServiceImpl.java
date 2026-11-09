package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.ReservationMapper;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.web.dto.ReservationRequestDTO;
import tn.esprit.autoloc.web.dto.ReservationResponseDTO;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;
    private final IClientRepository clientRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final ReservationMapper reservationMapper;

    @Override
    public ReservationResponseDTO create(ReservationRequestDTO dto) {
        verifierDates(dto);
        Reservation reservation = reservationMapper.toEntity(dto);
        rattacher(reservation, dto);
        return reservationMapper.toDto(reservationRepository.save(reservation));
    }

    @Override
    public ReservationResponseDTO findById(Long id) {
        return reservationMapper.toDto(getReservation(id));
    }

    @Override
    public List<ReservationResponseDTO> findAll() {
        return reservationRepository.findAll().stream().map(reservationMapper::toDto).toList();
    }

    @Override
    public ReservationResponseDTO update(Long id, ReservationRequestDTO dto) {
        verifierDates(dto);
        Reservation existant = getReservation(id);
        reservationMapper.updateEntity(existant, dto);
        rattacher(existant, dto);
        return reservationMapper.toDto(reservationRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation", id);
        }
        reservationRepository.deleteById(id);
    }

    private Reservation getReservation(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
    }

    // règle qui porte sur deux champs : elle reste dans le service
    private void verifierDates(ReservationRequestDTO dto) {
        if (dto.getDateFin().isBefore(dto.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin ne peut pas précéder la date de début");
        }
    }

    private void rattacher(Reservation reservation, ReservationRequestDTO dto) {
        Long idClient = dto.getIdClient();
        Long idVehicule = dto.getIdVehicule();
        reservation.setClient(clientRepository.findById(idClient)
                .orElseThrow(() -> new ResourceNotFoundException("Client", idClient)));
        reservation.setVehicule(vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", idVehicule)));
    }
}
