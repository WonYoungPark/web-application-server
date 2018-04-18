package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wyparks2@gmail.com on 2018. 4. 10.
 * Blog : http://WonYoungPark.github.io
 * Github : http://github.com/WonYoungPark
 */
public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private Map<String, String> headers    = new HashMap<>();
    private Map<String, String> parameters = new HashMap<>();
    private RequestLine requestLine;

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

        String line = bufferedReader.readLine();
        if (line == null) {
            return;
        }

        // 요청라인
        requestLine = new RequestLine(line);

        // Header
        line = bufferedReader.readLine();
        while (line !=null && !"".equals(line)) {
            log.debug("header : {}", line);
            String[] headerTokens = line.split(": ");
            if (headerTokens.length == 2)
                headers.put(headerTokens[0], headerTokens[1]);

            line = bufferedReader.readLine();
        }

        // 요청 바디
        parseRequestBody(bufferedReader);
    }

    private void parseRequestBody(BufferedReader bufferedReader) throws IOException {
        if (!getMethod().isPost())
            return;

        String requestBody = IOUtils.readData(bufferedReader, Integer.parseInt(getHeader("Content-Length")));
        log.debug("requestBody : {}", requestBody);
        parameters = HttpRequestUtils.parseQueryString(requestBody);
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getHeader(String key) {
        return headers.getOrDefault(key, "");
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }



}
