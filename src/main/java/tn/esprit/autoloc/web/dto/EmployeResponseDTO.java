package tn.esprit.autoloc.web.dto;

import tn.esprit.autoloc.domain.RoleEmploye;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeResponseDTO {

    private Long idEmploye;

    private String nom;

    private String prenom;

    private RoleEmploye role;

    private Long idAgence;

    private String nomAgence;
}
