package be.unamur.fpgen.mapper.webToDomain;

import be.unamur.fpgen.project.ProjectTypeEnum;
import be.unamur.model.ProjectType;

import java.util.Optional;

public class ProjectTypeWebToDomainMapper {
    public static ProjectTypeEnum map(final ProjectType web){
        return Optional.ofNullable(web)
                .map(Enum::name)
                .map(ProjectTypeEnum::valueOf)
                .orElse(null);
    }
}
