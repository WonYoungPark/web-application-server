package controller;

import http.HttpRequest;
import http.HttpResponse;

/**
 * Created by wyparks2@gmail.com on 2018. 4. 16.
 * Blog : http://WonYoungPark.github.io
 * Github : http://github.com/WonYoungPark
 */
public interface Controller {
    void service(HttpRequest request, HttpResponse response);
}
