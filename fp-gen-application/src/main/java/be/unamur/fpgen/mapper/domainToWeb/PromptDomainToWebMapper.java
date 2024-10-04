package be.unamur.fpgen.mapper.domainToWeb;


import be.unamur.model.Prompt;
import be.unamur.model.PromptStatusEnum;

import java.util.Optional;

public class PromptDomainToWebMapper {

    public static Prompt map(be.unamur.fpgen.prompt.Prompt domain) {
        if (domain == null) {
            return null;
        }

        return new Prompt()
                .id(domain.getId())
                .creationDate(domain.getCreationDate())
                .category(MessageTypeDomainToWebMapper.map(domain.getType()))
                .version(domain.getVersion())
                .userContent(domain.getUserPrompt())
                .systemContent(domain.getSystemPrompt())
                .author(AuthorDomainToWebMapper.map(domain.getAuthor()))
                .status(map(domain.getStatus()));
    }

    public static PromptStatusEnum map(be.unamur.fpgen.prompt.PromptStatusEnum status) {
        return Optional.ofNullable(status)
                .map(s -> PromptStatusEnum.fromValue(s.name()))
                .orElse(null);
    }
}
