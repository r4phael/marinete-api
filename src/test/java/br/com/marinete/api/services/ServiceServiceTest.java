package br.com.marinete.api.services;

import br.com.marinete.api.entities.Service;
import br.com.marinete.api.repositories.ServiceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")

public class ServiceServiceTest {
    @MockBean
    private ServiceRepository serviceRepository;

    @Autowired
    private ServiceService serviceService;

    @Before
    public void configureSetup() throws Exception{
        BDDMockito.given(this.serviceRepository.findByMarineteId(Mockito.anyLong(), Mockito.any(PageRequest.class)))
                .willReturn(new PageImpl<Service>(new ArrayList<Service>()));
        BDDMockito.given(this.serviceRepository.findById(Mockito.anyLong())).willReturn(Optional.of(new Service()));
        BDDMockito.given(this.serviceRepository.save(Mockito.any(Service.class))).willReturn(new Service());
    }

    @Test
    public void testFindServiceByMarineteId(){
        Page<Service> service = this.serviceService.findByMarineteId(1, PageRequest.of(0,10));
        assertNotNull(service);
    }

    @Test
    public void testFindServiceById() {
        Optional<Service> service = this.serviceService.findById(1L);
        assertTrue(service.isPresent());
    }

    @Test
    public void testPersistService() {
        Service service = this.serviceService.persist(new Service());
        assertNotNull(service);
    }


}
