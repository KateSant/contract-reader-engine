package com.thinktalkbuild.contractreader.api.store.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.WriteResult;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class UserService {

    private static String AUTH_PROVIDER = "google"; //TODO add other impls

    public void addUser(String subjectClaim) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreOptions.getDefaultInstance().getService();
        String id = subjectClaim + "_" + AUTH_PROVIDER;
        DocumentReference docRef = db.collection("users").document(id);
        Map<String, Object> data = new HashMap<>();
        data.put("subject", subjectClaim);
        data.put("provider", AUTH_PROVIDER);
        data.put("created", Timestamp.now());
        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(data);
        // ...
        // result.get() blocks on response
        System.out.println("Update time : " + result.get().getUpdateTime());
    }
}
