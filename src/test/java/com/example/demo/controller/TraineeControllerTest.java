package com.example.demo.controller;

import com.example.demo.domain.Trainee;
import com.example.demo.service.TraineeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TraineeController.class)
@AutoConfigureJsonTesters
class TraineeControllerTest {
    @MockBean
    private TraineeService traineeService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<Trainee> traineeJson;


    private Trainee trainee;
    @BeforeEach
    void setUp() {

        // todo
        trainee = Trainee.builder()
                .id(1L)
                .email("email")
                .name("学员")
                .grouped(false)
                .office("office1")
                .zoomId("zoom 999")
                .build();
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(traineeService);
    }

    @Nested
    class getAllTraineeWithoutGrouped{
        @Test
        void should_return_all_trainee_without_grouped_given_grouped_false() throws Exception {
            when(traineeService.getAllTraineeWithoutGrouped()).thenReturn(Collections.singletonList(trainee));
            mockMvc.perform(get("/trainees?grouped=false"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].name",is("学员")))
                    .andExpect(jsonPath("$[0].email",is("email")))
                    .andExpect(jsonPath("$[0].office",is("office1")))
                    .andExpect(jsonPath("$[0].zoomId",is("zoom 999")))
                    .andExpect(jsonPath("$[0].id",is(1)))
                    .andExpect(jsonPath("$",hasSize(1)));

            verify(traineeService,times(1)).getAllTraineeWithoutGrouped();
        }

        @Test
        void should_return_null_given_grouped_true() throws Exception {
            when(traineeService.getAllTraineeWithoutGrouped()).thenReturn(Collections.emptyList());
            MockHttpServletResponse response = mockMvc.perform(get("/trainees?grouped=true"))
                    .andReturn()
                    .getResponse();
            assertThat(response.getContentLength()).isEqualTo(0);
            verify(traineeService,times(0)).getAllTraineeWithoutGrouped();
        }
    }

}
