package well.pharmacy.open_pharmacy.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import well.pharmacy.open_pharmacy.dto.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class FindNumberOfSurgeriesServiceTest {

    @Autowired
    private FindNumberOfSurgeriesService service;

    @Test
    void samePostcode() {
        Pharmacy pharmacy = Pharmacy.builder().postcode(new PostCode("M5 3GT")).build();
        Pharmacy pharmacy1 = Pharmacy.builder().postcode(new PostCode("M5 4GT")).build();
        Pharmacy pharmacy2 = Pharmacy.builder().postcode(new PostCode("M5 5GT")).build();
        Pharmacy pharmacy3 = Pharmacy.builder().postcode(new PostCode("M5 3GU")).build();
        Pharmacy pharmacy4 = Pharmacy.builder().postcode(new PostCode("M5 3GP")).build();

        Data data = new Data();
        data.setList(List.of(pharmacy, pharmacy1, pharmacy2, pharmacy3, pharmacy4));
        FindNumberOfSurgeriesRequest findNumberOfSurgeriesRequest = new FindNumberOfSurgeriesRequest();
        findNumberOfSurgeriesRequest.setData(data);

        List<FindNumberOfSurgeriesResponse> result = service.findNumberOfSurgeries(findNumberOfSurgeriesRequest, 0).collect(Collectors.toList());
        assertEquals(1, result.size());
        assertEquals(5, result.get(0).getNumberOfSurgeries());
        assertEquals("100.00%", result.get(0).getPercentageOfSurgeries());
        assertEquals("M5", result.get(0).getPostcode());

    }

    @Test
    void differentPostcodes() {
        Pharmacy pharmacy = Pharmacy.builder().postcode(new PostCode("M5 3GT")).build();
        Pharmacy pharmacy1 = Pharmacy.builder().postcode(new PostCode("M5 4GT")).build();
        Pharmacy pharmacy2 = Pharmacy.builder().postcode(new PostCode("M3 5GT")).build();
        Pharmacy pharmacy3 = Pharmacy.builder().postcode(new PostCode("M3 3GU")).build();
        Pharmacy pharmacy4 = Pharmacy.builder().postcode(new PostCode("M4 3GP")).build();

        Data data = new Data();
        data.setList(List.of(pharmacy, pharmacy1, pharmacy2, pharmacy3, pharmacy4));
        FindNumberOfSurgeriesRequest findNumberOfSurgeriesRequest = new FindNumberOfSurgeriesRequest();
        findNumberOfSurgeriesRequest.setData(data);

        List<FindNumberOfSurgeriesResponse> result = service.findNumberOfSurgeries(findNumberOfSurgeriesRequest, 0).collect(Collectors.toList());
        assertEquals(3, result.size());
        assertEquals(2, result.get(0).getNumberOfSurgeries());
        assertEquals("40.00%", result.get(0).getPercentageOfSurgeries());
        assertEquals("M3", result.get(0).getPostcode());
        assertEquals(2, result.get(1).getNumberOfSurgeries());
        assertEquals("40.00%", result.get(1).getPercentageOfSurgeries());
        assertEquals("M5", result.get(1).getPostcode());
        assertEquals(1, result.get(2).getNumberOfSurgeries());
        assertEquals("20.00%", result.get(2).getPercentageOfSurgeries());
        assertEquals("M4", result.get(2).getPostcode());

    }


    @Test
    void noMCRPostcode() {
        Pharmacy pharmacy = Pharmacy.builder().postcode(new PostCode("M58 3GT")).build();

        Data data = new Data();
        data.setList(List.of(pharmacy));
        FindNumberOfSurgeriesRequest findNumberOfSurgeriesRequest = new FindNumberOfSurgeriesRequest();
        findNumberOfSurgeriesRequest.setData(data);

        List<FindNumberOfSurgeriesResponse> result = service.findNumberOfSurgeries(findNumberOfSurgeriesRequest, 0).collect(Collectors.toList());
        assertTrue(result.isEmpty(), "Result is empty");
    }

}
