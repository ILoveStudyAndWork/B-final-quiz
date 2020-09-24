package com.example.demo.controller;

import com.example.demo.domain.Trainer;
import com.example.demo.service.TrainerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TrainerController.class)
@AutoConfigureJsonTesters
class TrainerControllerTest {

    @MockBean
    private TrainerService trainerService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<Trainer> trainerJson;
    private Trainer trainer;
    @BeforeEach
    void setUp() {
        trainer = Trainer.builder()
                .id(1L)
                .name("trainer").build();
    }

    @Nested
    class addTrainer{

        @Nested
        class WhenTrainerValid{

            @Test
            void should_return_trainer() throws Exception {
                Trainer requestTrainer = Trainer.builder().name("trainer").build();
                when(trainerService.addTrainer(requestTrainer)).thenReturn(trainer);
                MockHttpServletResponse response = mockMvc.perform(post("/trainers")
                            .content(trainerJson.write(requestTrainer).getJson())
                            .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated())
                        .andReturn()
                        .getResponse();
                assertThat(response.getContentAsString()).isEqualTo(trainerJson.write(trainer).getJson());

            }
        }

        @Nested
        class WhenTrainerNotValid{

            @Test
            void should_return_bad_request() throws Exception {
                Trainer requestTrainer = Trainer.builder().name(null).build();
                when(trainerService.addTrainer(requestTrainer)).thenReturn(trainer);
                mockMvc.perform(post("/trainers")
                        .content(trainerJson.write(requestTrainer).getJson())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    class getAllTrainerWithoutGrouped{

        @Test
        void should_return_all_trainer_without_grouped_given_grouped_false() throws Exception {
            when(trainerService.getAllTrainerWithoutGrouped()).thenReturn(Collections.singletonList(trainer));
            mockMvc.perform(get("/trainers?grouped=false"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].name",is("trainer")))
                    .andExpect(jsonPath("$[0].id",is(1)))
                    .andExpect(jsonPath("$",hasSize(1)));


            verify(trainerService,times(1)).getAllTrainerWithoutGrouped();

        }

        @Test
        void should_return_null_given_grouped_true() throws Exception {
            when(trainerService.getAllTrainerWithoutGrouped()).thenReturn(Collections.emptyList());
            MockHttpServletResponse response = mockMvc.perform(get("/trainers?grouped=true"))
                    .andReturn()
                    .getResponse();
            assertThat(response.getContentLength()).isEqualTo(0);
            verify(trainerService,times(0)).getAllTrainerWithoutGrouped();
        }
    }
}
