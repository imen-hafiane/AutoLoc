package tn.esprit.autoloc.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

// Gestion centralisée des erreurs pour tous les contrôleurs
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 400 : un champ du DTO ne respecte pas ses annotations (@NotBlank, @Email...)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErreurResponse> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> erreurs = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err -> erreurs.putIfAbsent(err.getField(), err.getDefaultMessage()));
        return reponse(HttpStatus.BAD_REQUEST, "Données invalides", erreurs);
    }

    // 404 : ressource introuvable (levée par les services)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErreurResponse> handleNotFound(ResourceNotFoundException ex) {
        return reponse(HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }

    // 400 : règle métier non respectée (ex. date de fin avant la date de début)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErreurResponse> handleIllegalArgument(IllegalArgumentException ex) {
        return reponse(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
    }

    // 400 : JSON mal formé, date hors format AAAA-MM-JJ, valeur d'énumération inconnue
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErreurResponse> handleJsonIllisible(HttpMessageNotReadableException ex) {
        return reponse(HttpStatus.BAD_REQUEST,
                "Corps JSON invalide : vérifiez la syntaxe, les dates (AAAA-MM-JJ) et les valeurs d'énumération", null);
    }

    // 400 : identifiant non numérique dans l'URL (ex. /contrats/abc)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErreurResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return reponse(HttpStatus.BAD_REQUEST,
                "Valeur invalide pour le paramètre '" + ex.getName() + "' : " + ex.getValue(), null);
    }

    // 405 : verbe HTTP non prévu (ex. POST /paiements)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErreurResponse> handleMethodeNonSupportee(HttpRequestMethodNotSupportedException ex) {
        return reponse(HttpStatus.METHOD_NOT_ALLOWED,
                "Méthode " + ex.getMethod() + " non autorisée sur cette ressource", null);
    }

    // 409 : valeur unique déjà utilisée, ou suppression d'une donnée encore référencée
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErreurResponse> handleConflit(DataIntegrityViolationException ex) {
        return reponse(HttpStatus.CONFLICT,
                "Conflit avec les données existantes : valeur déjà utilisée ou ressource encore liée", null);
    }

    private ResponseEntity<ErreurResponse> reponse(HttpStatus statut, String message, Map<String, String> erreurs) {
        return ResponseEntity.status(statut)
                .body(new ErreurResponse(statut.value(), message, LocalDateTime.now(), erreurs));
    }
}
