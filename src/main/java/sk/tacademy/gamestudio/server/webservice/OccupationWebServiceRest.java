package sk.tacademy.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tacademy.gamestudio.entity.Occupation;
import sk.tacademy.gamestudio.service.OccupationService;

import java.util.List;

@RestController
@RequestMapping("/api/occupation")
public class OccupationWebServiceRest {
    @Autowired
    private OccupationService occupationService;

    @GetMapping
    public List<Occupation> getOccupations() {
        return occupationService.getOccupations();
    }

    @PostMapping
    public void addOccupation(@RequestBody Occupation occupation) {
        occupationService.addOccupation(occupation);
    }
}
