package tn.esprit.autoloc.service;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.ContratMapper;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.web.dto.ContratDTO;
import tn.esprit.autoloc.web.dto.ContratRequestDTO;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;
    private final IReservationRepository reservationRepository;
    private final ContratMapper contratMapper;

    @Override
    public ContratDTO create(ContratRequestDTO dto) {
        Contrat contrat = contratMapper.toEntity(dto);
        return contratMapper.toDto(contratRepository.save(contrat));
    }

    @Override
    public ContratDTO findById(Long id) {
        return contratMapper.toDto(getContrat(id));
    }

    @Override
    public List<ContratDTO> findAll() {
        return contratRepository.findAll().stream().map(contratMapper::toDto).toList();
    }

    @Override
    public ContratDTO update(Long id, ContratRequestDTO dto) {
        Contrat existant = getContrat(id);
        contratMapper.updateEntity(existant, dto);
        return contratMapper.toDto(contratRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        if (!contratRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contrat", id);
        }
        contratRepository.deleteById(id);
    }

    // ================= Atelier 7 : affectation =================

    // OneToOne Reservation <-> Contrat : Reservation est le côté propriétaire (clé contrat_id_contrat),
    // Contrat est le côté inverse (mappedBy) => l'affectation se fait sur la réservation
    @Override
    @Transactional
    public ContratDTO affecterContratAReservation(LocalDate dateSignature, LocalDate dateDebut, LocalDate dateFin) {
        // un contrat signé à cette date et pas encore rattaché à une réservation
        Contrat contrat = contratRepository.findByDateSignature(dateSignature).stream()
                .filter(c -> c.getReservation() == null)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Aucun contrat libre signé le " + dateSignature));

        // une réservation à ces dates et sans contrat
        Reservation reservation = reservationRepository
                .findFirstByDateDebutAndDateFinAndContratIsNull(dateDebut, dateFin)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Aucune réservation sans contrat du " + dateDebut + " au " + dateFin));

        // affectation sur le côté propriétaire, puis sauvegarde du propriétaire
        reservation.setContrat(contrat);
        reservationRepository.save(reservation);
        // on garde aussi le côté inverse cohérent en mémoire
        contrat.setReservation(reservation);
        return contratMapper.toDto(contrat);
    }

    private Contrat getContrat(Long id) {
        return contratRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
    }
}
