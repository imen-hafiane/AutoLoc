package tn.esprit.autoloc.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String ressource, Long id) {
        super(ressource + " introuvable avec l'identifiant " + id);
    }

    // pour les recherches par autre chose qu'un id (nom, immatriculation, dates...)
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
