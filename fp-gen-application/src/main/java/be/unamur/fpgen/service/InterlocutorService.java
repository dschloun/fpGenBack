package be.unamur.fpgen.service;

import be.unamur.fpgen.interlocutor.Interlocutor;
import be.unamur.fpgen.interlocutor.InterlocutorTypeEnum;
import be.unamur.fpgen.repository.InterlocutorRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class InterlocutorService {

    private final InterlocutorRepository interlocutorRepository;

    public InterlocutorService(InterlocutorRepository interlocutorRepository) {
        this.interlocutorRepository = interlocutorRepository;
    }

    @Transactional
    public Interlocutor createInterlocutor(final InterlocutorTypeEnum type){
        return interlocutorRepository.saveInterlocutor(Interlocutor.newBuilder()
                .withType(type)
                .build());
    }

    @Transactional
    public Interlocutor getRandomInterlocutorByType(final InterlocutorTypeEnum type){
        return interlocutorRepository.getRandomInterlocutorByType(type);
    }
}
