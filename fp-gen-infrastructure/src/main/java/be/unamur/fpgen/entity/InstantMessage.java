package be.unamur.fpgen.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serial;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class InstantMessage extends BaseUuidEntity {

    @Serial
    private static final long serialVersionUID = -80039190673154484L;

    private String content;

    private String topic;
    private String type;

    public String getContent() {
        return content;
    }

}
