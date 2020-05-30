package com.product.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

@Configuration
@DependsOn("systemConfig")
public class SecurityWebInitializer extends AbstractSecurityWebApplicationInitializer {
}