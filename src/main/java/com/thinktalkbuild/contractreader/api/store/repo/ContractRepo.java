package com.thinktalkbuild.contractreader.api.store.repo;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.thinktalkbuild.contractreader.api.store.model.ContractMetadata;
import com.thinktalkbuild.contractreader.api.store.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
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
        DocumentReference docRef = db.collection(contracts()).document();// firestore generates an ID
        Map<String, Object> data = new HashMap<>();
        data.put("name", newContract.getName());
        data.put("created", Timestamp.now());
        data.put("user", owner.getId());
        ApiFuture<WriteResult> result = docRef.set(data);
        log.info("Contract ID: {}", docRef.getId());
        return docRef.getId();
    }

    // TODO testcontainers dockerised local firestore so we can write some integration tests
    public List<ContractMetadata> getContractsForUser(User owner) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreOptions.getDefaultInstance().getService();
        ApiFuture<QuerySnapshot> query = db.collection(contracts()).whereEqualTo("user", owner.getId()).get();
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        log.info("documents: {}", documents.size());
        List<ContractMetadata> results = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            ContractMetadata result = new ContractMetadata();
            result.setId(document.getId());
            result.setName((String)document.get("name"));
            results.add(result);
        }
        return results;
    }

}

/* TODO
1. Handle dates
2. UI to display list
[3. jwt for different users for test
4. firestore docker for test]
 */

