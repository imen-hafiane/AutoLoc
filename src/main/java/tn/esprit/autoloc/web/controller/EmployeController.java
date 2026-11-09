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
import tn.esprit.autoloc.service.IEmployeService;
import tn.esprit.autoloc.web.dto.EmployeRequestDTO;
import tn.esprit.autoloc.web.dto.EmployeResponseDTO;

@Tag(name = "Employés", description = "Gestion des employés")
@RestController
@RequestMapping("/employes")
@RequiredArgsConstructor
public class EmployeController {

    private final IEmployeService employeService;

    @Operation(summary = "Lister les employés")
    @ApiResponse(responseCode = "200", description = "Liste renvoyée")
    @GetMapping
    public List<EmployeResponseDTO> findAll() {
        return employeService.findAll();
    }

    @Operation(summary = "Consulter un employé")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employé trouvé"),
            @ApiResponse(responseCode = "404", description = "Employé introuvable")
    })
    @GetMapping("/{id}")
    public EmployeResponseDTO findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return employeService.findById(id);
    }

    @Operation(summary = "Créer un employé")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Employé créé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeResponseDTO create(@Valid @RequestBody EmployeRequestDTO dto) {
        return employeService.create(dto);
    }

    @Operation(summary = "Modifier un employé")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employé modifié"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Employé introuvable")
    })
    @PutMapping("/{id}")
    public EmployeResponseDTO update(@Parameter(description = "Identifiant") @PathVariable Long id,
                                 @Valid @RequestBody EmployeRequestDTO dto) {
        return employeService.update(id, dto);
    }

    @Operation(summary = "Supprimer un employé")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Employé supprimé"),
            @ApiResponse(responseCode = "404", description = "Employé introuvable"),
            @ApiResponse(responseCode = "409", description = "Employé encore lié à d'autres données")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        employeService.deleteById(id);
    }
}
