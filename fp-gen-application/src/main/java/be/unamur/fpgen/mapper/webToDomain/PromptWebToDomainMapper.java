package be.unamur.fpgen.mapper.webToDomain;

import be.unamur.fpgen.prompt.PromptStatusEnum;

import java.util.Optional;

public class PromptWebToDomainMapper {

    public static PromptStatusEnum map(final be.unamur.model.PromptStatusEnum web){
        return Optional.ofNullable(web)
                .map(status -> PromptStatusEnum.valueOf(status.name()))
                .orElse(null);
    }
}
