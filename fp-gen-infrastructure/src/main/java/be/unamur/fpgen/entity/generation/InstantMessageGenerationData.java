package be.unamur.fpgen.entity.generation;

import be.unamur.fpgen.entity.instant_message.InstantMessageEntity;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Embeddable
public class InstantMessageGenerationData implements Serializable {
    @Serial
    private static final long serialVersionUID = 4455469519017267792L;

    private List<InstantMessageEntity> singleInstantMessageList;

    // getters and setters
    @OneToMany(mappedBy = "singleInstantMessageGeneration")
    public List<InstantMessageEntity> getSingleInstantMessageList() {
        return singleInstantMessageList;
    }

    public void setSingleInstantMessageList(List<InstantMessageEntity> singleInstantMessageList) {
        this.singleInstantMessageList = singleInstantMessageList;
    }
}
