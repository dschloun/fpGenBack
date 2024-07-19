package be.unamur.fpgen.mapper.domainToWeb;

import be.unamur.fpgen.dataset.ConversationDataset;
import be.unamur.fpgen.dataset.DatasetFunctionEnum;
import be.unamur.fpgen.dataset.InstantMessageDataset;
import be.unamur.model.Dataset;
import be.unamur.model.DatasetType;

import java.util.Optional;

public class DatasetDomainToWebMapper {

    public static be.unamur.model.DatasetFunctionEnum mapFunction(final DatasetFunctionEnum domain){
        return Optional.ofNullable(domain)
                .map(Enum::name)
                .map(be.unamur.model.DatasetFunctionEnum::valueOf)
                .orElseThrow();
    }

    public static Dataset mapInstantMessageDataset(final InstantMessageDataset domain){
        return new Dataset()
                .id(domain.getId())
                .creationDate(domain.getCreationDate())
                .modificationDate(domain.getModificationDate())
                .author(AuthorDomainToWebMapper.map(domain.getAuthor()))
                .function(mapFunction(domain.getDatasetFunction()))
                .type(DatasetType.INSTANT_MESSAGE);
    }

    public static Dataset mapConversationDataset(final ConversationDataset domain){
        return new Dataset()
                .id(domain.getId())
                .creationDate(domain.getCreationDate())
                .modificationDate(domain.getModificationDate())
                .author(AuthorDomainToWebMapper.map(domain.getAuthor()))
                .type(DatasetType.CONVERSATION);
    }
}
