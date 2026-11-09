package tn.esprit.autoloc.web.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// numPermis n'est pas renvoyé : donnée sensible, utile seulement en interne
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponseDTO {

    private Long idClient;

    private String nom;

    private String prenom;

    private String email;

    private String telephone;

    private LocalDate dateInscription;
}
