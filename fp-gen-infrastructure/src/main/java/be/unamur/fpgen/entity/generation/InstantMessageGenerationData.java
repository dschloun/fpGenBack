package be.unamur.fpgen.entity.generation;

import be.unamur.fpgen.entity.dataset.InstantMessageDatasetEntity;
import be.unamur.fpgen.entity.instant_message.InstantMessageEntity;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Embeddable
public class InstantMessageGenerationData implements Serializable {
    @Serial
    private static final long serialVersionUID = 4455469519017267792L;

    private List<InstantMessageEntity> instantMessageList;
    private InstantMessageDatasetEntity instantMessageDataset;

    // getters and setters
    @OneToMany(mappedBy = "instantMessageGeneration")
    public List<InstantMessageEntity> getInstantMessageList() {
        return instantMessageList;
    }

    public void setInstantMessageList(List<InstantMessageEntity> instantMessageList) {
        this.instantMessageList = instantMessageList;
    }

    @ManyToOne
    @JoinColumn(name = "dataset_id")
    public InstantMessageDatasetEntity getInstantMessageDataset() {
        return instantMessageDataset;
    }

    public void setInstantMessageDataset(InstantMessageDatasetEntity instantMessageDataset) {
        this.instantMessageDataset = instantMessageDataset;
    }
}
