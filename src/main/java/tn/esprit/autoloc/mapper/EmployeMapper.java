package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.web.dto.EmployeRequestDTO;
import tn.esprit.autoloc.web.dto.EmployeResponseDTO;

@Component
public class EmployeMapper {

    public EmployeResponseDTO toDto(Employe employe) {
        EmployeResponseDTO dto = new EmployeResponseDTO();
        dto.setIdEmploye(employe.getIdEmploye());
        dto.setNom(employe.getNom());
        dto.setPrenom(employe.getPrenom());
        dto.setRole(employe.getRole());
        if (employe.getAgence() != null) {
            dto.setIdAgence(employe.getAgence().getIdAgence());
            dto.setNomAgence(employe.getAgence().getNom());
        }
        return dto;
    }

    // l'agence est rattachée par le service, à partir de idAgence
    public Employe toEntity(EmployeRequestDTO dto) {
        Employe employe = new Employe();
        updateEntity(employe, dto);
        return employe;
    }

    public void updateEntity(Employe employe, EmployeRequestDTO dto) {
        employe.setNom(dto.getNom());
        employe.setPrenom(dto.getPrenom());
        employe.setRole(dto.getRole());
    }
}
