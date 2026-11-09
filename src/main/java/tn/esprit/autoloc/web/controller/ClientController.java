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
import tn.esprit.autoloc.service.IClientService;
import tn.esprit.autoloc.web.dto.ClientRequestDTO;
import tn.esprit.autoloc.web.dto.ClientResponseDTO;

@Tag(name = "Clients", description = "Gestion des clients")
@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final IClientService clientService;

    @Operation(summary = "Lister les clients")
    @ApiResponse(responseCode = "200", description = "Liste renvoyée")
    @GetMapping
    public List<ClientResponseDTO> findAll() {
        return clientService.findAll();
    }

    @Operation(summary = "Consulter un client")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client trouvé"),
            @ApiResponse(responseCode = "404", description = "Client introuvable")
    })
    @GetMapping("/{id}")
    public ClientResponseDTO findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return clientService.findById(id);
    }

    @Operation(summary = "Créer un client")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Client créé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientResponseDTO create(@Valid @RequestBody ClientRequestDTO dto) {
        return clientService.create(dto);
    }

    @Operation(summary = "Modifier un client")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client modifié"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Client introuvable")
    })
    @PutMapping("/{id}")
    public ClientResponseDTO update(@Parameter(description = "Identifiant") @PathVariable Long id,
                                 @Valid @RequestBody ClientRequestDTO dto) {
        return clientService.update(id, dto);
    }

    @Operation(summary = "Supprimer un client")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Client supprimé"),
            @ApiResponse(responseCode = "404", description = "Client introuvable"),
            @ApiResponse(responseCode = "409", description = "Client encore lié à d'autres données")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        clientService.deleteById(id);
    }
}
