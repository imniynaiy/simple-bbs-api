package com.theoriz.cnode.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "secure.ignored")
@Configuration
public class IgnoreUrlsConfig {
    private List<String> getUrls = new ArrayList<>();
    private List<String> postUrls = new ArrayList<>();
}
