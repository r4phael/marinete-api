package br.com.marinete.api.repositories;


import br.com.marinete.api.entities.Marinete;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class MarineteRepositoryTest {

    @Autowired
    private MarineteRepository marineteRepository;

    private static final Integer CTPS = 12313;

    @Before
    public void beforeTest() throws Exception{
        Marinete marinete = new Marinete();
        marinete.setName("Luiza");
        marinete.setEmail("Luiza@Marinete.com.br");
        marinete.setCtps(CTPS);
        marinete.setEvaluation(10);
        this.marineteRepository.save(marinete);
    }


    @After
    public final void afterTest(){
        this.marineteRepository.deleteAll();;
    }


    @Test
    public void testFindByCtps(){
        Marinete marinete = this.marineteRepository.findByCtps(CTPS);

        assertEquals(CTPS, marinete.getCtps());


    }
}
