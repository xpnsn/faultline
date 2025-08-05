package service;

import io.LogFileReader;
import model.ApiEndpoint;
import model.LogEntity;
import model.LogLevel;
import parsing.LogParser;

import java.util.*;
import java.util.stream.Collectors;

public class LogParsingService {

    private final LogFileReader logFileReader;
    private final LogParser logParser;
    private final String filepath;

    public LogParsingService(LogFileReader logFileReader, LogParser logParser, String filepath) {
        this.logFileReader = logFileReader;
        this.logParser = logParser;
        this.filepath = filepath;
    }


    public List<LogEntity> getLogEntities() {
        List<String> lines = logFileReader.readLines(filepath);

        return lines.stream()
                .map(logParser::parse)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public Map<String, Integer> getIPs() {
        Map<String, Integer> ips = new HashMap<>();
        List<LogEntity> logEntities = getLogEntities();

        for (LogEntity logEntity : logEntities) {
            Optional<String> OptionalIp = logParser.getIP(logEntity);
            OptionalIp.ifPresent((ip) -> ips.put(ip, ips.getOrDefault(ip, 0) + 1));
        }
        return ips;
    }

    public Set<LogLevel> getLogLevels() {
        Set<LogLevel> logLevels = new HashSet<>();
        List<LogEntity> logEntities = getLogEntities();
        for(LogEntity logEntity : logEntities) {
            logLevels.add(logEntity.level());
        }
        return logLevels;
    }

    public Map<String, Integer> getEndpoints() {
        Map<String, Integer> endpoints = new HashMap<>();
        List<LogEntity> logEntities = getLogEntities();
        for(LogEntity logEntity : logEntities) {
            Optional<?> url = logParser.getApiEndpoint(logEntity);
            url.ifPresent(o -> endpoints.put(o.toString(), endpoints.getOrDefault(o.toString(), 0) + 1));
        }
        return endpoints;
    }
}
