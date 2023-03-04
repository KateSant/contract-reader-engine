package com.thinktalkbuild.contractreader.api.store.service;

import com.thinktalkbuild.contractreader.api.store.model.ContractMetadata;
import com.thinktalkbuild.contractreader.api.store.model.User;
import com.thinktalkbuild.contractreader.api.store.repo.ContractRepo;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ContractServiceTests {
    @Autowired
    ContractService service;

    @MockBean
    ContractRepo repo;

    @MockBean
    UserService userService;


    @Test
    public void givenContractMetadata_whenInsertContractForUser_thenCallsRepoInsert() throws Exception {

        ContractMetadata myLittle = new ContractMetadata();
        myLittle.setName("My little contract");
        service.insertContractForUser(myLittle, "123");
        verify(repo, times(1)).insertContract(any(), any());
    }

    @Test
    public void givenContractMetadata_whenInsert_thenEnforcesUserIdAsForeignKey() throws Exception {

        ContractMetadata myLittle = new ContractMetadata();
        myLittle.setName("My little contract");
        User ada = new User();
        ada.setId("123_google");
        ada.setSubject("123");
        when(userService.findOrInsertUser("123")).thenReturn(ada);

        service.insertContractForUser(myLittle, "123");

        verify(userService).findOrInsertUser("123");
        verify(repo).insertContract(myLittle, ada);

    }


}
