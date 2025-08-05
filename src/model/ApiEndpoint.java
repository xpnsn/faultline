package model;

import java.util.Objects;

public class ApiEndpoint {
    private final String path;
    private final String method;

    public ApiEndpoint(String path, String method) {
        this.path = path;
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public String getMethod() {
        return method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiEndpoint that = (ApiEndpoint) o;
        return Objects.equals(path, that.path) &&
                Objects.equals(method, that.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, method);
    }

    @Override
    public String toString() {
        return method + " " + path;
    }
}
