package be.unamur.fpgen.mapper.domainToWeb;

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
}
