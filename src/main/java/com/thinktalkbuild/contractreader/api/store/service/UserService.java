package com.thinktalkbuild.contractreader.api.store.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.thinktalkbuild.contractreader.api.store.model.User;
import com.thinktalkbuild.contractreader.api.store.repo.UserRepo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepo userRepo;

//    @Value("${env}")
//    private String env;

    private static String AUTH_PROVIDER = "google"; //TODO add other impls

    private String makeId(String subjectClaim) {
        return subjectClaim + "_" + AUTH_PROVIDER;
    }

    public Optional<User> findUser(String subjectClaim) throws Exception{
        return userRepo.findById(makeId(subjectClaim));
    }
//
//    private String users(){
//        return env + ":USERS";
//    }
//
//    public void addUser(String subjectClaim) throws Exception {
//        Firestore db = FirestoreOptions.getDefaultInstance().getService();
//        String id = subjectClaim + "_" + AUTH_PROVIDER;
//        DocumentReference docRef = db.collection(users()).document(id);
//        Map<String, Object> data = new HashMap<>();
//        data.put("subject", subjectClaim);
//        data.put("provider", AUTH_PROVIDER);
//        data.put("created", Timestamp.now());
//        //asynchronously write data
//        ApiFuture<WriteResult> result = docRef.set(data);
//        // ...
//        // result.get() blocks on response
//        System.out.println("Update time : " + result.get().getUpdateTime());
//
//        retrieveById();
//        retrieveAllDocuments();
//        runQuery();
//    }
//
//    public void retrieveAllDocuments() throws Exception {
//        Firestore db = FirestoreOptions.getDefaultInstance().getService();
//        // [START firestore_setup_dataset_read]
//        // asynchronously retrieve all users
//        ApiFuture<QuerySnapshot> query = db.collection(users()).get();
//        // ...
//        // query.get() blocks on response
//        QuerySnapshot querySnapshot = query.get();
//        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
//        for (QueryDocumentSnapshot document : documents) {
//            System.out.println("User: " + document.getId());
//        }
//        // [END firestore_setup_dataset_read]
//    }
//
//
//    public void runQuery() throws Exception {
//        Firestore db = FirestoreOptions.getDefaultInstance().getService();
//        ApiFuture<QuerySnapshot> query =
//                db.collection(users()).whereEqualTo("provider", "google").get();
//        QuerySnapshot querySnapshot = query.get();
//        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
//        log.info("documents: {}", documents.size());
//        for (QueryDocumentSnapshot document : documents) {
//            System.out.println("User: " + document.getId());
//        }
//    }
//
//    public void retrieveById() throws ExecutionException, InterruptedException {
//        Firestore db = FirestoreOptions.getDefaultInstance().getService();
//        DocumentReference docRef = db.collection(users()).document("104539204191009382087_google");
//        System.out.println("subject doc: " +docRef.getId());
//    }
}
