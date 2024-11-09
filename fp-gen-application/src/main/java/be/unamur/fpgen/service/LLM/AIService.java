package be.unamur.fpgen.service.LLM;

import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.prompt.Prompt;
import be.unamur.fpgen.prompt.response.ResponseFormatConverter;
import be.unamur.fpgen.prompt.response.message.MessageResponse;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import dev.langchain4j.model.output.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class AIService {
    @Value("${open_ai_key}")
    private String openaiApiKey;

    private static final String MESSAGE_FORMAT_PATH = "../../../../resources/promptChatGpt/message_format.json";

    public List<String> generateMessages(MessageTopicEnum topic, Integer maxInteractionNumber, Integer quantity, Prompt prompt) throws IOException {
        // Load message format from JSON file
        String formatJson = new String(Files.readAllBytes(Paths.get(MESSAGE_FORMAT_PATH)));

        // define the model
        final ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey(openaiApiKey)
                .modelName(OpenAiChatModelName.GPT_4_O_MINI)
                .responseFormat(formatJson)
                .strictJsonSchema(true)
                .build();

        final SystemMessage systemMessage = SystemMessage.from(
                prompt.replacePlaceholder(quantity, maxInteractionNumber, topic)
        );

        final UserMessage userMessage = UserMessage.from(
                TextContent.from(prompt.getSystemPrompt())
        );

        final Response<AiMessage> response = model.generate(systemMessage, userMessage);

        // convert response into response object
        final MessageResponse messageResponse = ResponseFormatConverter.messageFromJson(response.content().text());


    }
}
