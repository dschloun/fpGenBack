package be.unamur.fpgen.prompt.response;

import be.unamur.fpgen.prompt.response.conversation.ConversationResponse;
import be.unamur.fpgen.prompt.response.message.MessageResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ResponseFormatConverter {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static MessageResponse messageFromJson(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, MessageResponse.class);
    }

    public static ConversationResponse conversationFromJson(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, ConversationResponse.class);
    }
}
