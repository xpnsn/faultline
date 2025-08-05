package reporting;

import analysis.KeyObservation;
import analysis.Summary;

public class ConsoleReporter implements Reporter {

    private final KeyObservation keyObservation;
    private final Summary summary;

    public ConsoleReporter(KeyObservation keyObservation, Summary summary) {
        this.keyObservation = keyObservation;
        this.summary = summary;
    }

    @Override
    public void generateReport() {

        keyObservation.analyze();
        summary.analyze();

    }
}
