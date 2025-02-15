package be.unamur.fpgen.service;

import be.unamur.fpgen.author.AuthorStatusEnum;
import be.unamur.fpgen.author.pagination.AuthorsPage;
import be.unamur.fpgen.author.pagination.PagedAuthorsQuery;
import be.unamur.fpgen.context.UserContextHolder;
import be.unamur.fpgen.exception.AuthorAlreadyExistException;
import be.unamur.fpgen.exception.AuthorNotFoundException;
import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.mapper.jpaToDomain.AuthorJpaToDomainMapper;
import be.unamur.fpgen.notification.Notification;
import be.unamur.fpgen.notification.NotificationStatus;
import be.unamur.fpgen.repository.AuthorRepository;
import be.unamur.fpgen.repository.NotificationRepository;
import be.unamur.fpgen.utils.MapperUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Service class for Author
 */
@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final KeycloakService keycloakService;
    private final NotificationRepository notificationRepository;

    public AuthorService(AuthorRepository authorRepository, KeycloakService keycloakService, NotificationRepository notificationRepository) {
        this.authorRepository = authorRepository;
        this.keycloakService = keycloakService;
        this.notificationRepository = notificationRepository;
    }

    /**
     * Create a new author
     * @param author the author to create
     * @return the created author
     */
    @Transactional
    public Author create(final Author author){
        return authorRepository.saveAuthor(author);
    }

    /**
     * Create a new author if not exists
     * @param author the author to create
     * @return the created author
     */
    @Transactional
    public Author createIfNotExists(final Author author){
        if(authorRepository.existsAuthorByTrigram(author.getTrigram())){
            throw AuthorAlreadyExistException.withTrigram(author.getTrigram());
        }
        author.updateStatus(AuthorStatusEnum.WAITING_VERIFICATION);

        sendNotificationToAllAdministrators(String.format
                ("VERIFY ACCOUNT: %s %s with trigram %s", author.getFirstName(), author.getLastName(), author.getTrigram()));

        return authorRepository.saveAuthor(author);
    }

    /**
     * Get author by id
     * @param authorId the author id
     * @return the author
     */
    @Transactional
    public Author getAuthorById(final UUID authorId){
        return authorRepository.getAuthorById(authorId).orElseThrow(() -> AuthorNotFoundException.withId(authorId));
    }

    /**
     * Get author by trigram
     * @param trigram the author trigram
     * @return the author
     */
    @Transactional
    public Author getAuthorByTrigram(final String trigram){
        return authorRepository.findAuthorByTrigram(trigram).orElseThrow(() -> AuthorNotFoundException.withTrigram(trigram));
    }

    /**
     * Get all authors
     * @return the authors
     */
    @Transactional
    public List<Author> getAuthors(){
        return authorRepository.getAuthors();
    }

    /**
     * Search authors
     * @param query the query
     * @return the author list matching the query
     */
    @Transactional
    public AuthorsPage searchAuthorPaginate(final PagedAuthorsQuery query){
        //1. get pageable
        final Pageable pageable = PageRequest
                .of(query.getQueryPage().getPage(),
                        query.getQueryPage().getSize());

        //2. search Authors
        return authorRepository.findAuthorsPagination(
                query.getAuthorQuery().getLastname(),
                query.getAuthorQuery().getFirstname(),
                query.getAuthorQuery().getOrganization(),
                query.getAuthorQuery().getFunction(),
                query.getAuthorQuery().getTrigram(),
                query.getAuthorQuery().getEmail(),
                query.getAuthorQuery().getStatus(),
                pageable);
    }

    /**
     * Update author status
     * @param authorId the author id
     * @param status the new status
     */
    @Transactional
    public void updateAuthorStatus(final UUID authorId, final AuthorStatusEnum status){
        // 1. get author
        final Author author = authorRepository.getAuthorById(authorId).orElseThrow(() -> AuthorNotFoundException.withId(authorId));

        // 2. create user keycloak
        if (!author.isAccountCreated() && !AuthorStatusEnum.VERIFIED.equals(author.getStatus()) && AuthorStatusEnum.VERIFIED.equals(status)) {
            keycloakService.createUser(author);
            author.createAccount();
        } else if (author.isAccountCreated() && AuthorStatusEnum.VERIFIED.equals(author.getStatus()) && AuthorStatusEnum.SUSPENDED.equals(status)) {
            keycloakService.updateUserStatus(false, author.getTrigram());
        } else if (author.isAccountCreated() && AuthorStatusEnum.SUSPENDED.equals(author.getStatus()) && AuthorStatusEnum.VERIFIED.equals(status)) {
            keycloakService.updateUserStatus(true, author.getTrigram());
        }

        // 3. update author status
        author.updateStatus(status);
        authorRepository.updateAuthor(author);
    }

    /**
     * Find all administrator
     * @return the list of administrators
     */
    @Transactional
    public List<Author> getAdministrators(){
        return authorRepository.findAdministrators();
    }

    /**
     * Send a notification to all administrators
     * @param message
     */
    @Transactional
    public void sendNotificationToAllAdministrators(final String message) {
        final Author sender = getAuthorByTrigram(Objects.nonNull(UserContextHolder.getContext().getTrigram()) ? UserContextHolder.getContext().getTrigram() : "SYSTEM");
        final List<Author> administrators = getAdministrators();

        administrators.forEach(administrator -> {
            final Notification notificationToCreate = Notification.newBuilder()
                    .withSender(sender)
                    .withReceiver(administrator)
                    .withMessage(message)
                    .withStatus(NotificationStatus.UNREAD)
                    .build();
            notificationRepository.createNotification(notificationToCreate);
        });
    }
}
