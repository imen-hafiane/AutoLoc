package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.StatutReservation;
import tn.esprit.autoloc.web.dto.ReservationRequestDTO;
import tn.esprit.autoloc.web.dto.ReservationResponseDTO;

@Component
public class ReservationMapper {

    public ReservationResponseDTO toDto(Reservation reservation) {
        ReservationResponseDTO dto = new ReservationResponseDTO();
        dto.setIdReservation(reservation.getIdReservation());
        dto.setDateDebut(reservation.getDateDebut());
        dto.setDateFin(reservation.getDateFin());
        dto.setStatut(reservation.getStatut());
        if (reservation.getClient() != null) {
            dto.setIdClient(reservation.getClient().getIdClient());
            dto.setNomClient(reservation.getClient().getPrenom() + " " + reservation.getClient().getNom());
        }
        if (reservation.getVehicule() != null) {
            dto.setIdVehicule(reservation.getVehicule().getIdVehicule());
            dto.setImmatriculation(reservation.getVehicule().getImmatriculation());
        }
        if (reservation.getContrat() != null) {
            dto.setIdContrat(reservation.getContrat().getIdContrat());
        }
        return dto;
    }

    // le client et le véhicule sont rattachés par le service, à partir de leurs identifiants
    public Reservation toEntity(ReservationRequestDTO dto) {
        Reservation reservation = new Reservation();
        updateEntity(reservation, dto);
        if (reservation.getStatut() == null) {
            reservation.setStatut(StatutReservation.EN_ATTENTE);
        }
        return reservation;
    }

    public void updateEntity(Reservation reservation, ReservationRequestDTO dto) {
        reservation.setDateDebut(dto.getDateDebut());
        reservation.setDateFin(dto.getDateFin());
        if (dto.getStatut() != null) {
            reservation.setStatut(dto.getStatut());
        }
    }
}
