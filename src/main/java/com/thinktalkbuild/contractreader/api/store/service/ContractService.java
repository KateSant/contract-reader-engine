package com.thinktalkbuild.contractreader.api.store.service;

import com.thinktalkbuild.contractreader.api.store.model.ContractMetadata;
import com.thinktalkbuild.contractreader.api.store.model.User;
import com.thinktalkbuild.contractreader.api.store.repo.ContractRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class ContractService {

    @Autowired
    ContractRepo contractRepo;

    @Autowired
    private UserService userService;


    public ContractMetadata insertContractForUser(ContractMetadata contract, String subject) throws Exception{
        User user = userService.findOrInsertUser(subject);
        log.info("Inserted or found user: {}", user);
        String id = contractRepo.insertContract(contract, user);
        contract.setId(id);
        return contract;
    }

    public List<ContractMetadata> getContractsForUser(String subject) throws Exception{
        User user = userService.findOrInsertUser(subject);
        log.info("Inserted or found user: {}", user);
        return contractRepo.getContractsForUser(user);
    }

}
