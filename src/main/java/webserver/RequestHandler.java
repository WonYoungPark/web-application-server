package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

public class RequestHandler extends Thread {
	private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
	
	private Socket connection;

	public RequestHandler(Socket connectionSocket) {
		this.connection = connectionSocket;
	}

	public void run() {
		log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
			connection.getPort());

		try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
			// TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

			// TODO : 18.03.21 - 스트림으로 작성해 보기
			//            bufferedReader.lines()
			//                          .filter(data -> data != null & !"".equals(data))
			//                          .forEach(data -> log.info("{}", data));
			List<String> http = new ArrayList<>();
			String line = bufferedReader.readLine();
			while (!"".equals(line)) {
				http.add(line);
				log.info("http -> {}", line);
				line = bufferedReader.readLine();

				//                if (line == null)
				//                    return;
			}

			String httpMethod = http.get(0).split(" ")[0];
			if ("GET".equals(httpMethod)) {

				String url = http.get(0).split(" ")[1];
				log.info("==========================> {}", url);

				int index = url.indexOf("?");
				if (index > 0) {
					url = url.substring(0, index);

					String param = url.substring(index + 1);

					Map<String, String> map = HttpRequestUtils.parseQueryString(param);
					User user = new User(map.get("userId"), map.get("password"), map.get("name"),
						map.get("email"));
				}
				DataOutputStream dos = new DataOutputStream(out);
				byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
				response200Header(dos, body.length);
				responseBody(dos, body);
			} else if ("POST".equals(httpMethod)) {
				log.info(IOUtils.readData(bufferedReader, 44));
				//                DataOutputStream dos = new DataOutputStream(out);
				//                byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
				//                response200Header(dos, body.length);
				//                responseBody(dos, body);
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
		try {
			dos.writeBytes("HTTP/1.1 200 OK \r\n");
			dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
			dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
	
	private void responseBody(DataOutputStream dos, byte[] body) {
		try {
			dos.write(body, 0, body.length);
			dos.writeBytes("\r\n");
			dos.flush();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
