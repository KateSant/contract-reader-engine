package com.thinktalkbuild.contractreader.api.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinktalkbuild.contractreader.api.store.model.ContractMetadata;
import com.thinktalkbuild.contractreader.api.store.repo.ContractRepo;
import com.thinktalkbuild.contractreader.api.store.service.ContractService;
import com.thinktalkbuild.contractreader.api.store.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.List;

import static java.time.LocalDate.now;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ContractControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    ContractRepo contractRepo;

    @MockBean
    private UserService userService;

    @MockBean
    private ContractService contractService;

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

    @Test
    void whenGetContracts_thenReturnsListOfContractMeta() throws Exception {

        ContractMetadata contractMeta = new ContractMetadata();
        contractMeta.setName("Bloggs reseller agreement");
        contractMeta.setStartDate(LocalDate.of(2023, Month.MARCH, 23));
        when(contractService.getContractsForUser(anyString()))
                .thenReturn(Collections.singletonList(contractMeta));

        mvc.perform(get("/contract")
                .with(jwt()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$[0].name").value("Bloggs reseller agreement"))
                .andExpect(jsonPath("$[0].startDate").value("2023-03-23"));
    }

    @Test
    void givenUser_whenGetContracts_thenReturnsOnlyUsersOwnContracts() throws Exception {

        ContractMetadata contractA = new ContractMetadata();
        contractA.setName("A contract");
        when(contractService.getContractsForUser("a"))
                .thenReturn(Collections.singletonList(contractA));

        ContractMetadata contractB = new ContractMetadata();
        contractB.setName("B contract");
        when(contractService.getContractsForUser("b"))
                .thenReturn(Collections.singletonList(contractB));

        mvc.perform(get("/contract")
                .with(jwt()
                        .jwt( jwt -> jwt.subject("b") )
                ))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$[0].name").value("B contract"))
                .andExpect(jsonPath("$.length()").value(1));
    }
}
