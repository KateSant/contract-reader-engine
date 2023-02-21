package com.thinktalkbuild.contractreader.api.store.service;

import com.thinktalkbuild.contractreader.api.store.model.User;
import com.thinktalkbuild.contractreader.api.store.repo.UserRepo;
import org.junit.jupiter.api.Test;
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
        User ada = new User();
        ada.setId("ada");
        ada.setSubject("ada");
        when(repo.findById("ada_google")).thenReturn(Optional.of(ada));
        Optional<User> u = service.findUser("ada");
        verify(repo, times(1)).findById("ada_google");
        assertEquals("ada", u.get().getSubject());
    }

    @Test
    public void givenUserNotExists_whenCallFindUser_empty() throws Exception {
        when(repo.findById("bea_google")).thenReturn(Optional.empty());
        Optional<User> u = service.findUser("bea");
        assertTrue(u.isEmpty());
    }

}
