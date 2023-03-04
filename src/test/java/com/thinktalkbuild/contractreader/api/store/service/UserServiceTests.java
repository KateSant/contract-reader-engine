package com.thinktalkbuild.contractreader.api.store.service;

import com.thinktalkbuild.contractreader.api.store.model.User;
import com.thinktalkbuild.contractreader.api.store.repo.UserRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTests {
    @Autowired
    UserService service;

    @MockBean
    UserRepo repo;

    @Test
    public void givenUserExistsInStoreWithProviderPrefix_whenCallFindUserBySubject_findHer() throws Exception {
        when(repo.findById("ada_google")).thenReturn(Optional.of(makeAda()));
        Optional<User> u = service.findUser("ada");
        verify(repo, times(1)).findById("ada_google");
        assertEquals("ada", u.get().getSubject());
    }

    private User makeAda(){
        User ada = new User();
        ada.setId("ada");
        ada.setSubject("ada");
        return ada;
    }

    @Test
    public void givenUserNotExists_whenCallFindUser_empty() throws Exception {
        when(repo.findById("nobody_google")).thenReturn(Optional.empty());
        Optional<User> u = service.findUser("nobody");
        assertTrue(u.isEmpty());
    }

    @Test
    public void givenUserNotExists_whenCallInsertUserIfNotExists_thenInsertUser() throws Exception {
        when(repo.findById("newjoiner_google")).thenReturn(Optional.empty());
        service.findOrInsertUser("newjoiner");
        Mockito.verify(repo, times(1)).insertUser(any());
    }

    @Test
    public void givenUserExists_whenCallInsertUserIfNotExists_thenNotInsertUser() throws Exception {
        when(repo.findById("ada_google")).thenReturn(Optional.of(makeAda()));
        service.findOrInsertUser("ada");
        Mockito.verify(repo, times(0)).insertUser(any());
    }

}
