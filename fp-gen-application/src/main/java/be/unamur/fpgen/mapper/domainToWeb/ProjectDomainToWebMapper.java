package be.unamur.fpgen.mapper.domainToWeb;

import be.unamur.fpgen.dataset.AbstractDataset;
import be.unamur.fpgen.dataset.ConversationDataset;
import be.unamur.fpgen.dataset.InstantMessageDataset;
import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.project.ProjectTypeEnum;
import be.unamur.fpgen.utils.MapperUtil;
import be.unamur.model.DatasetType;
import be.unamur.model.MessageType;
import be.unamur.model.Project;
import be.unamur.model.ProjectType;

import java.util.Optional;

public class ProjectDomainToWebMapper {

    public static Project map(be.unamur.fpgen.project.Project domain) {
        return new Project()
                .id(domain.getId())
                .creationDate(domain.getCreationDate())
                .modificationDate(domain.getModificationDate())
                .name(domain.getName())
                .description(domain.getDescription())
                .organization(domain.getOrganisation())
                .type(map(domain.getType()))
                .author(AuthorDomainToWebMapper.map(domain.getAuthor()))
                .datasets(domain.getDatasetList()
                        .stream()
                        .map(d ->
                                DatasetDomainToWebMapper.map(d, getType(d)))
                        .toList());

    }

    public static ProjectType map(final ProjectTypeEnum domain) {
        return Optional.ofNullable(domain)
                .map(Enum::name)
                .map(ProjectType::valueOf)
                .orElse(null);
    }

    private static DatasetType getType(AbstractDataset dataset) {
        if (dataset instanceof InstantMessageDataset) {
            return DatasetType.INSTANT_MESSAGE;
        } else if (dataset instanceof ConversationDataset) {
            return DatasetType.CONVERSATION;
        } else {
            return null;
        }

    }
}
