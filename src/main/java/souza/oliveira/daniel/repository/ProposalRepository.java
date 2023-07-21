package souza.oliveira.daniel.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import souza.oliveira.daniel.entity.ProposalEntity;

import java.util.Optional;

@ApplicationScoped
public class ProposalRepository implements PanacheRepository<ProposalEntity> {
    public Optional<ProposalEntity> findByCustomer(String customer){
        return this.find("customer", customer)
                .firstResultOptional();
    }
}
