package be.unamur.fpgen.entity.generation;

import be.unamur.fpgen.entity.dataset.InstantMessageDatasetEntity;
import be.unamur.fpgen.entity.instant_message.InstantMessageEntity;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Embeddable
public class InstantMessageGenerationData implements Serializable {
    @Serial
    private static final long serialVersionUID = 4455469519017267792L;

    private List<InstantMessageEntity> instantMessageList;
    private Set<InstantMessageDatasetEntity> instantMessageDatasetList;

    // getters and setters
    @OneToMany(mappedBy = "instantMessageGeneration")
    public List<InstantMessageEntity> getInstantMessageList() {
        return instantMessageList;
    }

    public void setInstantMessageList(List<InstantMessageEntity> instantMessageList) {
        this.instantMessageList = instantMessageList;
    }

    @ManyToMany(mappedBy = "instantMessageGenerationList")
    public Set<InstantMessageDatasetEntity> getInstantMessageDatasetList() {
        return instantMessageDatasetList;
    }

    public void setInstantMessageDatasetList(Set<InstantMessageDatasetEntity> instantMessageDatasetList) {
        this.instantMessageDatasetList = instantMessageDatasetList;
    }
}
