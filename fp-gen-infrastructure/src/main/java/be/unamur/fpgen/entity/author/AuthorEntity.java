package be.unamur.fpgen.entity.author;

import be.unamur.fpgen.author.AuthorStatusEnum;
import be.unamur.fpgen.entity.PromptEntity;
import be.unamur.fpgen.entity.base.BaseUuidEntity;
import be.unamur.fpgen.entity.dataset.DatasetEntity;
import be.unamur.fpgen.entity.generation.GenerationEntity;
import be.unamur.fpgen.entity.generation.ongoing_generation.OngoingGenerationEntity;
import be.unamur.fpgen.entity.notification.NotificationEntity;
import be.unamur.fpgen.entity.project.ProjectEntity;
import be.unamur.fpgen.entity.result.ResultEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents an author of a message, conversation, project, dataset or prompt
 * It's a user of the application
 */
@Entity
@Table(name = "author")
public class AuthorEntity extends BaseUuidEntity {

    // members
    private String lastname;
    private String firstname;
    private String trigram;
    private String organization;
    private String function;
    private String email;
    private String phoneNumber;
    private Set<GenerationEntity> generationList;
    private Set<DatasetEntity> datasetList;
    private Set<ProjectEntity> projectList;
    private Set<OngoingGenerationEntity> ongoingGenerationList;
    private Set<ResultEntity> resultList = new HashSet<>();
    private Set<PromptEntity> promptList = new HashSet<>();
    private Set<NotificationEntity> sentNotificationList = new HashSet<>();
    private Set<NotificationEntity> receivedNotificationList = new HashSet<>();
    private AuthorStatusEnum status;
    private boolean acceptTermsOfUse;
    private String motivation;
    private boolean accountCreated;
    private Boolean administrator;

    // getters and setters

    @Column(name = "lastname", nullable = false)
    public String getLastname() {
        return lastname;
    }

    public void setLastname(final String lastName) {
        this.lastname = lastName;
    }

    @Column(name = "firstname", nullable = false)
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(final String firstName) {
        this.firstname = firstName;
    }

    @Column(name = "trigram", nullable = false)
    public String getTrigram() {
        return trigram;
    }

    public void setTrigram(final String trigram) {
        this.trigram = trigram;
    }

    @Column(name = "organization")
    public String getOrganization() {
        return organization;
    }

    public void setOrganization(final String organization) {
        this.organization = organization;
    }

    @Column(name = "function")
    public String getFunction() {
        return function;
    }

    public void setFunction(final String function) {
        this.function = function;
    }

    @Column(name = "email", nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    @Column(name = "phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @OneToMany(mappedBy = "author")
    public Set<GenerationEntity> getGenerationList() {
        return generationList;
    }

    public void setGenerationList(final Set<GenerationEntity> generationList) {
        this.generationList = generationList;
    }

    @OneToMany(mappedBy = "author")
    public Set<DatasetEntity> getDatasetList() {
        return datasetList;
    }

    public void setDatasetList(final Set<DatasetEntity> datasetList) {
        this.datasetList = datasetList;
    }

    @OneToMany(mappedBy = "author")
    public Set<ProjectEntity> getProjectList() {
        return projectList;
    }

    public void setProjectList(Set<ProjectEntity> projectList) {
        this.projectList = projectList;
    }

    @OneToMany(mappedBy = "author")
    public Set<OngoingGenerationEntity> getOngoingGenerationList() {
        return ongoingGenerationList;
    }

    public void setOngoingGenerationList(Set<OngoingGenerationEntity> ongoingGenerationList) {
        this.ongoingGenerationList = ongoingGenerationList;
    }

    @OneToMany(mappedBy = "author", orphanRemoval = true)
    public Set<ResultEntity> getResultList() {
        return resultList;
    }

    public void setResultList(Set<ResultEntity> resultList) {
        this.resultList = resultList;
    }

    @OneToMany(mappedBy = "author", orphanRemoval = true)
    public Set<PromptEntity> getPromptList() {
        return promptList;
    }

    public void setPromptList(Set<PromptEntity> promptList) {
        this.promptList = promptList;
    }

    @OneToMany(mappedBy = "sender", orphanRemoval = true)
    public Set<NotificationEntity> getSentNotificationList() {
        return sentNotificationList;
    }

    public void setSentNotificationList(Set<NotificationEntity> notificationList) {
        this.sentNotificationList = notificationList;
    }

    @OneToMany(mappedBy = "receiver", orphanRemoval = true)
    public Set<NotificationEntity> getReceivedNotificationList() {
        return receivedNotificationList;
    }

    public void setReceivedNotificationList(Set<NotificationEntity> receivedNotificationList) {
        this.receivedNotificationList = receivedNotificationList;
    }

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    public AuthorStatusEnum getStatus() {
        return status;
    }

    public void setStatus(AuthorStatusEnum status) {
        this.status = status;
    }

    @Column(name = "accept_terms_of_use", nullable = false)
    public boolean isAcceptTermsOfUse() {
        return acceptTermsOfUse;
    }

    public void setAcceptTermsOfUse(boolean acceptTermsOfUse) {
        this.acceptTermsOfUse = acceptTermsOfUse;
    }

    @Column(name = "motivation", nullable = false)
    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    @Column(name = "account_created", nullable = false)
    public boolean isAccountCreated() {
        return accountCreated;
    }

    public void setAccountCreated(boolean accountCreated) {
        this.accountCreated = accountCreated;
    }

    @Column(name = "administrator")
    public Boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(Boolean administrator) {
        this.administrator = administrator;
    }
}
