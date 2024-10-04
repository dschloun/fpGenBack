package be.unamur.fpgen.entity;

import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.base.BaseUuidEntity;
import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.prompt.PromptStatusEnum;

import javax.persistence.*;

@Entity
@Table(name = "prompt")
public class PromptEntity extends BaseUuidEntity {
    private MessageTypeEnum type;
    private Integer version;
    private String userPrompt;
    private String systemPrompt;
    private AuthorEntity author;
    private PromptStatusEnum status;
    private boolean defaultPrompt;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    public MessageTypeEnum getType() {
        return type;
    }

    public void setType(MessageTypeEnum type) {
        this.type = type;
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
}
