package br.com.marinete.api.services;


import br.com.marinete.api.entities.Marinete;
import br.com.marinete.api.repositories.MarineteRepository;
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
public class MarineteServiceTest {

    @MockBean
    private MarineteRepository marineteRepository;

    @Autowired
    private MarineteService marineteService;

    private static final int CTPS = 1234567891;

    @Before
    public void configureSetup() throws Exception{
        BDDMockito.given(this.marineteRepository.findByCtps(Mockito.anyInt())).willReturn(new Marinete());
        BDDMockito.given(this.marineteRepository.save(Mockito.any(Marinete.class))).willReturn(new Marinete());
    }

    @Test
    public void testFindMarineteByCtps(){
        Optional<Marinete> marinete = this.marineteService.findByCtps(CTPS);
        assertTrue(marinete.isPresent());
    }

    @Test
    public void testPersistMarinete(){
        Marinete marinete = this.marineteService.persist(new Marinete());
        assertNotNull(marinete);

    }


}
