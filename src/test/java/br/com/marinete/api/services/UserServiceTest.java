package br.com.marinete.api.services;


import br.com.marinete.api.entities.User;
import br.com.marinete.api.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private static final String CPF = "123456789-0";
    private static final String EMAIL = "zezinho@hotmail.com";

    @Before
    public void configureSetup() throws Exception{
        BDDMockito.given(this.userRepository.findByCpf(Mockito.anyString())).willReturn(new User());
        BDDMockito.given(this.userRepository.findByEmail(Mockito.anyString())).willReturn(new User());
        BDDMockito.given(this.userRepository.save(Mockito.any(User.class))).willReturn(new User());
        BDDMockito.given(this.userRepository.findById(Mockito.anyLong())).willReturn(Optional.of(new User()));
    }

    @Test
    public void testFindMarineteByCpf(){
        Optional<User> user = this.userService.findByCpf(CPF);
        assertTrue(user.isPresent());
    }

    @Test
    public void testFindMarineteByEmail(){
        Optional<User> user = this.userService.findByEmail(EMAIL);
        assertTrue(user.isPresent());
    }

    @Test
    public void testPersistMarinete(){
        User user = this.userService.persist(new User());
        assertNotNull(user);

    }

    @Test
    public void testFindUserById(){
        Optional<User> user = this.userService.findUserById(1L);
        assertTrue(user.isPresent());
    }


}
