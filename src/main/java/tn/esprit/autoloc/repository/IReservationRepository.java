package tn.esprit.autoloc.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.StatutReservation;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {
    // réservations avec contrat qui finissent entre deux dates (échéance)
    List<Reservation> findByContratIsNotNullAndDateFinBetween(LocalDate debut, LocalDate fin);

    // locations terminées : date de fin passée et réservation encore confirmée
    List<Reservation> findByDateFinBeforeAndStatut(LocalDate date, StatutReservation statut);

    // première réservation aux dates données qui n'a pas encore de contrat
    Optional<Reservation> findFirstByDateDebutAndDateFinAndContratIsNull(LocalDate dateDebut, LocalDate dateFin);
}
