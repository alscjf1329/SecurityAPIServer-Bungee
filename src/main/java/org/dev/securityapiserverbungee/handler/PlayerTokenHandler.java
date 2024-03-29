package org.dev.securityapiserverbungee.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import org.dev.securityapiserverbungee.dto.VerificationResultDTO;
import org.dev.securityapiserverbungee.security.TokenManager;
import org.json.JSONObject;

public class PlayerTokenHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(),
            StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder requestBuilder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            requestBuilder.append(line);
        }
        String requestBody = requestBuilder.toString();

        // JSON 파싱
        JSONObject jsonObject = new JSONObject(requestBody);

        // JSONObject에서 데이터 추출 후 DTO 생성
        String nickname = jsonObject.getString("nickname");
        String authenticationCode = jsonObject.getString("authenticationCode");

        VerificationResultDTO resultDTO = TokenManager.getInstance()
            .authenticate(nickname, authenticationCode);
        // JSON으로 변환
        JSONObject jsonResponse = new JSONObject(resultDTO);

        // 응답 헤더 설정
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, jsonResponse.toString().length());

        // 응답 보내기
        OutputStream os = exchange.getResponseBody();
        os.write(jsonResponse.toString().getBytes());
        os.close();
    }
}
