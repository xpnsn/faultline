package model;

public record ApiEndpoint(String path, String method) {

    @Override
    public String toString() {
        return method + " " + path;
    }
}
