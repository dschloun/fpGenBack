package be.unamur.fpgen.repository.interclocutor;

import be.unamur.fpgen.interlocutor.Interlocutor;
import be.unamur.fpgen.interlocutor.InterlocutorTypeEnum;
import be.unamur.fpgen.mapper.domainToJpa.InterlocutorDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.InterlocutorJpaToDomainMapper;
import be.unamur.fpgen.repository.GenerationRepository;
import be.unamur.fpgen.repository.InterlocutorRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Random;

/**
 * See the specifications in the {@link InterlocutorRepository} interface.
 */
@Repository
public class JpaInterlocutorRepository implements InterlocutorRepository {

    private final JpaInterlocutorRepositoryCRUD jpaInterlocutorRepositoryCRUD;
    private final Random random = new Random();

    public JpaInterlocutorRepository(JpaInterlocutorRepositoryCRUD jpaInterlocutorRepositoryCRUD) {
        this.jpaInterlocutorRepositoryCRUD = jpaInterlocutorRepositoryCRUD;
    }

    @Override
    public Interlocutor getRandomInterlocutorByType(InterlocutorTypeEnum type) {
        List<Interlocutor> interlocutorList = jpaInterlocutorRepositoryCRUD.findByInterlocutorTypeEnum(type).stream()
                .map(InterlocutorJpaToDomainMapper::map)
                .toList();
        // if interlocutor list is empty, create an interlocutor from the type
        if (interlocutorList.isEmpty()){
            return saveInterlocutor(Interlocutor.newBuilder()
                            .withType(type)
                            .build());
        } else {
            int randomIndex = random.nextInt(interlocutorList.size());
            return interlocutorList.get(randomIndex);
        }

    }

    @Override
    public Interlocutor getInterlocutorByType(InterlocutorTypeEnum type) {
        return jpaInterlocutorRepositoryCRUD.getInterlocutorEntityByInterlocutorTypeEnum(type).map(InterlocutorJpaToDomainMapper::map).orElse(null);
    }

    @Override
    public Interlocutor saveInterlocutor(Interlocutor interlocutor) {
        return InterlocutorJpaToDomainMapper.map(
                jpaInterlocutorRepositoryCRUD.save(
                        InterlocutorDomainToJpaMapper.mapForCreate(interlocutor)));
    }

    @Override
    public Interlocutor getGenuineInterlocutor1() {
        return jpaInterlocutorRepositoryCRUD.getInterlocutorEntityByInterlocutorTypeEnumAndNumber(InterlocutorTypeEnum.GENUINE, 1)
                .map(InterlocutorJpaToDomainMapper::map)
                .orElse(null);
    }

    @Override
    public Interlocutor getGenuineInterlocutor2() {
        return jpaInterlocutorRepositoryCRUD.getInterlocutorEntityByInterlocutorTypeEnumAndNumber(InterlocutorTypeEnum.GENUINE, 2)
                .map(InterlocutorJpaToDomainMapper::map)
                .orElse(null);
    }
}
