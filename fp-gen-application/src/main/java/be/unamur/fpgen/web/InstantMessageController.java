package be.unamur.fpgen.web;

import be.unamur.api.InstantMessageApi;
import be.unamur.fpgen.mapper.domainToWeb.InstantMessageDomainToWebMapper;
import be.unamur.fpgen.mapper.domainToWeb.InstantMessagePaginationDomainToWebMapper;
import be.unamur.fpgen.mapper.webToDomain.InstantMessagePaginationWebToDomainMapper;
import be.unamur.fpgen.service.InstantMessageService;
import be.unamur.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Controller
public class InstantMessageController implements InstantMessageApi {

    private final InstantMessageService instantMessageService;

    public InstantMessageController(final InstantMessageService instantMessageService) {
        this.instantMessageService = instantMessageService;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return InstantMessageApi.super.getRequest();
    }

    @Override
    public ResponseEntity<Void> createMessage(@Valid InstantMessageBatchCreation instantMessageBatchCreation) {
        instantMessageService.generateInstantMessages(instantMessageBatchCreation);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<InstantMessage> getInstantMessageById(UUID instantMessageId) {
        return new ResponseEntity<>(
                InstantMessageDomainToWebMapper.map(
                        instantMessageService.getInstantMessageById(instantMessageId)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<InstantMessagesPage> searchInstantMessagesPaginate(@Valid PagedInstantMessageQuery pagedInstantMessageQuery) {
        return new ResponseEntity<>(
                InstantMessagePaginationDomainToWebMapper.map(
                        instantMessageService.searchInstantMessagesPaginate(
                                InstantMessagePaginationWebToDomainMapper.map(pagedInstantMessageQuery))
                ), HttpStatus.OK
        );
    }
}
