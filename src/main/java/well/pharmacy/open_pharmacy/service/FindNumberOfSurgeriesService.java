package well.pharmacy.open_pharmacy.service;

import org.springframework.stereotype.Service;
import well.pharmacy.open_pharmacy.dto.FindNumberOfSurgeriesRequest;
import well.pharmacy.open_pharmacy.dto.FindNumberOfSurgeriesResponse;
import well.pharmacy.open_pharmacy.dto.Pharmacy;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toMap;


@Service
public class FindNumberOfSurgeriesService {

    private static final String LISTOFMCRPOSTCODES = "M1,M2,M3,M4,M5,M6,M7,M8,M9,M11,M12,M13,M14,M15,M16,M17,M18,M19,M20,M21," +
            "M22,M23,M24,M25,M26,M27,M28,M29,M30,M31,M32,M33,M34,M35,M38,M40,M41,M43,M44,M45,M46,M50,M90";

    private static final DecimalFormat df = new DecimalFormat("0.00");


    public Stream<FindNumberOfSurgeriesResponse> findNumberOfSurgeries(FindNumberOfSurgeriesRequest findNumberOfSurgeriesRequest, Integer topOfSurgeries) {
        //organize surgeries per postcode
        Map<String, List<Pharmacy>> surgeriesPerOutwardCode = findNumberOfSurgeriesRequest.getData().getList().stream()
                .filter(surgery -> LISTOFMCRPOSTCODES.contains(surgery.getPostcode().getOutwardCode()))
                .collect(Collectors.groupingBy(x -> x.getPostcode().getOutwardCode()));

        //find total number of surgeries on the specific postcode
        int totalNumberOfSurgeries = surgeriesPerOutwardCode.values().stream().mapToInt(List::size).sum();

        //find top 5 postcodes with the biggest number of surgeries
        Map<String, List<Pharmacy>> top5Surgeries =
                surgeriesPerOutwardCode.entrySet().stream().sorted(comparingInt(e -> e.getValue().size() * -1))
                        .limit(topOfSurgeries == 0 ? surgeriesPerOutwardCode.size() : topOfSurgeries)
                        .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> {
                            throw new AssertionError();
                        }, LinkedHashMap::new));

        //build the response
        Stream.Builder<FindNumberOfSurgeriesResponse> histogram = Stream.builder();
        for (Map.Entry<String, List<Pharmacy>> entry : top5Surgeries.entrySet()) {
            String postcode = entry.getKey();
            int numberOfSurgeries = entry.getValue().size();
            float percentage = ((float) numberOfSurgeries * 100) / (float) totalNumberOfSurgeries;
            histogram.add(new FindNumberOfSurgeriesResponse(postcode, numberOfSurgeries, df.format(percentage) + "%"));
        }
        return histogram.build();
    }


}




