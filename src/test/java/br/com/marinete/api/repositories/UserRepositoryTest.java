package br.com.marinete.api.repositories;



import br.com.marinete.api.entities.User;
import br.com.marinete.api.enums.ProfileEnum;
import br.com.marinete.api.utils.PasswordUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    public static final String EMAIL = "zezinho@hotmail.com";
    public static final String CPF = "123456789-0";

    private User populateUser(){
        User user = new User("Zezinho", EMAIL, PasswordUtils.genBcrypt("1234"), CPF, ProfileEnum.ROLE_USER);
        return user;
    }

    @Before
    public void beforeTest() throws Exception {
        User user = this.userRepository.save(populateUser());

    }

    @After
    public final void afterTest(){
        this.userRepository.deleteAll();
    }

    @Test
    public void testFindUserByEmail(){
        User user = this.userRepository.findByEmail(EMAIL);
        assertEquals(EMAIL, user.getEmail());
    }

    @Test
    public void testFindUserByCpfl(){
        User user = this.userRepository.findByCpf(CPF);
        assertEquals(CPF, user.getCpf());
    }

    @Test
    public void testFindUserByEmailOrCpf(){
        User user = this.userRepository.findByEmailOrCpf(EMAIL, CPF);
        assertNotNull(user);
    }

    @Test
    public void testFindUserByEmailOrCpfInvalid(){
        User user = this.userRepository.findByEmailOrCpf(CPF, "email@invalid.com");
        assertNotNull(user);
    }

    @Test
    public void testFindUserByEmailAndCpfInvalid(){
        User user = this.userRepository.findByEmailOrCpf("12313132", "email@invalid.com");
        assertNull(user);
    }










}
