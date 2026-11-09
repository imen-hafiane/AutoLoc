package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.ClientMapper;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.web.dto.ClientRequestDTO;
import tn.esprit.autoloc.web.dto.ClientResponseDTO;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final IClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public ClientResponseDTO create(ClientRequestDTO dto) {
        Client client = clientMapper.toEntity(dto);
        return clientMapper.toDto(clientRepository.save(client));
    }

    @Override
    public ClientResponseDTO findById(Long id) {
        return clientMapper.toDto(getClient(id));
    }

    @Override
    public List<ClientResponseDTO> findAll() {
        return clientRepository.findAll().stream().map(clientMapper::toDto).toList();
    }

    @Override
    public ClientResponseDTO update(Long id, ClientRequestDTO dto) {
        Client existant = getClient(id);
        clientMapper.updateEntity(existant, dto);
        return clientMapper.toDto(clientRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Client", id);
        }
        clientRepository.deleteById(id);
    }

    private Client getClient(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", id));
    }
}
