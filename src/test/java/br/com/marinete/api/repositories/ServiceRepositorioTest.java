package br.com.marinete.api.repositories;


import br.com.marinete.api.entities.Marinete;
import br.com.marinete.api.entities.Service;
import br.com.marinete.api.entities.User;
import br.com.marinete.api.enums.ProfileEnum;
import br.com.marinete.api.enums.StatusEnum;
import br.com.marinete.api.utils.PasswordUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ServiceRepositorioTest {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MarineteRepository marineteRepository;

    private Long userId;
    private Long marineteId;

    @Before
    public void beforeTests() throws Exception{
        Marinete marinete = this.marineteRepository.save(populateMarinete());
        User user = this.userRepository.save(populateUser());

        this.marineteId = marinete.getId();

        this.serviceRepository.save(populateService(marinete, user));
        this.serviceRepository.save(populateService(marinete, user));


    }

    @After
    public void afterTests() throws Exception{
        this.serviceRepository.deleteAll();
    }

    @Test
    public void testFindServicesByMarineteId(){
        List<Service> marineteList = this.serviceRepository.findByMarineteId(marineteId);

        assertEquals(2, marineteList.size());
    }

    @Test
    public void testFindServiceByMarineteIdPaged(){
        PageRequest page =PageRequest.of(0,10);
        Page<Service> servicePage = this.serviceRepository.findByMarineteId(marineteId, page);

        assertEquals(2, servicePage.getTotalElements());

    }


    private User populateUser() throws NoSuchAlgorithmException {
        User user = new User();
        user.setCpf("1234567890");
        user.setEmail("zezinho@hotmail.com");
        user.setName("Zezinho");
        user.setPassword(PasswordUtils.genBcrypt("123456"));
        user.setProfile(ProfileEnum.ROLE_USER);
        return user;
    }

    private Marinete populateMarinete(){
        Marinete marinete = new Marinete();
        marinete.setName("Marinete");
        marinete.setEmail("marinete@hotmail.com");
        marinete.setCtps(12356);
        marinete.setEvaluation(10);
        return marinete;
    }

    private Service populateService(Marinete marinete, User user){
        Service serv = new Service();
        serv.setComments("Nice service!");
        serv.setEvaluation(10);
        serv.setLocalization("UFAL");
        serv.setPrice(BigDecimal.valueOf(10));
        serv.setStatus(StatusEnum.ACTIVE);
        serv.setServiceDate(new Date());
        serv.setMarinete(marinete);
        serv.setUser(user);
        return serv;

    }




}
