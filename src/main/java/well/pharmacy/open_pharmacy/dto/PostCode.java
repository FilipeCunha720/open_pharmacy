package well.pharmacy.open_pharmacy.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Data
public class PostCode {
    private String postCode;
    private String outwardCode;

    public PostCode(String postCode) {
        this.postCode = postCode;
        String[] postCodeOutwardCode = postCode.split("\\s+");
        if (postCodeOutwardCode.length < 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("This postcode '%s' is not valid", postCode));
        }
        this.outwardCode = postCodeOutwardCode[0];
    }
}
