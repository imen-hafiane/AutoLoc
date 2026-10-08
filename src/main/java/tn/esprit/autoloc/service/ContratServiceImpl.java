package tn.esprit.autoloc.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.repository.IContratRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;

    @Override
    public Contrat addContrat(Contrat contrat) {
        lierPaiements(contrat);
        return contratRepository.save(contrat);
    }

    @Override
    public List<Contrat> retrieveAllContrats() {
        return contratRepository.findAll();
    }

    @Override
    public Contrat retrieveContrat(Long idContrat) {
        return contratRepository.findById(idContrat)
                .orElseThrow(() -> new EntityNotFoundException("Contrat introuvable : " + idContrat));
    }

    @Override
    public Contrat updateContrat(Contrat contrat) {
        if (contrat.getIdContrat() == null || !contratRepository.existsById(contrat.getIdContrat())) {
            throw new EntityNotFoundException("Contrat introuvable : " + contrat.getIdContrat());
        }
        lierPaiements(contrat);
        return contratRepository.save(contrat);
    }

    @Override
    public void removeContrat(Long idContrat) {
        // cascade = ALL : les paiements du contrat sont supprimés avec lui
        contratRepository.deleteById(idContrat);
    }

    // Côté propriétaire (Paiement) : sans ce lien, la clé étrangère contrat_id_contrat resterait NULL
    private void lierPaiements(Contrat contrat) {
        if (contrat.getPaiements() != null) {
            contrat.getPaiements().forEach(paiement -> paiement.setContrat(contrat));
        }
    }
}
