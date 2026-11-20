package tn.esprit.autoloc.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.ReservationMapper;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.web.dto.ReservationRequestDTO;
import tn.esprit.autoloc.web.dto.ReservationResponseDTO;
import tn.esprit.autoloc.web.dto.ReservationWithContratRequestDTO;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;
    private final IClientRepository clientRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final IContratRepository contratRepository;
    private final ReservationMapper reservationMapper;

    @Override
    public ReservationResponseDTO create(ReservationRequestDTO dto) {
        verifierDates(dto.getDateDebut(), dto.getDateFin());
        Reservation reservation = reservationMapper.toEntity(dto);
        reservation.setClient(getClient(dto.getIdClient()));
        reservation.setVehicule(getVehicule(dto.getIdVehicule()));
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
        verifierDates(dto.getDateDebut(), dto.getDateFin());
        Reservation existant = getReservation(id);
        reservationMapper.updateEntity(existant, dto);
        existant.setClient(getClient(dto.getIdClient()));
        existant.setVehicule(getVehicule(dto.getIdVehicule()));
        return reservationMapper.toDto(reservationRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation", id);
        }
        reservationRepository.deleteById(id);
    }

    // ================= Atelier 7 : affectations =================

    // ManyToOne Reservation -> Client : Reservation est le côté propriétaire (clé client_id_client)
    @Override
    @Transactional
    public ReservationResponseDTO affecterReservationAClient(Long idReservation, String nom, String prenom) {
        Reservation reservation = getReservation(idReservation);
        Client client = clientRepository.findFirstByNomAndPrenom(nom, prenom)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Aucun client nommé " + prenom + " " + nom));
        // affectation sur le côté propriétaire
        reservation.setClient(client);
        return reservationMapper.toDto(reservationRepository.save(reservation));
    }

    // OneToOne Reservation -> Contrat : Reservation est le côté propriétaire (clé contrat_id_contrat)
    // @Transactional : si une étape échoue, ni le contrat ni la réservation ne sont enregistrés
    @Override
    @Transactional
    public ReservationResponseDTO creerReservationEtContratAssocie(ReservationWithContratRequestDTO requestDTO) {
        verifierDates(requestDTO.getDateDebut(), requestDTO.getDateFin());
        Client client = getClient(requestDTO.getIdClient());
        Vehicule vehicule = getVehicule(requestDTO.getIdVehicule());

        // 1. le contrat (enfant) : pas de cascade depuis Reservation, on l'enregistre d'abord
        Contrat contrat = new Contrat();
        contrat.setDateSignature(requestDTO.getDateSignature());
        contrat.setValide(requestDTO.isValide());
        contrat.setMontantTotal(calculerMontant(vehicule, requestDTO.getDateDebut(), requestDTO.getDateFin()));
        contrat = contratRepository.save(contrat);

        // 2. la réservation (parent) : on lui affecte le client, le véhicule et le contrat
        Reservation reservation = reservationMapper.toEntity(requestDTO);
        reservation.setClient(client);
        reservation.setVehicule(vehicule);
        reservation.setContrat(contrat);
        return reservationMapper.toDto(reservationRepository.save(reservation));
    }

    // ================= méthodes utilitaires =================

    // montant = tarif journalier x nombre de jours (au moins 1 jour)
    private BigDecimal calculerMontant(Vehicule vehicule, LocalDate dateDebut, LocalDate dateFin) {
        long nbJours = Math.max(1, ChronoUnit.DAYS.between(dateDebut, dateFin));
        return vehicule.getTarifJournalier().multiply(BigDecimal.valueOf(nbJours));
    }

    private void verifierDates(LocalDate dateDebut, LocalDate dateFin) {
        if (dateFin.isBefore(dateDebut)) {
            throw new IllegalArgumentException("La date de fin ne peut pas précéder la date de début");
        }
    }

    private Reservation getReservation(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
    }

    private Client getClient(Long idClient) {
        return clientRepository.findById(idClient)
                .orElseThrow(() -> new ResourceNotFoundException("Client", idClient));
    }

    private Vehicule getVehicule(Long idVehicule) {
        return vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", idVehicule));
    }
}
