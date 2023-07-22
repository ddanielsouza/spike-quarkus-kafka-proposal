package souza.oliveira.daniel.controller;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import souza.oliveira.daniel.dto.ProposalDetailsDTO;
import souza.oliveira.daniel.service.ProposalService;

@Path("/api/proposal")
@ApplicationScoped
@Authenticated
public class ProposalController {
    private final Logger LOG = LoggerFactory.getLogger(ProposalController.class);
    private final ProposalService proposalService;
    private final JsonWebToken jsonWebToken;

    @Inject
    public ProposalController(ProposalService proposalService, JsonWebToken jsonWebToken) {
        this.proposalService = proposalService;
        this.jsonWebToken = jsonWebToken;
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"user", "manager"})
    public ProposalDetailsDTO findDetailsProposal(@PathParam("id") long id){
        return proposalService.findFullProposal(id);
    }

    @POST
    @RolesAllowed({"proposal-customer"})
    public Response createNewProposal(ProposalDetailsDTO proposalDetails){
        LOG.info("-- Recebendo proposta de compra --");

        proposalService.createNewProposal(proposalDetails);
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"manager"})
    public Response removeProposal(@PathParam("id") long id){
        proposalService.removeProposal(id);
        return Response.ok().build();
    }
}
