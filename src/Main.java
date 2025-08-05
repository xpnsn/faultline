import analysis.Summary;
import model.LogEntity;
import analysis.KeyObservation;
import io.LogFileReader;
import parsing.LogParser;
import reporting.ConsoleReporter;
import reporting.Reporter;
import service.LogParsingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java Main <path_to_log_file>");
            return;
        }

        String filePath = args[0];

        LogFileReader logFileReader = new LogFileReader();
        LogParser logParser = new LogParser();

        LogParsingService logParsingService = new LogParsingService(logFileReader, logParser, filePath);

        KeyObservation keyObservation = new KeyObservation(logParsingService);
        Summary summary = new Summary(logParsingService);

        Reporter reporter = new ConsoleReporter(keyObservation, summary);

        reporter.generateReport();
    }
}
