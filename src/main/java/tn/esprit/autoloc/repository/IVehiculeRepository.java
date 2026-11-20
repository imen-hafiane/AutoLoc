package tn.esprit.autoloc.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.domain.Vehicule;

public interface IVehiculeRepository extends JpaRepository<Vehicule, Long> {

    // l'immatriculation est unique : au plus un résultat
    Optional<Vehicule> findByImmatriculation(String immatriculation);
}
