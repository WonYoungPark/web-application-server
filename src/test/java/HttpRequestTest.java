import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by wyparks2@gmail.com on 2018. 4. 10.
 * Blog : http://WonYoungPark.github.io
 * Github : http://github.com/WonYoungPark
 */
public class HttpRequestTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    public void request_GET() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        HttpRequest request = new HttpRequest(in);

        assertEquals("GET"         , request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive"  , request.getHeader("Connection"));
        assertEquals("wyparks2"    , request.getParameter("userId"));
    }

    @Test
    public void request_POST() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST.txt"));
        HttpRequest request = new HttpRequest(in);

        assertEquals("POST"         , request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive"  , request.getHeader("Connection"));
        assertEquals("wyparks2"    , request.getParameter("userId"));
    }
}
