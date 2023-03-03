package com.thinktalkbuild.contractreader.api.store.repo;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.thinktalkbuild.contractreader.api.store.model.ContractMetadata;
import com.thinktalkbuild.contractreader.api.store.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Component
@Slf4j
public class ContractRepo {

    @Value("${env}")
    private String env;

    private String contracts(){
        return env + ":CONTRACTS";
    }


    public String insertContract(ContractMetadata newContract, User owner) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreOptions.getDefaultInstance().getService();
        DocumentReference docRef = db.collection(contracts()).document();// if we don't name it, will firestore generate an ID?
        Map<String, Object> data = new HashMap<>();
        data.put("name", newContract.getName());
        data.put("created", Timestamp.now());
        data.put("user", owner.getId());
        ApiFuture<WriteResult> result = docRef.set(data);
        log.info("Contract ID: {}", docRef.getId());
        return docRef.getId();
    }

}
