package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.PaiementMapper;
import tn.esprit.autoloc.repository.IPaiementRepository;
import tn.esprit.autoloc.web.dto.PaiementResponseDTO;

// Lecture seule : les paiements sont créés et supprimés via le Contrat (composition)
@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements IPaiementService {

    private final IPaiementRepository paiementRepository;
    private final PaiementMapper paiementMapper;

    @Override
    public PaiementResponseDTO findById(Long id) {
        return paiementRepository.findById(id)
                .map(paiementMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement", id));
    }

    @Override
    public List<PaiementResponseDTO> findAll() {
        return paiementRepository.findAll().stream().map(paiementMapper::toDto).toList();
    }
}
