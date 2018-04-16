package controller;

import http.HttpRequest;
import http.HttpResponse;

/**
 * Created by wyparks2@gmail.com on 2018. 4. 16.
 * Blog : http://WonYoungPark.github.io
 * Github : http://github.com/WonYoungPark
 */
public class ListUserController extends AbstractController{

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        super.doGet(request, response);
    }

    private boolean isLogin(String username) {
        return false;
    }
}
