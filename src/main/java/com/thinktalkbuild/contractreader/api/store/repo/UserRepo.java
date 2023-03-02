package com.thinktalkbuild.contractreader.api.store.repo;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
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
public class UserRepo {

    @Value("${env}")
    private String env;

    private String users(){
        return env + ":USERS";
    }


    public Optional<User> findById(String id) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreOptions.getDefaultInstance().getService();
        DocumentReference docRef = db.collection(users()).document(id);
        DocumentSnapshot doc = docRef.get().get();
        log.info("[{}] Exists? [{}]", id, doc.exists());
        if(doc.exists()) {
            User u = new User();
            u.setId(docRef.getId());

            u.setSubject((String) doc.get("subject"));
            u.setProvider((String) doc.get("provider"));
            return Optional.of(u);
        }else{
            return Optional.empty();
        }
    }

    public void insertUser(User newUser) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreOptions.getDefaultInstance().getService();
        DocumentReference docRef = db.collection(users()).document(newUser.getId());
        Map<String, Object> data = new HashMap<>();
        data.put("subject", newUser.getSubject());
        data.put("provider", newUser.getProvider());
        data.put("created", Timestamp.now());
        ApiFuture<WriteResult> result = docRef.set(data);
        System.out.println("Update time : " + result.get().getUpdateTime());
    }

}
