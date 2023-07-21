package souza.oliveira.daniel.service;

import jakarta.enterprise.context.ApplicationScoped;
import souza.oliveira.daniel.dto.ProposalDetailsDTO;

@ApplicationScoped
public interface ProposalService {
    ProposalDetailsDTO findFullProposal(long id);

    void createNewProposal(ProposalDetailsDTO proposalDetailsDTO);

    void removeProposal(long id);
}
