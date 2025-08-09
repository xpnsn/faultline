package analysis;

import model.LogEntity;
import parsing.LogParser;
import service.LogParsingService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class KeyObservation implements Analyzer {

    private final LogParsingService logParsingService;
//    private final

    public KeyObservation(LogParsingService logParsingService) {
        this.logParsingService = logParsingService;
    }

    @Override
    public void analyze() {

        List<LogEntity> entries = logParsingService.getLogEntities();

        Map<String, Integer> iPs = logParsingService.getIPs();
        String ip = "";
        int max = Integer.MIN_VALUE;
        for(Map.Entry<String, Integer> entry : iPs.entrySet()) {
            if(entry.getValue() > max) {
                max = entry.getValue();
                ip = entry.getKey();
            }
        }
        Map<Integer, Long> hourTraffic = logParsingService.getPeakHour();
        Long maxTraffic = Long.MIN_VALUE;
        int hour = -1;
        for(Map.Entry<Integer, Long> entry : hourTraffic.entrySet()) {
            if(entry.getValue() > maxTraffic) {
                maxTraffic = entry.getValue();
                hour = entry.getKey();
            }
        }
        System.out.println("Key Observations :");
        System.out.println("┌───────────────────────────────────────────────────────┐");

        if(!ip.isEmpty()) {
            System.out.println("   ►   High rate of requests from IP : "+ip);
        }
        if(hour != -1) {
            System.out.println("   ►   Peak traffic hour : "+hour+":00 - "+hour+":59 with "+maxTraffic+" requests" );
        }
        System.out.println("└───────────────────────────────────────────────────────┘");



//        System.out.println("Total Log Entries: " + entries.size());

        // Count entries by level
//        Map<LogLevel, Long> countsByLevel = entries.stream()
//                .collect(Collectors.groupingBy(LogEntry::level, Collectors.counting()));
//
//        System.out.println("\nCounts by Log Level:");
//        countsByLevel.forEach((level, count) -> System.out.println(level + ": " + count));
//
//        // List all ERROR messages
//        System.out.println("\n--- ERROR Messages ---");
//        entries.stream()
//                .filter(entry -> entry.level() == LogLevel.ERROR)
//                .forEach(entry -> System.out.println(entry.timestamp() + " | " + entry.message()));
//
//        System.out.println("\n--- End of Report ---");
    }
}
