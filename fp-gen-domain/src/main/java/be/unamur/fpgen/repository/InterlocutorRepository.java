package be.unamur.fpgen.repository;

import be.unamur.fpgen.interlocutor.Interlocutor;
import be.unamur.fpgen.interlocutor.InterlocutorTypeEnum;

public interface InterlocutorRepository {

    Interlocutor getRandomInterlocutorByType(InterlocutorTypeEnum type);

    Interlocutor getInterlocutorByType(InterlocutorTypeEnum type);

    Interlocutor saveInterlocutor(Interlocutor interlocutor);

    Interlocutor getGenuineInterlocutor1();

    Interlocutor getGenuineInterlocutor2();
}
