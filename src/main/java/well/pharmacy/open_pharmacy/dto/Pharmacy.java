package well.pharmacy.open_pharmacy.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

@Data
public class Pharmacy {

    private String name;
    private String addressLine1;
    private String addressLine2;
    private String city;
    @JsonDeserialize(using = PostCodeDeserializer.class)
    private PostCode postcode;
    private String phone;


}
