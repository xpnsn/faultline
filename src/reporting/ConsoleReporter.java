package reporting;

import analysis.Analyzer;
import analysis.KeyObservation;
import analysis.Summary;

import java.util.List;

public class ConsoleReporter implements Reporter {

    private final List<Analyzer> analyzers;

    public ConsoleReporter(List<Analyzer> analyzers) {
        this.analyzers = analyzers;
    }

    @Override
    public void generateReport() {

        for(int i=0; i<analyzers.size(); i++) {
            analyzers.get(i).analyze();
            if(i != analyzers.size() - 1) {
                System.out.println("---------------------------------------------------------");
            }
        }

    }
}
