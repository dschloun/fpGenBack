package be.unamur.fpgen.entity.author;

import be.unamur.fpgen.entity.base.BaseUuidEntity;
import be.unamur.fpgen.entity.dataset.DatasetEntity;
import be.unamur.fpgen.entity.generation.GenerationEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity(name = "author")
public class AuthorEntity extends BaseUuidEntity {

    // members
    private String lastName;
    private String firstName;
    private String trigram;
    private String organization;
    private String function;
    private String email;
    private String phoneNumber;
    private Set<GenerationEntity> generationList;
    private Set<DatasetEntity> datasetList;

    // getters and setters

    @Column(name = "lastname", nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "firstname", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
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
}
