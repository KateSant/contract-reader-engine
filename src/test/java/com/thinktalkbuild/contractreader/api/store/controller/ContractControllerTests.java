package com.thinktalkbuild.contractreader.api.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinktalkbuild.contractreader.api.store.model.ContractMetadata;
import com.thinktalkbuild.contractreader.api.store.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ContractControllerTests {

    @Autowired
    private MockMvc mvc;


    @Test
    void whenPostToContractService_withToken_andWithJson_thenSuccess() throws Exception {

        ContractMetadata contract = new ContractMetadata();
        contract.setName("foo");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(contract);
        mvc.perform(post("/contract")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .with(jwt()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(200));
    }
}
