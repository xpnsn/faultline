package analysis;

import model.LogEntity;
import model.LogLevel;
import service.LogParsingService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Summary implements Analyzer{

    private final LogParsingService logParsingService;

    public Summary(LogParsingService logParsingService) {
        this.logParsingService = logParsingService;
    }

    @Override
    public void analyze() {

        Set<LogLevel> logLevels = logParsingService.getLogLevels();
        List<LogEntity> logEntities = logParsingService.getLogEntities();

        LogLevel highestSeverity = LogLevel.UNKNOWN;
        boolean requireAction = false;

        if (logLevels.contains(LogLevel.ERROR)) {
            highestSeverity = LogLevel.ERROR;
        } else if (logLevels.contains(LogLevel.WARN)) {
            highestSeverity = LogLevel.WARN;
        } else if (logLevels.contains(LogLevel.INFO)) {
            highestSeverity = LogLevel.INFO;
        }

        if(highestSeverity == LogLevel.ERROR) {
            requireAction = true;
        }
        int warnLevels = 0;
        for(LogEntity logEntity : logEntities) {
            if(logEntity.level().name().equals("WARN")) {
                warnLevels++;
            }
        }
        if(warnLevels > logLevels.size()*0.2) { // more than 20% of logs have WARN
            requireAction = true;
        }



        System.out.println("Analysis Summary : ");
        System.out.println("┌───────────────────────────────────────────────────────┐");
        System.out.println("   •   Highest Severity : "+highestSeverity);
        System.out.println("   •   Requires Immediate Action : "+requireAction);
        System.out.println("└───────────────────────────────────────────────────────┘");

    }
}
