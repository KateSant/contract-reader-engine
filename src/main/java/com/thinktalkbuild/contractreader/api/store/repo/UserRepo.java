package com.thinktalkbuild.contractreader.api.store.repo;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.thinktalkbuild.contractreader.api.store.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Component
public class UserRepo {

    @Value("${env}")
    private String env;

    private String users(){
        return env + ":USERS";
    }

    public Optional<User> findById(String id) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreOptions.getDefaultInstance().getService();
        DocumentReference docRef = db.collection(users()).document(id);
        User u = new User();
        u.setId(docRef.getId());
        DocumentSnapshot doc = docRef.get().get();
        u.setSubject((String)doc.get("subject"));
        u.setProvider((String)doc.get("provider"));
        return Optional.of(u);
    }
}
