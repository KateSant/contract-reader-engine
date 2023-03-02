package com.thinktalkbuild.contractreader.api.store.service;

import com.thinktalkbuild.contractreader.api.store.model.User;
import com.thinktalkbuild.contractreader.api.store.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepo userRepo;

    private static String AUTH_PROVIDER = "google"; //Can add other auth providers in the future

    private String makeId(String subjectClaim) {
        return subjectClaim + "_" + AUTH_PROVIDER;
    }

    public Optional<User> findUser(String subjectClaim) throws Exception{
        return userRepo.findById(makeId(subjectClaim));
    }

    public User insertUserIfNotExists(String subjectClaim) throws Exception{
        Optional<User> found = userRepo.findById(makeId(subjectClaim));
        if(found.isPresent()){
            log.info("Found...");
            return found.get();
        }
        log.info("Not found so inserting...");
        User newUser = new User();
        newUser.setSubject(subjectClaim);
        newUser.setProvider(AUTH_PROVIDER);
        newUser.setId(makeId(subjectClaim));
        userRepo.insertUser(newUser);
        return newUser;
    }

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

}
