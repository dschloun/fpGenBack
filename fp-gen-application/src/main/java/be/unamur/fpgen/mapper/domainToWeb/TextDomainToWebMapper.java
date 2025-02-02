package be.unamur.fpgen.mapper.domainToWeb;

import be.unamur.fpgen.text.Text;

/**
 * This class is a mapper which map the domain object to the web object
 */
public class TextDomainToWebMapper {

    public static be.unamur.model.Text map(final Text domain){
        return new be.unamur.model.Text()
                .text(domain.getContent());
    }
}
