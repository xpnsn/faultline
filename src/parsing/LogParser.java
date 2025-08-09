package parsing;

import model.ApiEndpoint;
import model.LogEntity;
import model.LogLevel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {

    private static final String LOG_REGEX = "^(\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2})\\s+([A-Z]+)\\s+(.*)$";
    private static final String IP_REGEX = "\\b((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)\\b";
    private static final String API_REGEX = "\\b(GET|POST|PUT|DELETE)\\s+(api/\\S+)";

    public Optional<LogEntity> parse(String log) {

        Pattern logPattern = Pattern.compile(LOG_REGEX);
        Matcher logMatcher = logPattern.matcher(log);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LogEntity logEntity = null;

        if(logMatcher.matches()) {
            String date = logMatcher.group(1);
            String level = logMatcher.group(2);
            String message = logMatcher.group(3).trim();

            LocalDateTime timestamp = null;
            LogLevel logLevel;

            try {
                timestamp = LocalDateTime.parse(date, formatter);
            } catch (DateTimeParseException e) {
                System.err.println("Invalid date format: " + date);
            }
            try {
                logLevel = LogLevel.valueOf(level);
            } catch (IllegalArgumentException e) {
                logLevel = LogLevel.UNKNOWN;
            }

            return Optional.of(new LogEntity(timestamp, logLevel, message));
        }

        return Optional.empty();
    }

    public Optional<String> getIP(LogEntity log) {

        Pattern ipPattern = Pattern.compile(IP_REGEX);


        Matcher ipMatcher = ipPattern.matcher(log.message());

        if(ipMatcher.find()) {
            return Optional.of(ipMatcher.group());
        }
        return Optional.empty();
    }

    public Optional<ApiEndpoint> getApiEndpoint(LogEntity log) {
        Pattern apiPattern = Pattern.compile(API_REGEX);
        Matcher apiMatcher = apiPattern.matcher(log.message());

        if (apiMatcher.find()) {
            String method = apiMatcher.group(1);
            String path = normalizePath(apiMatcher.group(2));
            return Optional.of(new ApiEndpoint(path, method));
        }
        return Optional.empty();
    }

    private String normalizePath(String path) {
        // Replace numeric IDs or UUIDs with "*"
        return path.replaceAll("\\b\\d+\\b", "*")
                .replaceAll("\\b[0-9a-fA-F\\-]{36}\\b", "*"); // for UUIDs
    }

}
