package io.john.amiscaray.test.security.di;


import io.john.amiscaray.quak.core.di.provider.annotation.Instantiate;
import io.john.amiscaray.quak.core.di.provider.annotation.Provide;
import io.john.amiscaray.quak.core.di.provider.annotation.ProvidedWith;
import io.john.amiscaray.quak.core.di.provider.annotation.Provider;
import io.john.amiscaray.quak.security.config.CORSConfig;
import io.john.amiscaray.quak.security.config.EndpointMapping;
import io.john.amiscaray.quak.security.config.SecurityConfig;
import io.john.amiscaray.quak.security.di.AuthenticationStrategy;
import io.john.amiscaray.quak.security.di.SecurityDependencyIDs;
import io.john.amiscaray.test.security.roles.Roles;

import java.time.Duration;
import java.util.List;

@Provider
public class SecurityConfigProvider {

    private final String jwtSecret;

    @Instantiate
    public SecurityConfigProvider(@ProvidedWith(dependencyName = "jwt") String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    @Provide(dependencyName = SecurityDependencyIDs.SECURITY_CONFIG_DEPENDENCY_NAME)
    public SecurityConfig securityConfig() {
        return SecurityConfig.builder()
                .securePathWithRole(new EndpointMapping("/studentdto/*", List.of(EndpointMapping.RequestMethodMatcher.ANY_MODIFYING)), List.of(Roles.admin()))
                .securePathWithCorsConfig("/*", CORSConfig.builder()
                        .allowOrigin("http://127.0.0.1:5500")
                        .allowMethod("GET")
                        .build())
                .authenticationStrategy(AuthenticationStrategy.JWT)
                .jwtSecretKey(jwtSecret)
                .jwtSecretExpiryTime(Duration.ofHours(10).toMillis())
                .build();
    }

}
