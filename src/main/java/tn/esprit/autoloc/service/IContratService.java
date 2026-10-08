package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Contrat;

import java.util.List;

public interface IContratService {

    Contrat addContrat(Contrat contrat);

    List<Contrat> retrieveAllContrats();

    Contrat retrieveContrat(Long idContrat);

    Contrat updateContrat(Contrat contrat);

    void removeContrat(Long idContrat);
}
