package souza.oliveira.daniel.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.opentracing.Traced;
import souza.oliveira.daniel.dto.ProposalDTO;
import souza.oliveira.daniel.dto.ProposalDetailsDTO;
import souza.oliveira.daniel.entity.ProposalEntity;
import souza.oliveira.daniel.message.KafkaEvents;
import souza.oliveira.daniel.repository.ProposalRepository;
import souza.oliveira.daniel.service.ProposalService;

@ApplicationScoped
@Traced
public class ProposalServiceImpl implements ProposalService {

    private final ProposalRepository proposalRepository;
    private final KafkaEvents kafkaEvents;

    @Inject
    public ProposalServiceImpl(ProposalRepository proposalRepository, 
                               KafkaEvents kafkaEvents) {
        this.proposalRepository = proposalRepository;
        this.kafkaEvents = kafkaEvents;
    }

    @Override
    public ProposalDetailsDTO findFullProposal(long id) {
        final var proposal = proposalRepository.findById(id);

        return ProposalDetailsDTO.builder()
                .proposalId(proposal.getId())
                .proposalValidityDays(proposal.getProposalValidityDays())
                .tonnes(proposal.getTonnes())
                .country(proposal.getCountry())
                .customer(proposal.getCustomer())
                .priceTonne(proposal.getPriceTonne())
                .build();
    }

    @Override
    @Transactional
    public void createNewProposal(ProposalDetailsDTO proposalDetailsDTO) {
        ProposalDTO proposal = buildAndSaveNewProposal(proposalDetailsDTO);
        kafkaEvents.sendNewKafkaEvent(proposal);
    }

    @Override
    @Transactional
    public void removeProposal(long id) {
        proposalRepository.deleteById(id);
    }

    private ProposalDTO buildAndSaveNewProposal(ProposalDetailsDTO proposalDetailsDTO) {
        final var proposal =  ProposalEntity.builder()
                .proposalValidityDays(proposalDetailsDTO.getProposalValidityDays())
                .tonnes(proposalDetailsDTO.getTonnes())
                .country(proposalDetailsDTO.getCountry())
                .priceTonne(proposalDetailsDTO.getPriceTonne())
                .customer(proposalDetailsDTO.getCustomer())
                .build();

        proposalRepository.persist(proposal);
        proposalRepository.flush();

        return ProposalDTO.builder()
                //.proposalId(proposalRepository.findByCustomer(proposal.getCustomer()).get().getId())
                .proposalId(proposal.getId())
                .priceTonne(proposal.getPriceTonne())
                .customer(proposal.getCustomer())
                .build();
    }
}
