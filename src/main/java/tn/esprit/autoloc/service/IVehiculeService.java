package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Vehicule;

import java.util.List;

public interface IVehiculeService {

    Vehicule addVehicule(Vehicule vehicule);

    List<Vehicule> retrieveAllVehicules();

    Vehicule retrieveVehicule(Long idVehicule);

    Vehicule updateVehicule(Vehicule vehicule);

    void removeVehicule(Long idVehicule);
}
