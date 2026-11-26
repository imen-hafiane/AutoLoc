package tn.esprit.autoloc.service;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.repository.IReservationRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContratSchedulerService {

    private final IReservationRepository reservationRepository;

    // du lundi au vendredi à 7h00
    @Scheduled(cron = "0 0 7 * * MON-FRI")
    public void contratsArrivantAEcheance() {
        LocalDate aujourdhui = LocalDate.now();
        List<Reservation> reservations = reservationRepository
                .findByContratIsNotNullAndDateFinBetween(aujourdhui, aujourdhui.plusDays(2));

        reservations.forEach(r -> log.info("Contrat {} arrive à échéance le {} (client : {} {})",
                r.getContrat().getIdContrat(), r.getDateFin(),
                r.getClient().getPrenom(), r.getClient().getNom()));
    }
}