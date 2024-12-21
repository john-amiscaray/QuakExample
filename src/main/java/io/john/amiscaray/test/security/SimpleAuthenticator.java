package io.john.amiscaray.test.security;

import io.john.amiscaray.quak.core.di.provider.annotation.ManagedType;
import io.john.amiscaray.quak.security.auth.Authenticator;
import io.john.amiscaray.quak.security.auth.credentials.Credentials;
import io.john.amiscaray.quak.security.auth.principal.Principal;
import io.john.amiscaray.quak.security.auth.principal.RoleAttachedPrincipal;
import io.john.amiscaray.quak.security.auth.principal.role.Role;
import io.john.amiscaray.quak.security.di.SecurityDependencyIDs;
import io.john.amiscaray.test.security.roles.Roles;

import java.time.Duration;
import java.util.Optional;

@ManagedType(dependencyName = SecurityDependencyIDs.AUTHENTICATOR_DEPENDENCY_NAME, dependencyType = Authenticator.class)
public class SimpleAuthenticator implements Authenticator {

    private static final RoleAttachedPrincipal JOHN = new RoleAttachedPrincipal() {
        @Override
        public Role[] getRoles() {
            return new Role[] { Roles.user() };
        }

        @Override
        public String getSecurityID() {
            return "Johnny Boy";
        }
    };

    private static final RoleAttachedPrincipal ELLI = new RoleAttachedPrincipal() {
        @Override
        public Role[] getRoles() {
            return new Role[] { Roles.admin() };
        }

        @Override
        public String getSecurityID() {
            return "Elli";
        }
    };

    @Override
    public Optional<Principal> lookupPrincipal(String s) {
        if (s.equals(JOHN.getSecurityID())) {
            return Optional.of(JOHN);
        } else if (s.equals(ELLI.getSecurityID())) {
            return Optional.of(ELLI);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Principal> lookupPrincipal(Credentials credentials) {
        if (credentials.getUsername().equals("John") && credentials.getPassword().equals("password")) {
            return Optional.of(JOHN);
        } else if (credentials.getUsername().equals("Elli") && credentials.getPassword().equals("password")) {
            return Optional.of(ELLI);
        }
        return Optional.empty();
    }

    @Override
    public Duration getAuthenticationValidDuration() {
        return Duration.ofDays(30);
    }

}
