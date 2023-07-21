package souza.oliveira.daniel.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import souza.oliveira.daniel.dto.ProposalDetailsDTO;
import souza.oliveira.daniel.service.ProposalService;

@Path("/api/proposal")
@ApplicationScoped
public class ProposalController {
    private final Logger LOG = LoggerFactory.getLogger(ProposalController.class);
    private final ProposalService proposalService;

    @Inject
    public ProposalController(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"user", "manager"})
    public ProposalDetailsDTO findDetailsProposal(@PathParam("id") long id){
        return proposalService.findFullProposal(id);
    }

    @POST
    public Response createNewProposal(ProposalDetailsDTO proposalDetails){
        LOG.info("-- Recebendo proposta de compra --");

        proposalService.createNewProposal(proposalDetails);
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("/{id}")
    public Response removeProposal(@PathParam("id") long id){
        proposalService.removeProposal(id);
        return Response.ok().build();
    }
}
