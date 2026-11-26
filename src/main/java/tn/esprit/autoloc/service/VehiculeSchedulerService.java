package tn.esprit.autoloc.service;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.StatutReservation;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.repository.IReservationRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehiculeSchedulerService {

    private final IReservationRepository reservationRepository;

    // tous les jours à 1h00
    @Scheduled(cron = "0 0 1 * * *")
    @Transactional
    public void terminerLocations() {
        List<Reservation> terminees = reservationRepository
                .findByDateFinBeforeAndStatut(LocalDate.now(), StatutReservation.CONFIRMEE);

        for (Reservation r : terminees) {
            r.setStatut(StatutReservation.TERMINEE);              // ferme la location
            r.getVehicule().setStatut(StatutVehicule.DISPONIBLE); // libère le véhicule
            log.info("Réservation {} terminée, véhicule {} disponible",
                    r.getIdReservation(), r.getVehicule().getImmatriculation());
        }
    }
}