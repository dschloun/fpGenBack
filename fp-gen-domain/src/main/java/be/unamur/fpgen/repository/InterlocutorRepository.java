package be.unamur.fpgen.repository;

import be.unamur.fpgen.interlocutor.Interlocutor;
import be.unamur.fpgen.interlocutor.InterlocutorTypeEnum;

public interface InterlocutorRepository {

    Interlocutor getRandomInterlocutorByType(InterlocutorTypeEnum type);

    Interlocutor saveInterlocutor(Interlocutor interlocutor);
}
