package be.unamur.fpgen.mapper.domainToWeb;

import be.unamur.fpgen.generation.AbstractGeneration;
import be.unamur.fpgen.generation.InstantMessageGeneration;
import be.unamur.model.Generation;
import be.unamur.model.GenerationType;

public class GenerationDomainToWebMapper {

    public static Generation mapInstantMessageGeneration(final InstantMessageGeneration domain){
        return new Generation()
                .id(domain.getId())
                .creationDate(domain.getCreationDate())
                .modificationDate(domain.getModificationDate())
                .generationDate(domain.getGenerationDate())
                .generationType(GenerationType.INSTANT_MESSAGE)
                .author(domain.getAuthor().getTrigram())
                .details(domain.getDetails())
                .quantity(domain.getQuantity());
    }

    public static Generation map(final AbstractGeneration domain){
        return new Generation()
                .id(domain.getId())
                .creationDate(domain.getCreationDate())
                .modificationDate(domain.getModificationDate())
                .generationDate(domain.getGenerationDate())
                .generationType(domain instanceof InstantMessageGeneration ? GenerationType.INSTANT_MESSAGE : GenerationType.CONVERSATION)
                .messageType(MessageTypeDomainToWebMapper.map(domain.getType()))
                .messageTopic(MessageTopicDomainToWebMapper.map(domain.getTopic()))
                .systemPrompt(domain.getSystemPrompt())
                .userPrompt(domain.getUserPrompt())
                .author(domain.getAuthor().getTrigram())
                .details(domain.getDetails())
                .quantity(domain.getQuantity());
    }
}
