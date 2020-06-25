package well.pharmacy.open_pharmacy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import well.pharmacy.open_pharmacy.dto.FindNumberOfSurgeriesRequest;
import well.pharmacy.open_pharmacy.dto.FindNumberOfSurgeriesResponse;
import well.pharmacy.open_pharmacy.service.FindNumberOfSurgeriesService;

import javax.validation.Valid;
import java.util.stream.Stream;

@RestController
@RequestMapping("openPharmacy")
public class OpenPharmacyController {

    private final FindNumberOfSurgeriesService findNumberOfSurgeriesService;

    @Autowired
    public OpenPharmacyController(FindNumberOfSurgeriesService findNumberOfSurgeriesService) {
        this.findNumberOfSurgeriesService = findNumberOfSurgeriesService;
    }

    @PostMapping("{topOfSurgeries}")
    public Stream<FindNumberOfSurgeriesResponse> findTopNumberOfSurgeries(@Valid @RequestBody FindNumberOfSurgeriesRequest findNumberOfSurgeriesRequest, @PathVariable Integer topOfSurgeries) {
        return findNumberOfSurgeriesService.findNumberOfSurgeries(findNumberOfSurgeriesRequest, topOfSurgeries);
    }

    @PostMapping
    public Stream<FindNumberOfSurgeriesResponse> findNumberOfSurgeries(@Valid @RequestBody FindNumberOfSurgeriesRequest findNumberOfSurgeriesRequest) {
        return findNumberOfSurgeriesService.findNumberOfSurgeries(findNumberOfSurgeriesRequest, 0);
    }


}