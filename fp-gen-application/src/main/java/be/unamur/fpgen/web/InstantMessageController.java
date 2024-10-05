package be.unamur.fpgen.web;

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

@Controller
public class InstantMessageController implements InstantMessageApi {

    private final InstantMessageService instantMessageService;

    public InstantMessageController(final InstantMessageService instantMessageService) {
        this.instantMessageService = instantMessageService;
    }

    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<Void> createMessage(@Valid InstantMessageBatchCreation instantMessageBatchCreation) {
        instantMessageService.generateInstantMessages(instantMessageBatchCreation);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<InstantMessage> getInstantMessageById(UUID instantMessageId) {
        return new ResponseEntity<>(
                InstantMessageDomainToWebMapper.map(
                        instantMessageService.getInstantMessageById(instantMessageId)), HttpStatus.OK);
    }

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

    @RolesAllowed({"administrator"})
    @Override
    public ResponseEntity<Void> deleteInstantMessageById(UUID instantMessageId) {
        instantMessageService.deleteById(instantMessageId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<List<InstantMessage>> findInstantMessagesByGenerationId(UUID generationId) {
        return new ResponseEntity<>(
                MapperUtil.mapList(instantMessageService.findAllByGenerationId(generationId), InstantMessageDomainToWebMapper::map),
                HttpStatus.OK);
    }
}
