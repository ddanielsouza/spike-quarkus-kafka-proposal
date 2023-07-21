package souza.oliveira.daniel.message;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import souza.oliveira.daniel.dto.ProposalDTO;

@ApplicationScoped
public class KafkaEvents {
    private final Logger LOG = LoggerFactory.getLogger(KafkaEvents.class);

    @Channel("proposal-channel")
    Emitter<ProposalDTO> proposalRequestEmitter;

    public void sendNewKafkaEvent(ProposalDTO proposal){
        LOG.info("-- Enviando nova prospota à tópico kafka --");
        this.proposalRequestEmitter.send(proposal).toCompletableFuture().join();
    }
}
