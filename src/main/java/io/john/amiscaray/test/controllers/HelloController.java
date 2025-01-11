package io.john.amiscaray.test.controllers;

import io.john.amiscaray.quak.http.request.Request;
import io.john.amiscaray.quak.http.request.RequestMethod;
import io.john.amiscaray.quak.http.response.Response;
import io.john.amiscaray.quak.web.controller.annotation.Controller;
import io.john.amiscaray.quak.web.handler.annotation.Handle;

@Controller
public class HelloController {

    @Handle(path = "/hello", method = RequestMethod.GET)
    public Response<String> hello(Request<Void> request) {
        return Response.of("Hello, world");
    }

}
