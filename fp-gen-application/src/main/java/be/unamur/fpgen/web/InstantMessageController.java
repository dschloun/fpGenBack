package be.unamur.fpgen.web;

import be.unamur.api.GenerationApi;
import be.unamur.api.InstantMessageApi;
import be.unamur.fpgen.mapper.domainToWeb.InstantMessageDomainToWebMapper;
import be.unamur.fpgen.mapper.domainToWeb.pagination.InstantMessagePaginationDomainToWebMapper;
import be.unamur.fpgen.mapper.webToDomain.pagination.InstantMessagePaginationWebToDomainMapper;
import be.unamur.fpgen.service.InstantMessageService;
import be.unamur.fpgen.utils.MapperUtil;
import be.unamur.model.InstantMessage;
import be.unamur.model.InstantMessageBatchCreation;
import be.unamur.model.InstantMessagesPage;
import be.unamur.model.PagedInstantMessageQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * This rest controller class is the implementation of the InstantMessageApi interface.
 * It is used to manage the instant messages.
 */
@Controller
public class InstantMessageController implements InstantMessageApi {

    private final InstantMessageService instantMessageService;

    public InstantMessageController(final InstantMessageService instantMessageService) {
        this.instantMessageService = instantMessageService;
    }

    /**
     * This method is used to create a new instant message.
     * @param instantMessageBatchCreation the instant message to create
     * @return a response entity with the status code
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<Void> createMessage(@Valid InstantMessageBatchCreation instantMessageBatchCreation) {
        instantMessageService.generateInstantMessages(instantMessageBatchCreation);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * This method is used to get an instant message by its id.
     * @param instantMessageId the id of the instant message
     * @return a response entity with the instant message
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<InstantMessage> getInstantMessageById(UUID instantMessageId) {
        return new ResponseEntity<>(
                InstantMessageDomainToWebMapper.map(
                        instantMessageService.getInstantMessageById(instantMessageId)), HttpStatus.OK);
    }

    /**
     * This method is used to search instant messages with pagination.
     * @param pagedInstantMessageQuery the query to search instant messages
     * @return a response entity with the instant messages page
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<InstantMessagesPage> searchInstantMessagesPaginate(@Valid PagedInstantMessageQuery pagedInstantMessageQuery) {
        return new ResponseEntity<>(
                InstantMessagePaginationDomainToWebMapper.map(
                        instantMessageService.searchInstantMessagesPaginate(
                                InstantMessagePaginationWebToDomainMapper.map(pagedInstantMessageQuery))
                ), HttpStatus.OK
        );
    }

    /**
     * delete an instant message by its id
     * @param instantMessageId
     */
    @RolesAllowed({"administrator"})
    @Override
    public ResponseEntity<Void> deleteInstantMessageById(UUID instantMessageId) {
        instantMessageService.deleteById(instantMessageId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * find all instant messages by generation id
     * @param generationId
     * @return
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<List<InstantMessage>> findInstantMessagesByGenerationId(UUID generationId) {
        return new ResponseEntity<>(
                MapperUtil.mapList(instantMessageService.findAllByGenerationId(generationId), InstantMessageDomainToWebMapper::map),
                HttpStatus.OK);
    }
}
