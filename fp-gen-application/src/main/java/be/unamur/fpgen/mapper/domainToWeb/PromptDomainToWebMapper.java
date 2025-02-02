package be.unamur.fpgen.mapper.domainToWeb;


import be.unamur.model.Prompt;
import be.unamur.model.PromptStatusEnum;

import java.util.Optional;

/**
 * This class is a mapper which map the domain object to the web object
 */
public class PromptDomainToWebMapper {

    public static Prompt map(be.unamur.fpgen.prompt.Prompt domain) {
        if (domain == null) {
            return null;
        }

        return new Prompt()
                .id(domain.getId())
                .creationDate(domain.getCreationDate())
                .datasetType(DatasetTypeDomainToWebMapper.map(domain.getDatasetType()))
                .messageType(MessageTypeDomainToWebMapper.map(domain.getMessageType()))
                .version(domain.getVersion())
                .userContent(domain.getUserPrompt())
                .systemContent(domain.getSystemPrompt())
                .author(AuthorDomainToWebMapper.map(domain.getAuthor()))
                .status(map(domain.getStatus()))
                .motivation(domain.getMotivation())
                ._default(domain.isDefaultPrompt());
    }

    public static PromptStatusEnum map(be.unamur.fpgen.prompt.PromptStatusEnum status) {
        return Optional.ofNullable(status)
                .map(s -> PromptStatusEnum.fromValue(s.name()))
                .orElse(null);
    }
}
