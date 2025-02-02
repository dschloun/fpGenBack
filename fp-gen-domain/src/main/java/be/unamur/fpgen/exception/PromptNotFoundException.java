package be.unamur.fpgen.exception;

import java.util.UUID;

/**
 * Exception thrown when a prompt is not found
 */
public class PromptNotFoundException extends NotFoundException{

    private static final String FPGEN_CODE = "FP_GEN_PROMPT_NOT_FOUND";
    private static String NOT_FOUND_BY_ID = "Could not find prompt with id: %s";
    private static String NOT_FOUND_BY_TYPE_VERSION = "Could not find prompt with type % and version: %s";
    private static String NOT_FOUND_BY_DEFAULT_PROMPT = "Could not find default prompt";


    public PromptNotFoundException(String code, String message) {
        super(code, message);
    }

    public static PromptNotFoundException withId(final UUID id){
        final String message = String.format(NOT_FOUND_BY_ID, id);
        return new PromptNotFoundException(FPGEN_CODE, message);
    }

    public static PromptNotFoundException withTypeAndVersion(final String type, final Integer version){
        final String message = String.format(NOT_FOUND_BY_TYPE_VERSION, type, version);
        return new PromptNotFoundException(FPGEN_CODE, message);
    }

    public static PromptNotFoundException withDefaultPrompt(){
        return new PromptNotFoundException(FPGEN_CODE, NOT_FOUND_BY_DEFAULT_PROMPT);
    }

}
