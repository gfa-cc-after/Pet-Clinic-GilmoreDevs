package com.greenfoxacademy.backend.services.vet;

import java.io.IOException;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.greenfoxacademy.backend.config.OllamaConfig;
import com.greenfoxacademy.backend.dtos.VetAnswerDTO;
import com.greenfoxacademy.backend.models.Owner;
import com.greenfoxacademy.backend.models.Pet;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.OllamaResult;
import io.github.ollama4j.utils.OptionsBuilder;
import io.github.ollama4j.utils.PromptBuilder;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VetServiceImpl implements VetService {

    private final String OLLAMA_MODEL = "llama3.1";
    private final OllamaConfig ollamaConfig;

    @Override
    public VetAnswerDTO generateVisitDocument(Pet subject) {
        if (subject == null) {
            subject = new Pet();
            subject.setPetName("Kutya the dog");
            var owner = new Owner();
            owner.setFirstName("Jozsef");
            owner.setLastName("Kovacs");

            subject.setPetOwner(owner);
            subject.setPetBirthDate(new Date());
            subject.setPetSex("male");
            subject.setId(1);
        }

        OllamaAPI ollamaAPI = new OllamaAPI(ollamaConfig.getOllamaUrl());
        ollamaAPI.setRequestTimeoutSeconds(100);

        PromptBuilder promptBuilder = new PromptBuilder()
                .addLine("You are an expert veterinarian and you need to write a visit document for a pet.")
                .addLine("Given the following prompt, write a maximum 10 lines of document")
                .addLine("Please write the document in English.")
                .addLine("Please don't write any bad words or inappropriate content.")
                .addLine("Please don't write anything bad news about the pet.")
                .addLine("Prompt:")
                .addLine("You are a veterinarian and you need to write a visit document for a pet.")
                .addLine("the following information is provided:")
                .addLine("Pet name: " + subject.getPetName())
                .addLine("Pet sex: " + subject.getPetSex())
                .addLine("Pet id: " + subject.getId())
                .addLine("Pet age: " + subject.getPetBirthDate())
                .addLine("Pet owner: " + subject.getPetOwner().getFirstName() + " "
                        + subject.getPetOwner().getLastName())
                .addSeparator()
                .add("""
                        Write a visit document for the pet from the point of view of the vet,
                            make it as professional as possible, around 100 words.
                            """);

        try {
            boolean raw = false;
            OllamaResult response;
            response = ollamaAPI.generate(OLLAMA_MODEL, promptBuilder.build(), raw,
                    new OptionsBuilder().build());
            return new VetAnswerDTO(response.getResponse().replaceAll("\n", " "));
        } catch (OllamaBaseException | IOException | InterruptedException e) {
            return null;
        }
    }

}
