package be.unamur.fpgen.repository;

import be.unamur.fpgen.interlocutor.Interlocutor;
import be.unamur.fpgen.interlocutor.InterlocutorTypeEnum;

/**
 * Repository for Interlocutor
 */
public interface InterlocutorRepository {

    /**
     * Get a random interlocutor by type
     * @param type
     * @return an interlocutor
     */
    Interlocutor getRandomInterlocutorByType(InterlocutorTypeEnum type);

    /**
     * Get an interlocutor by type
     * @param type
     * @return an interlocutor
     */
    Interlocutor getInterlocutorByType(InterlocutorTypeEnum type);

    /**
     * Save an interlocutor
     * @param interlocutor
     * @return the saved interlocutor
     */
    Interlocutor saveInterlocutor(Interlocutor interlocutor);

    /**
     * Get genuine interlocutor 1 (two genuine interlocutors in DB)
     * @return interlocutor 1
     * useful when generating genuine conversation
     */
    Interlocutor getGenuineInterlocutor1();

    /**
     * Get genuine interlocutor 2 (two genuine interlocutors in DB)
     * @return interlocutor 2
     * useful when generating genuine conversation
     */
    Interlocutor getGenuineInterlocutor2();
}
