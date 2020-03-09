package za.co.discovery.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.discovery.model.ClientDTO;
import za.co.discovery.service.ClientService;

@RestController
//@RequestMapping("/internal/api")
public class ClientResource {

    @Autowired
    private ClientService clientService;

   // @GetMapping("/retrieve/client")
    public ClientDTO retrieveClient(Integer clientID) {
        return clientService.retrieveClient(clientID);
    }
}
