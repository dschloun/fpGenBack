package be.unamur.fpgen.entity.generation.ongoing_generation;

import be.unamur.fpgen.entity.base.BaseUuidEntity;
import be.unamur.fpgen.generation.GenerationTypeEnum;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ongoing_generation")
public class OngoingGenerationEntity extends BaseUuidEntity {
    private GenerationTypeEnum type;
    private Set<OngoingGenerationItemEntity> itemList;
    private String author_trigram;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    public GenerationTypeEnum getType() {
        return type;
    }

    public void setType(GenerationTypeEnum type) {
        this.type = type;
    }

    @OneToMany(mappedBy = "ongoingGeneration")
    public Set<OngoingGenerationItemEntity> getItemList() {
        return itemList;
    }

    public void setItemList(Set<OngoingGenerationItemEntity> itemList) {
        this.itemList = itemList;
    }

    @Column(name = "author_trigram", nullable = false)
    public String getAuthor_trigram() {
        return author_trigram;
    }

    public void setAuthor_trigram(String author_trigram) {
        this.author_trigram = author_trigram;
    }
}
