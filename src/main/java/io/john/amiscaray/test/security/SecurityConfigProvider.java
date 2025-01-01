package io.john.amiscaray.test.security;

import io.john.amiscaray.quak.core.di.provider.annotation.Provide;
import io.john.amiscaray.quak.core.di.provider.annotation.Provider;
import io.john.amiscaray.quak.security.config.CORSConfig;
import io.john.amiscaray.quak.security.config.EndpointMapping;
import io.john.amiscaray.quak.security.config.SecurityConfig;
import io.john.amiscaray.quak.security.di.AuthenticationStrategy;
import io.john.amiscaray.quak.security.di.SecurityDependencyIDs;

import java.util.List;

@Provider
public class SecurityConfigProvider {

    @Provide(dependencyName = SecurityDependencyIDs.SECURITY_CONFIG_DEPENDENCY_NAME)
    public SecurityConfig provideSecurityConfig() {
        return SecurityConfig
                .builder()
                .authenticationStrategy(AuthenticationStrategy.JWT)
                .securePathWithRole(new EndpointMapping(
                        "/studentdto/*",
                        List.of(EndpointMapping.RequestMethodMatcher.ANY_MODIFYING)
                ), List.of(Roles.admin()))
                .securePathWithCorsConfig("/*", CORSConfig.builder()
                        .allowOrigin("http://localhost:4200")
                        .allowAllHeaders(true)
                        .allowedMethods(List.of("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS"))
                        .build())
                .build();
    }

}
