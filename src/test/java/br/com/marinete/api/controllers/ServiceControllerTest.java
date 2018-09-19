package br.com.marinete.api.controllers;

import br.com.marinete.api.dtos.ServiceDto;
import br.com.marinete.api.entities.Marinete;
import br.com.marinete.api.entities.Service;
import br.com.marinete.api.entities.User;
import br.com.marinete.api.enums.StatusEnum;
import br.com.marinete.api.services.MarineteService;
import br.com.marinete.api.services.ServiceService;
import br.com.marinete.api.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
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

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceService serviceServ;

    @MockBean
    private MarineteService marineteService;

    @MockBean
    private UserService userService;

    private static final String URL_BASE = "/api/services/";
    public static final Long ID_USER = 1L;
    public static final Long ID_MARINETE = 1L;
    public static final Long ID_SERVICE = 1L;
    public static final String STATUS = StatusEnum.ACTIVE.name();
    public static final Date DATA = new Date();
    public static final int EVALUATION = 10;
    public static final BigDecimal PRICE = BigDecimal.valueOf(50);
    public static final String COMMENTS = "Nice Service!";
    public static final String LOCALIZATION = "Maceio, Alagoas";

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Test
    public void testSaveService() throws Exception{
        Service service = getServiceData();
        BDDMockito.given(this.userService.findUserById(Mockito.anyLong())).willReturn(Optional.of(new User()));
        BDDMockito.given(this.marineteService.findMarineteById(Mockito.anyLong())).willReturn(Optional.of(new Marinete()));
        BDDMockito.given(this.serviceServ.persist(Mockito.any(Service.class))).willReturn(service);

        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                .content(this.getJsonPost())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(ID_SERVICE))
                .andExpect(jsonPath("$.data.price").value(PRICE))
                .andExpect(jsonPath("$.data.status").value(STATUS))
                .andExpect(jsonPath("$.data.evaluation").value(EVALUATION))
                .andExpect(jsonPath("$.data.comments").value(COMMENTS))
                .andExpect(jsonPath("$.data.localization").value(LOCALIZATION))
                .andExpect(jsonPath("$.data.serviceDate").value(this.dateFormat.format(DATA)))
                .andExpect(jsonPath("$.data.marineteId").value(ID_MARINETE))
                .andExpect(jsonPath("$.data.userId").value(ID_USER))
                .andExpect(jsonPath("$.errors").isEmpty());
    }

    @Test
    public void testSaveServiceInvalidUser() throws Exception{
        BDDMockito.given(this.marineteService.findMarineteById(Mockito.anyLong())).willReturn(Optional.of(new Marinete()));
        BDDMockito.given(this.userService.findUserById(Mockito.anyLong())).willReturn(Optional.empty());


        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                .content(this.getJsonPost())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("User not found. Id not exists in database"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    public void testRemoveService() throws Exception{
        BDDMockito.given(this.serviceServ.findById(Mockito.anyLong())).willReturn(Optional.of(new Service()));

        mockMvc.perform(MockMvcRequestBuilders.delete(URL_BASE + ID_SERVICE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }



    private String getJsonPost() throws JsonProcessingException{

        ServiceDto serviceDto = new ServiceDto();
        serviceDto.setUserId(null);
        serviceDto.setPrice(PRICE);
        serviceDto.setStatus(STATUS);
        serviceDto.setEvaluation(EVALUATION);
        serviceDto.setComments(COMMENTS);
        serviceDto.setLocalization(LOCALIZATION);
        serviceDto.setServiceDate(this.dateFormat.format(DATA));
        serviceDto.setMarineteId(ID_MARINETE);
        serviceDto.setUserId(ID_USER);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());
        return mapper.writeValueAsString(serviceDto);

    }



    private Service getServiceData() {
        Service service = new Service();
        service.setId(ID_SERVICE);
        service.setPrice(PRICE);
        service.setStatus(StatusEnum.valueOf(STATUS));
        service.setEvaluation(EVALUATION);
        service.setComments(COMMENTS);
        service.setLocalization(LOCALIZATION);
        service.setServiceDate(DATA);
        service.setUser(new User());
        service.getUser().setId(ID_USER);
        service.setMarinete(new Marinete());
        service.getMarinete().setId(ID_USER);

        return service;
    }

}
