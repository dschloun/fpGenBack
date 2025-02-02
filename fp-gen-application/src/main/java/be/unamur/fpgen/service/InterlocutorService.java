package be.unamur.fpgen.service;

import be.unamur.fpgen.interlocutor.Interlocutor;
import be.unamur.fpgen.interlocutor.InterlocutorTypeEnum;
import be.unamur.fpgen.repository.InterlocutorRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Service class for Interlocutor
 */
@Service
public class InterlocutorService {

    private final InterlocutorRepository interlocutorRepository;

    public InterlocutorService(InterlocutorRepository interlocutorRepository) {
        this.interlocutorRepository = interlocutorRepository;
    }

    /**
     * Create a new Interlocutor
     * @param type the type of the interlocutor
     * @return the created Interlocutor
     */
    @Transactional
    public Interlocutor createInterlocutor(final InterlocutorTypeEnum type){
        return interlocutorRepository.saveInterlocutor(Interlocutor.newBuilder()
                .withType(type)
                .build());
    }

    /**
     * Get a random Interlocutor by type
     * @param type
     * @return a random Interlocutor
     */
    @Transactional
    public Interlocutor getRandomInterlocutorByType(final InterlocutorTypeEnum type){
        return interlocutorRepository.getRandomInterlocutorByType(type);
    }

    /**
     * Get an Interlocutor by type
     * @param type
     * @return an Interlocutor
     */
    @Transactional
    public Interlocutor getInterlocutorByType(final InterlocutorTypeEnum type){
        return interlocutorRepository.getInterlocutorByType(type);
    }

    /**
     * Get the genuine Interlocutor 1
     * @return the genuine Interlocutor 1
     */
    @Transactional
    public Interlocutor getGenuineInterlocutor1(){
        return interlocutorRepository.getGenuineInterlocutor1();
    }

    /**
     * Get the genuine Interlocutor 2
     * @return the genuine Interlocutor 2
     */
    @Transactional
    public Interlocutor getGenuineInterlocutor2(){
        return interlocutorRepository.getGenuineInterlocutor2();
    }
}
