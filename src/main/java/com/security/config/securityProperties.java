package com.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "security")
public class securityProperties {

    List<String> allowedDomains = Collections.emptyList();
}
