package souza.oliveira.daniel.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "proposals")
public class ProposalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String customer;
    @Column(name="price_tonne")
    private BigDecimal priceTonne;
    @Column
    private Integer tonnes;
    @Column
    private String country;
    @Column(name="proposal_validity_days")
    private Integer proposalValidityDays;
}
