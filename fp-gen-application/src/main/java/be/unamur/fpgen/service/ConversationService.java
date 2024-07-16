package be.unamur.fpgen.service;

import be.unamur.fpgen.conversation.Conversation;
import be.unamur.fpgen.generation.ConversationGeneration;
import be.unamur.fpgen.repository.ConversationRepository;
import be.unamur.model.ConversationBatchCreation;
import be.unamur.model.ConversationCreation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final SaveGenerationService saveGenerationService;

    public ConversationService(ConversationRepository conversationRepository, SaveGenerationService saveGenerationService) {
        this.conversationRepository = conversationRepository;
        this.saveGenerationService = saveGenerationService;
    }

    public List<Conversation> createConversationList(ConversationBatchCreation command) {
       // 0. for each
        command.getConversationCreationList().forEach(cc -> {
            // 1. create generation data
            final ConversationGeneration generation = saveGenerationService.createConversationGeneration(cc);

            // 2. prepare conversation list
            final List<Conversation> conversationList = new ArrayList<>();

            // 3. generate conversations
            //todo chat gpt api with prompt // return the x messages in json format, unmarshall, ...
            for(int i = 0; i < cc.getQuantity(); i++){
                conversationList.add(new Conversation());
            }
        });
    }

    private Conversation mockConversationGeneration(final ConversationCreation command){
        return Conversation.newBuilder()

    }
}
