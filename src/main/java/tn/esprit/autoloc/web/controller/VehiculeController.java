package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.service.IVehiculeService;
import tn.esprit.autoloc.web.dto.VehiculeRequestDTO;
import tn.esprit.autoloc.web.dto.VehiculeResponseDTO;

@Tag(name = "Véhicules", description = "Gestion de la flotte de véhicules")
@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    @Operation(summary = "Lister les véhicules")
    @ApiResponse(responseCode = "200", description = "Liste renvoyée")
    @GetMapping
    public List<VehiculeResponseDTO> findAll() {
        return vehiculeService.findAll();
    }

    @Operation(summary = "Consulter un véhicule")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Véhicule trouvé"),
            @ApiResponse(responseCode = "404", description = "Véhicule introuvable")
    })
    @GetMapping("/{id}")
    public VehiculeResponseDTO findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return vehiculeService.findById(id);
    }

    @Operation(summary = "Créer un véhicule")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Véhicule créé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehiculeResponseDTO create(@Valid @RequestBody VehiculeRequestDTO dto) {
        return vehiculeService.create(dto);
    }

    @Operation(summary = "Modifier un véhicule")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Véhicule modifié"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Véhicule introuvable")
    })
    @PutMapping("/{id}")
    public VehiculeResponseDTO update(@Parameter(description = "Identifiant") @PathVariable Long id,
                                 @Valid @RequestBody VehiculeRequestDTO dto) {
        return vehiculeService.update(id, dto);
    }

    @Operation(summary = "Supprimer un véhicule")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Véhicule supprimé"),
            @ApiResponse(responseCode = "404", description = "Véhicule introuvable"),
            @ApiResponse(responseCode = "409", description = "Véhicule encore lié à d'autres données")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        vehiculeService.deleteById(id);
    }
}
