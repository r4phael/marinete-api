package br.com.marinete.api.controllers;

import br.com.marinete.api.entities.Marinete;
import br.com.marinete.api.services.MarineteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MarineteControllerTest {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private MarineteService marineteService;

    private static final String FIND_MARINETE_BY_CTPS_URL = "/api/marinete/ctps/";
    private static final Long ID = 1L;
    private static final Integer CTPS = 1231313;
    private static final String EMAIL = "marinete@test.com.br";
    private static final String NAME = "Marinete da Silva";
    private static final Integer EVAL = 10 ;


    @Test
    public void findByCtpsInvalid() throws Exception{
        BDDMockito.given(this.marineteService.findByCtps(Mockito.anyInt())).willReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.get(FIND_MARINETE_BY_CTPS_URL + CTPS).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("Marinete not found by CTPS:  "+ CTPS));


    }

    @Test
    public void findByCtpsValid() throws Exception{
        BDDMockito.given(this.marineteService.findByCtps(Mockito.anyInt())).willReturn(Optional.of(this.getMarineteData()));

        mvc.perform(MockMvcRequestBuilders.get(FIND_MARINETE_BY_CTPS_URL + CTPS)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(ID))
                .andExpect(jsonPath("$.data.name",equalTo(NAME)))
                .andExpect(jsonPath("$.data.ctps").value(CTPS))
                .andExpect(jsonPath("$.data.email",equalTo(EMAIL)))
                .andExpect(jsonPath("$.errors").isEmpty());


    }

    public Marinete getMarineteData(){
        Marinete marinete = new Marinete();
        marinete.setId(ID);
        marinete.setEmail(EMAIL);
        marinete.setCtps(CTPS);
        marinete.setName(NAME);
        marinete.setEvaluation(EVAL);

        return marinete;
    }



}
