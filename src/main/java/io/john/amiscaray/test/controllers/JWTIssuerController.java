package io.john.amiscaray.test.controllers;

import io.john.amiscaray.quak.core.di.provider.annotation.Instantiate;
import io.john.amiscaray.quak.http.request.Request;
import io.john.amiscaray.quak.http.request.RequestMethod;
import io.john.amiscaray.quak.http.response.Response;
import io.john.amiscaray.quak.security.auth.Authenticator;
import io.john.amiscaray.quak.security.auth.credentials.Credentials;
import io.john.amiscaray.quak.security.auth.exception.InvalidCredentialsException;
import io.john.amiscaray.quak.security.auth.jwt.JwtUtil;
import io.john.amiscaray.quak.web.controller.annotation.Controller;
import io.john.amiscaray.quak.web.handler.annotation.Handle;
import io.john.amiscaray.test.models.LoginRequestBody;

@Controller
public class JWTIssuerController {

    private JwtUtil jwtUtil;
    private Authenticator authenticator;

    @Instantiate
    public JWTIssuerController(JwtUtil jwtUtil, Authenticator authenticator) {
        this.jwtUtil = jwtUtil;
        this.authenticator = authenticator;
    }

    @Handle(path="/login", method = RequestMethod.POST)
    public Response<String> login(Request<LoginRequestBody> request) {
        var requestBody = request.body();
        var credentials = new Credentials() {
            @Override
            public String getUsername() {
                return requestBody.username();
            }

            @Override
            public String getPassword() {
                return requestBody.password();
            }
        };

        try {
            var authentication = authenticator.authenticate(credentials);
            var jwt = jwtUtil.generateToken(authentication.getIssuedTo());
            return Response.of(jwt);
        } catch (InvalidCredentialsException e) {
            return new Response<>(401, "Invalid credentials");
        }
    }

}
