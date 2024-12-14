package io.john.amiscaray.test.security.di;


import io.john.amiscaray.quak.core.di.ApplicationContext;
import io.john.amiscaray.quak.core.di.dependency.DependencyID;
import io.john.amiscaray.quak.core.di.dependency.ProvidedDependency;
import io.john.amiscaray.quak.core.di.provider.DependencyProvider;
import io.john.amiscaray.quak.security.auth.Authenticator;
import io.john.amiscaray.quak.security.di.SecurityDependencyIDs;
import io.john.amiscaray.test.security.SimpleAuthenticator;

import java.util.List;

public class SimpleAuthenticatorProvider implements DependencyProvider<Authenticator> {
    @Override
    public DependencyID<Authenticator> getDependencyID() {
        return SecurityDependencyIDs.AUTHENTICATOR_DEPENDENCY;
    }

    @Override
    public ProvidedDependency<Authenticator> provideDependency(ApplicationContext applicationContext) {
        return new ProvidedDependency<>(getDependencyID(), new SimpleAuthenticator());
    }

    @Override
    public List<DependencyID<?>> getDependencies() {
        return List.of();
    }
}
