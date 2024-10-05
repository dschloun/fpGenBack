package be.unamur.fpgen.service;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.context.UserContextHolder;
import be.unamur.fpgen.generation.GenerationTypeEnum;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGeneration;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationItem;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationStatus;
import be.unamur.fpgen.repository.OngoingGenerationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.UUID;

@Service
public class OngoingGenerationService {
    private final AuthorService authorService;
    private final OngoingGenerationRepository ongoingGenerationRepository;

    public OngoingGenerationService(AuthorService authorService, OngoingGenerationRepository ongoingGenerationRepository) {
        this.authorService = authorService;
        this.ongoingGenerationRepository = ongoingGenerationRepository;
    }

    @Transactional
    public OngoingGeneration createOngoingGeneration(GenerationTypeEnum type) {
        final Author author = authorService.getAuthorByTrigram(UserContextHolder.getContext().getTrigram());
        return ongoingGenerationRepository.save(OngoingGeneration.newBuilder()
                .withType(type)
                .withAuthor(author)
                .withStatus(OngoingGenerationStatus.ONGOING)
                .build());
    }

    @Transactional
    public void addItemList(OngoingGeneration ongoingGeneration, List<OngoingGenerationItem> itemList) {
        ongoingGenerationRepository.addItemList(ongoingGeneration, itemList);
    }

    @Transactional
    public void updateStatus(OngoingGeneration ongoingGeneration, OngoingGenerationStatus status) {
        ongoingGenerationRepository.updateStatus(ongoingGeneration, status);
    }

    @Transactional
    public OngoingGeneration getOngoingGenerationById(UUID id) {
        return ongoingGenerationRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void deleteOngoingGenerationById(UUID id) {
        ongoingGenerationRepository.deleteById(id);
    }
}
