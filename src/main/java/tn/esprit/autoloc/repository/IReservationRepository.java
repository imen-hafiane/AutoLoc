package tn.esprit.autoloc.repository;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.domain.Reservation;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {

    // première réservation aux dates données qui n'a pas encore de contrat
    Optional<Reservation> findFirstByDateDebutAndDateFinAndContratIsNull(LocalDate dateDebut, LocalDate dateFin);
}
