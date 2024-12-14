package io.john.amiscaray.test.security.di;


import io.john.amiscaray.quak.core.di.provider.annotation.Provide;
import io.john.amiscaray.quak.core.di.provider.annotation.Provider;
import io.john.amiscaray.quak.core.properties.ApplicationProperties;
import io.john.amiscaray.quak.core.properties.ApplicationProperty;

@Provider
public class JWTSecretProvider {

    @Provide(dependencyName = "jwt")
    public String provideJWTSecret() {
        var properties = ApplicationProperties.getInstance();
        return properties.get(ApplicationProperty.JWT_SECRET_KEY);
    }

}
