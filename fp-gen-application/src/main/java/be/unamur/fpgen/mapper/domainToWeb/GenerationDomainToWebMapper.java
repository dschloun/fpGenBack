package be.unamur.fpgen.mapper.domainToWeb;

import be.unamur.fpgen.generation.Generation;

/**
 * This class is a mapper which map the domain object to the web object
 */
public class GenerationDomainToWebMapper {

    public static be.unamur.model.Generation map(final Generation domain){
        return new be.unamur.model.Generation()
                .id(domain.getId())
                .creationDate(domain.getCreationDate())
                .modificationDate(domain.getModificationDate())
                .generationType(GenerationTypeDomainToWebMapper.map(domain.getGenerationType()))
                .messageType(MessageTypeDomainToWebMapper.map(domain.getType()))
                .messageTopic(MessageTopicDomainToWebMapper.map(domain.getTopic()))
                .promptVersion(domain.getPrompt().getVersion())
                .author(domain.getAuthor().getTrigram())
                .details(domain.getDetails())
                .quantity(domain.getQuantity());
    }
}
