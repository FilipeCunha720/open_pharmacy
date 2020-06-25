package well.pharmacy.open_pharmacy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindNumberOfSurgeriesResponse {
    private String postcode;
    private int numberOfSurgeries;
    private String percentageOfSurgeries;
}




