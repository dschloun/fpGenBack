package be.unamur.fpgen.service;

import be.unamur.fpgen.repository.PromptRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PromptService {
    private final AuthorService authorService;
    private final PromptRepository promptRepository;

    public PromptService(AuthorService authorService, PromptRepository promptRepository) {
        this.authorService = authorService;
        this.promptRepository = promptRepository;
    }

    @Transactional
    public Prompt create
}
