package io.john.amiscaray.test.controllers.config;

import com.fasterxml.jackson.databind.JsonMappingException;
import io.john.amiscaray.quak.core.di.provider.annotation.Provide;
import io.john.amiscaray.quak.core.di.provider.annotation.Provider;
import io.john.amiscaray.quak.web.cfg.WebConfig;

import java.util.Map;

@Provider
public class AppWebConfigProvider {

    @Provide(dependencyName = WebConfig.APPLICATION_WEB_CFG_DEPENDENCY_NAME)
    public WebConfig provideWebConfig() {
        return new WebConfig(Map.of(JsonMappingException.class, 400));
    }

}
