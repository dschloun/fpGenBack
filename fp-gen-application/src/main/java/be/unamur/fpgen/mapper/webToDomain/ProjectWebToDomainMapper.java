package be.unamur.fpgen.mapper.webToDomain;


import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.project.Project;
import be.unamur.fpgen.project.ProjectTypeEnum;
import be.unamur.model.ProjectType;

import java.util.Optional;

/**
 * This class is a mapper which map the web object to the domain object
 */
public class ProjectWebToDomainMapper {
    public static Project map(final be.unamur.model.ProjectCreation web, final Author author){
        return Project.newBuilder()
                .withType(map(web.getProjectType()))
                .withName(web.getName())
                .withDescription(web.getDescription())
                .withOrganisation(web.getOrganization())
                .withAuthor(author)
                .build();
    }

    public static ProjectTypeEnum map(final ProjectType domain) {
        return Optional.ofNullable(domain)
                .map(Enum::name)
                .map(ProjectTypeEnum::valueOf)
                .orElse(null);
    }
}
