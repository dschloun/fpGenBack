package be.unamur.fpgen.entity;

import be.unamur.fpgen.dataset.DatasetTypeEnum;
import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.base.BaseUuidEntity;
import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.prompt.PromptStatusEnum;

import javax.persistence.*;

/**
 * A prompt is dedicated to a dataset type(CONVERSATION / MESSAGE) and a message type (SOCIAL_ENGINEERING / HARASSMENT / GENUINE)
 * A version allow to track the evolution of the prompt
 * It's composed of a user prompt and a system prompt
 * it has a status because it has to be validated by the administrator
 */
@Entity
@Table(name = "prompt")
public class PromptEntity extends BaseUuidEntity {
    private DatasetTypeEnum datasetType;
    private MessageTypeEnum messageType;
    private Integer version;
    private String userPrompt;
    private String systemPrompt;
    private AuthorEntity author;
    private PromptStatusEnum status;
    private boolean defaultPrompt;
    private String motivation;

    @Column(name = "dataset_type", nullable = false)
    @Enumerated(EnumType.STRING)
    public DatasetTypeEnum getDatasetType() {
        return datasetType;
    }

    public void setDatasetType(DatasetTypeEnum datasetType) {
        this.datasetType = datasetType;
    }

    @Column(name = "message_type", nullable = false)
    @Enumerated(EnumType.STRING)
    public MessageTypeEnum getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageTypeEnum type) {
        this.messageType = type;
    }

    @Column(name = "version", nullable = false)
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Column(name = "user_prompt", nullable = false)
    public String getUserPrompt() {
        return userPrompt;
    }

    public void setUserPrompt(String userPrompt) {
        this.userPrompt = userPrompt;
    }

    @Column(name = "system_prompt")
    public String getSystemPrompt() {
        return systemPrompt;
    }

    public void setSystemPrompt(String systemPrompt) {
        this.systemPrompt = systemPrompt;
    }

    @ManyToOne
    @JoinColumn(name = "author_id")
    public AuthorEntity getAuthor() {
        return author;
    }


    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    public PromptStatusEnum getStatus() {
        return status;
    }

    public void setStatus(PromptStatusEnum status) {
        this.status = status;
    }

    @Column(name = "default_prompt", nullable = false)
    public boolean isDefaultPrompt() {
        return defaultPrompt;
    }

    public void setDefaultPrompt(boolean defaultPrompt) {
        this.defaultPrompt = defaultPrompt;
    }

    @Column(name = "motivation")
    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }
}
