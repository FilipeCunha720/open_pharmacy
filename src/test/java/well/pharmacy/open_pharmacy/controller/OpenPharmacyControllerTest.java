package well.pharmacy.open_pharmacy.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import well.pharmacy.open_pharmacy.controllers.OpenPharmacyController;
import well.pharmacy.open_pharmacy.dto.FindNumberOfSurgeriesResponse;
import well.pharmacy.open_pharmacy.service.FindNumberOfSurgeriesService;

import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OpenPharmacyController.class)
class OpenPharmacyControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FindNumberOfSurgeriesService service;

    @Test
    void wrongDataSet() throws Exception {

        String invalidDataSet = "{\n" +
                "  \"data\": {\n" +
                "    \"date\": \"2019-01-17T09:34:18.171Z\",\n" +
                "    \"list\": [\n" +
                "      {\n" +
                "        \"name\": \"CHATHAM MEDICAL CENTRE\",\n" +
                "        \"addressLine1\": \"DOCK ROAD\",\n" +
                "        \"addressLine2\": \"\",\n" +
                "        \"city\": \"\",\n" +
                "        \"postcode\": \"ME44UG\",\n" +
                "        \"phone\": \"\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";

        mvc.perform(post("/openPharmacy")
                .content(invalidDataSet)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void validDataSet() throws Exception {

        FindNumberOfSurgeriesResponse findNumberOfSurgeriesResponse = new FindNumberOfSurgeriesResponse("M5", 1, "100%");

        Stream<FindNumberOfSurgeriesResponse> response = Stream.of(findNumberOfSurgeriesResponse);

        when(service.findNumberOfSurgeries(any(), eq(0))).thenReturn(response);

        String validDataSet = "{\n" +
                "  \"data\": {\n" +
                "    \"date\": \"2019-01-17T09:34:18.171Z\",\n" +
                "    \"list\": [\n" +
                "      {\n" +
                "        \"name\": \"CHATHAM MEDICAL CENTRE\",\n" +
                "        \"addressLine1\": \"DOCK ROAD\",\n" +
                "        \"addressLine2\": \"\",\n" +
                "        \"city\": \"\",\n" +
                "        \"postcode\": \"M5 4UG\",\n" +
                "        \"phone\": \"\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";

        mvc.perform(post("/openPharmacy")
                .content(validDataSet)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0]['postcode']", hasToString("M5")))
                .andExpect(jsonPath("$[0]['numberOfSurgeries']", is(1)))
                .andExpect(jsonPath("$[0]['percentageOfSurgeries']", hasToString("100%")));
    }


    @Test
    void validTopDataSet() throws Exception {

        FindNumberOfSurgeriesResponse topSurgery = new FindNumberOfSurgeriesResponse("M3", 2, "50%");
        FindNumberOfSurgeriesResponse topSurgery2 = new FindNumberOfSurgeriesResponse("M4", 1, "25%");

        Stream<FindNumberOfSurgeriesResponse> response = Stream.of(topSurgery, topSurgery2);

        when(service.findNumberOfSurgeries(any(), eq(2))).thenReturn(response);

        String validDataSet = "{\n" +
                "  \"data\": {\n" +
                "    \"date\": \"2019-01-17T09:34:18.171Z\",\n" +
                "    \"list\": [\n" +
                "      {\n" +
                "        \"name\": \"CHATHAM MEDICAL CENTRE\",\n" +
                "        \"addressLine1\": \"DOCK ROAD\",\n" +
                "        \"addressLine2\": \"\",\n" +
                "        \"city\": \"\",\n" +
                "        \"postcode\": \"M5 4UG\",\n" +
                "        \"phone\": \"\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"LINDEN ROAD SURGERY\",\n" +
                "        \"addressLine1\": \"THE SURGERY\",\n" +
                "        \"addressLine2\": \"13 LINDEN ROAD\",\n" +
                "        \"city\": \"BEDFORD\",\n" +
                "        \"postcode\": \"M3 2DQ\",\n" +
                "        \"phone\": \"01234 273272\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"CLAPHAM ROAD SURGERY\",\n" +
                "        \"addressLine1\": \"CLAPHAM ROAD SURGERY\",\n" +
                "        \"addressLine2\": \"46-48 CLAPHAM ROAD\",\n" +
                "        \"city\": \"BEDFORD\",\n" +
                "        \"postcode\": \"M4 7PW\",\n" +
                "        \"phone\": \"01234 357143\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"HOUGHTON CLOSE SURGERY\",\n" +
                "        \"addressLine1\": \"HOUGHTON CLOSE SURGERY\",\n" +
                "        \"addressLine2\": \"1 HOUGHTON CLOSE\",\n" +
                "        \"city\": \"AMPTHILL\",\n" +
                "        \"postcode\": \"M3 2TG\",\n" +
                "        \"phone\": \"01525 300898\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";

        mvc.perform(post("/openPharmacy/2")
                .content(validDataSet)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]['postcode']", hasToString("M3")))
                .andExpect(jsonPath("$[0]['numberOfSurgeries']", is(2)))
                .andExpect(jsonPath("$[0]['percentageOfSurgeries']", hasToString("50%")))
                .andExpect(jsonPath("$[1]['postcode']", hasToString("M4")))
                .andExpect(jsonPath("$[1]['numberOfSurgeries']", is(1)))
                .andExpect(jsonPath("$[1]['percentageOfSurgeries']", hasToString("25%")));
    }
}
