package controller;

import http.HttpRequest;
import http.HttpResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wyparks2@gmail.com on 2018. 4. 16.
 * Blog : http://WonYoungPark.github.io
 * Github : http://github.com/WonYoungPark
 */
public interface Controller {
    Map<String, Controller> controllers = new HashMap<>();
    
    void service(HttpRequest request, HttpResponse response);
}
