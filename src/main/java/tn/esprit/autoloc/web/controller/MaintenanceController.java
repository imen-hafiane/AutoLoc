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
import tn.esprit.autoloc.service.IMaintenanceService;
import tn.esprit.autoloc.web.dto.MaintenanceRequestDTO;
import tn.esprit.autoloc.web.dto.MaintenanceResponseDTO;

@Tag(name = "Maintenances", description = "Gestion des maintenances")
@RestController
@RequestMapping("/maintenances")
@RequiredArgsConstructor
public class MaintenanceController {

    private final IMaintenanceService maintenanceService;

    @Operation(summary = "Lister les maintenances")
    @ApiResponse(responseCode = "200", description = "Liste renvoyée")
    @GetMapping
    public List<MaintenanceResponseDTO> findAll() {
        return maintenanceService.findAll();
    }

    @Operation(summary = "Consulter une maintenance")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Maintenance trouvée"),
            @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
    })
    @GetMapping("/{id}")
    public MaintenanceResponseDTO findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return maintenanceService.findById(id);
    }

    @Operation(summary = "Créer une maintenance")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Maintenance créée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MaintenanceResponseDTO create(@Valid @RequestBody MaintenanceRequestDTO dto) {
        return maintenanceService.create(dto);
    }

    @Operation(summary = "Modifier une maintenance")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Maintenance modifiée"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
    })
    @PutMapping("/{id}")
    public MaintenanceResponseDTO update(@Parameter(description = "Identifiant") @PathVariable Long id,
                                 @Valid @RequestBody MaintenanceRequestDTO dto) {
        return maintenanceService.update(id, dto);
    }

    @Operation(summary = "Supprimer une maintenance")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Maintenance supprimée"),
            @ApiResponse(responseCode = "404", description = "Maintenance introuvable"),
            @ApiResponse(responseCode = "409", description = "Maintenance encore liée à d'autres données")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        maintenanceService.deleteById(id);
    }
}
