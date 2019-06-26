package com.thecodingpark.loadproperties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties("app")
@Getter
@Setter
public class MyProperties {

    private List<ServiceProperties> servicesList;
    private Map<String, ServiceProperties> servicesMap;

}