package io.john.amiscaray.test.controllers;

import io.john.amiscaray.quak.core.di.provider.annotation.Instantiate;
import io.john.amiscaray.quak.data.DatabaseProxy;
import io.john.amiscaray.quak.http.request.Request;
import io.john.amiscaray.quak.http.request.RequestMethod;
import io.john.amiscaray.quak.http.response.Response;
import io.john.amiscaray.quak.security.auth.Authenticator;
import io.john.amiscaray.quak.security.auth.credentials.Credentials;
import io.john.amiscaray.quak.security.auth.exception.InvalidCredentialsException;
import io.john.amiscaray.quak.security.auth.jwt.JwtUtil;
import io.john.amiscaray.quak.web.controller.annotation.Controller;
import io.john.amiscaray.quak.web.handler.annotation.Handle;
import io.john.amiscaray.test.models.AuthRequestBody;
import io.john.amiscaray.test.orm.User;
import io.john.amiscaray.test.security.encryption.BcryptService;

@Controller
public class AuthController {

    private final JwtUtil jwtUtil;
    private final Authenticator authenticator;
    private final DatabaseProxy databaseProxy;
    private final BcryptService bcryptService;

    @Instantiate
    public AuthController(JwtUtil jwtUtil, Authenticator authenticator, DatabaseProxy databaseProxy, BcryptService bcryptService) {
        this.jwtUtil = jwtUtil;
        this.authenticator = authenticator;
        this.databaseProxy = databaseProxy;
        this.bcryptService = bcryptService;
    }

    @Handle(path="/signup", method = RequestMethod.POST)
    public Response<Void> signUp(Request<AuthRequestBody> request) {
        var requestBody = request.body();
        databaseProxy.persist(new User(
                requestBody.username(),
                bcryptService.hash(requestBody.password())
        ));
        return new Response<>(201, null);
    }

    @Handle(path="/login", method = RequestMethod.POST)
    public Response<String> login(Request<AuthRequestBody> request) {
        var requestBody = request.body();

        try {
            var authentication = authenticator.authenticate(new Credentials() {
                @Override
                public String getUsername() {
                    return requestBody.username();
                }

                @Override
                public String getPassword() {
                    return requestBody.password();
                }
            });
            var jwt = jwtUtil.generateToken(authentication.getIssuedTo());
            return Response.of(jwt);
        } catch (InvalidCredentialsException e) {
            return new Response<>(401, "Invalid credentials");
        }
    }

}
