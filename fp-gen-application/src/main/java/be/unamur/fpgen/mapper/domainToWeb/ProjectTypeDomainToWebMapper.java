package be.unamur.fpgen.mapper.domainToWeb;

import be.unamur.fpgen.project.ProjectTypeEnum;
import be.unamur.model.ProjectType;

import java.util.Optional;

public class ProjectTypeDomainToWebMapper {

    public static ProjectType map(final ProjectTypeEnum domain){
        return Optional.ofNullable(domain)
                .map(Enum::name)
                .map(ProjectType::valueOf)
                .orElse(null);
    }
}
