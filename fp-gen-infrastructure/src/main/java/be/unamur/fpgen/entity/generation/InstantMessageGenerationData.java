package be.unamur.fpgen.entity.generation;

import be.unamur.fpgen.entity.instant_message.SingleInstantMessageEntity;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Embeddable
public class InstantMessageGenerationData implements Serializable {
    @Serial
    private static final long serialVersionUID = 4455469519017267792L;

    private List<SingleInstantMessageEntity> singleInstantMessageList;

    // getters and setters
    @OneToMany(mappedBy = "singleInstantMessageGeneration")
    public List<SingleInstantMessageEntity> getSingleInstantMessageList() {
        return singleInstantMessageList;
    }

    public void setSingleInstantMessageList(List<SingleInstantMessageEntity> singleInstantMessageList) {
        this.singleInstantMessageList = singleInstantMessageList;
    }
}
