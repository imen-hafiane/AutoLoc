package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.web.dto.EquipementRequestDTO;
import tn.esprit.autoloc.web.dto.EquipementResponseDTO;

@Component
public class EquipementMapper {

    public EquipementResponseDTO toDto(Equipement equipement) {
        EquipementResponseDTO dto = new EquipementResponseDTO();
        dto.setIdEquipement(equipement.getIdEquipement());
        dto.setLibelle(equipement.getLibelle());
        return dto;
    }

    public Equipement toEntity(EquipementRequestDTO dto) {
        Equipement equipement = new Equipement();
        updateEntity(equipement, dto);
        return equipement;
    }

    public void updateEntity(Equipement equipement, EquipementRequestDTO dto) {
        equipement.setLibelle(dto.getLibelle());
    }
}
