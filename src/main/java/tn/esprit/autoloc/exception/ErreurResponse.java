package tn.esprit.autoloc.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

// Corps JSON renvoyé pour toutes les erreurs de l'API
@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErreurResponse {

    private int statut;
    private String message;
    private LocalDateTime horodatage;
    private Map<String, String> erreurs; // détail champ -> message (validation uniquement)
}
