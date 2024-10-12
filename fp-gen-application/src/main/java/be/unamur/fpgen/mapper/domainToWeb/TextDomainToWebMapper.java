package be.unamur.fpgen.mapper.domainToWeb;

import be.unamur.fpgen.text.Text;

public class TextDomainToWebMapper {

    public static be.unamur.model.Text map(final Text domain){
        return new be.unamur.model.Text()
                .text(domain.getContent());
    }
}
