package be.unamur.fpgen.repository.project;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.mapper.domainToJpa.ProjectDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.ProjectJpaToDomainMapper;
import be.unamur.fpgen.pagination.Pagination;
import be.unamur.fpgen.project.Project;
import be.unamur.fpgen.project.ProjectTypeEnum;
import be.unamur.fpgen.project.pagination.ProjectsPage;
import be.unamur.fpgen.repository.OngoingGenerationRepository;
import be.unamur.fpgen.repository.author.JpaAuthorRepositoryCRUD;
import be.unamur.fpgen.repository.ProjectRepository;
import be.unamur.fpgen.utils.MapperUtil;
import be.unamur.fpgen.utils.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * See the specifications in the {@link ProjectRepository} interface.
 */
@Repository
public class JpaProjectRepository implements ProjectRepository {
    private final JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD;
    private final JpaProjectRepositoryCRUD jpaProjectRepositoryCRUD;

    public JpaProjectRepository(JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD,
                                JpaProjectRepositoryCRUD jpaProjectRepositoryCRUD) {
        this.jpaAuthorRepositoryCRUD = jpaAuthorRepositoryCRUD;
        this.jpaProjectRepositoryCRUD = jpaProjectRepositoryCRUD;
    }

    @Override
    public Project save(Project project, Author author) {
        final AuthorEntity authorEntity = jpaAuthorRepositoryCRUD.getReferenceById(author.getId());

        return ProjectJpaToDomainMapper.mapProject(jpaProjectRepositoryCRUD
                .save(ProjectDomainToJpaMapper
                        .mapForCreate(project, authorEntity)));
    }

    @Override
    public Project update(Project project) {
        return null;
    }

    @Override
    public Optional<Project> findById(UUID id) {
        return jpaProjectRepositoryCRUD.findById(id).map(ProjectJpaToDomainMapper::mapProject);
    }

    @Override
    public List<Project> findAllByAuthorId(UUID authorId) {
        return MapperUtil.mapList(jpaProjectRepositoryCRUD.findByAuthorId(authorId), ProjectJpaToDomainMapper::mapProject);
    }

    @Override
    public void deleteById(UUID id) {
        jpaProjectRepositoryCRUD.deleteById(id);
    }

    @Override
    public ProjectsPage findPagination(ProjectTypeEnum projectType, String name, String description, String organization, String authorTrigram, OffsetDateTime startDate, OffsetDateTime endDate, Pageable pageable) {
        // 1. get in Page format
        Page<Project> page = jpaProjectRepositoryCRUD.
                findPagination(
                        projectType,
                        StringUtil.toLowerCaseIfNotNull(name),
                        StringUtil.toLowerCaseIfNotNull(description),
                        StringUtil.toLowerCaseIfNotNull(organization),
                        StringUtil.toLowerCaseIfNotNull(authorTrigram),
                        startDate,
                        endDate,
                        pageable)
                .map(ProjectJpaToDomainMapper::mapProject);

        // 2. convert
        final ProjectsPage projectsPage = ProjectsPage.newBuilder()
                .withPagination(new Pagination.Builder()
                        .page(page.getNumber())
                        .size(page.getSize())
                        .totalSize((int)page.getTotalElements())
                        .build())
                .withProjectList(page.getContent())
                .build();

        // 3. return
        return projectsPage;
    }
}
