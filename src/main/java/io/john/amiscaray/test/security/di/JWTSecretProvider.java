package io.john.amiscaray.test.security.di;


import io.john.amiscaray.quak.core.di.provider.annotation.Provide;
import io.john.amiscaray.quak.core.di.provider.annotation.Provider;

@Provider
public class JWTSecretProvider {

    @Provide(dependencyName = "jwt")
    public String provideJWTSecret() {
        return "Something secure";
    }

}
