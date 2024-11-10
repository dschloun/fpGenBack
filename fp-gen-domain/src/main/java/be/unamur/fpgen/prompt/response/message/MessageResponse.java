package be.unamur.fpgen.prompt.response.message;

import java.util.List;

public class MessageResponse {
    private List<Generation> generations;

    public List<Generation> getGenerations() {
        return generations;
    }

    public void setGenerations(List<Generation> generations) {
        this.generations = generations;
    }
}
