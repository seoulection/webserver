package webserver;

public class HttpRequestValidator {
    public static boolean isUnsupportedMediaType(String contentType) {
        return !contentType.equals("application/x-www-form-urlencoded");
    }

    public static boolean isInvalidValue(String body) {
        return body.contains(" ");
    }

    public static boolean isValidFilterRequestPath(String path) {
        return path.contains("/todo?filter=");
    }

    public static boolean isValidRequestBody(String body) {
        return body.contains("todo-name=");
    }
}
