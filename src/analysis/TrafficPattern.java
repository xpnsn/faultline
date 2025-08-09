package analysis;

import model.ApiEndpoint;
import model.LogEntity;
import service.LogParsingService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TrafficPattern implements Analyzer{


    private final LogParsingService logParsingService;

    public TrafficPattern(LogParsingService logParsingService) {
        this.logParsingService = logParsingService;
    }

    @Override
    public void analyze() {

        Map<ApiEndpoint, Integer> endpoints = logParsingService.getEndpoints();

        LinkedHashMap<ApiEndpoint, Integer> sortedMap = endpoints.entrySet()
                .stream()
                .sorted(Map.Entry.<ApiEndpoint, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
        int i=4;

        System.out.println("Traffic Pattern : ");
        System.out.println("┌───────────────────────────────────────────────────────┐");
        for (Map.Entry<ApiEndpoint, Integer> entry : sortedMap.entrySet()) {
            if(i==0) break;
            System.out.println("   •   "+entry.getKey().toString()+" -> "+entry.getValue());
            i--;
        }
        System.out.println("└───────────────────────────────────────────────────────┘");

    }
}
