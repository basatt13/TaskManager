package Api;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;

public class ValidateRequests {
    public static void validateRequest(HttpExchange exchange, String response) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            System.out.println(response);
            exchange.sendResponseHeaders(200, 0);
        }else if ("POST".equals(exchange.getRequestMethod())){
            System.out.println(response);
            exchange.sendResponseHeaders(201, 0);
        } else if ("DELETE".equals(exchange.getRequestMethod())){
            System.out.println(response);
            exchange.sendResponseHeaders(202, 0);
        }else {
            response = "Ожидается запрос GET \n" +
                    "Выполнен запрос " + exchange.getRequestMethod();
            System.out.println(response);
            exchange.sendResponseHeaders(405, 0);
        }
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

}
