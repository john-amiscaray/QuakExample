package io.john.amiscaray.test.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import io.john.amiscaray.quak.core.di.provider.annotation.Instantiate;
import io.john.amiscaray.quak.core.di.provider.annotation.ManagedType;
import io.john.amiscaray.quak.data.DatabaseProxy;
import io.john.amiscaray.quak.security.auth.credentials.Credentials;
import io.john.amiscaray.quak.security.auth.jwt.JwtUtil;
import io.john.amiscaray.quak.security.auth.principal.Principal;
import io.john.amiscaray.quak.security.di.SecurityDependencyIDs;
import io.john.amiscaray.test.orm.User;
import io.john.amiscaray.test.security.encryption.BcryptService;
import lombok.extern.java.Log;

import java.util.Optional;

import static io.john.amiscaray.quak.data.query.QueryCriteria.*;

@ManagedType(dependencyName = SecurityDependencyIDs.AUTHENTICATOR_DEPENDENCY_NAME, dependencyType = io.john.amiscaray.quak.security.auth.Authenticator.class)
@Log
public class Authenticator implements io.john.amiscaray.quak.security.auth.Authenticator {

    private final DatabaseProxy databaseProxy;
    private final JwtUtil jwtUtil;
    private final BcryptService bcryptService;

    @Instantiate
    public Authenticator(DatabaseProxy databaseProxy, JwtUtil jwtUtil, BcryptService bcryptService) {
        this.databaseProxy = databaseProxy;
        this.jwtUtil = jwtUtil;
        this.bcryptService = bcryptService;
    }

    @Override
    public Optional<Principal> lookupPrincipal(String securityID) {
        var matchingUsers = databaseProxy.queryAllWhere(User.class, valueOfField("id", is(securityID)));
        if (!matchingUsers.isEmpty()) {
            assert matchingUsers.size() == 1;
            var user = matchingUsers.getFirst();
            return Optional.of(() -> user.getId() + "");
        }
        return Optional.empty();
    }

    @Override
    public Optional<Principal> lookupPrincipal(Credentials credentials) {
        var matchingUsers = databaseProxy.queryAllWhere(User.class, valueOfField("username", is(credentials.getUsername())));
        if (!matchingUsers.isEmpty()) {
            assert matchingUsers.size() == 1;
            var user = matchingUsers.getFirst();
            if (bcryptService.verify(credentials.getPassword(), user.getPassword())) {
                return Optional.of(() -> user.getId() + "");
            }
        }
        return Optional.empty();
    }

}
