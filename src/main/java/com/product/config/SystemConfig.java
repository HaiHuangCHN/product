package com.product.config;

import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;

import com.product.costant.Constants;
import com.product.entity.Config;
import com.product.repository.ConfigRepository;

/**
 * TODO real time update config<br>
 * TODO config center<br>
 * TODO learn all the related knowledge
 * 
 * @author haihu
 */
@Configuration
public class SystemConfig {

    @Autowired
    private ConfigurableEnvironment configurableEnvironment;

    @Autowired
    private ConfigRepository configRepository;

    @Value("${config.env}")
    private String env;

    @PostConstruct
    public void initDatabasePropertySourceUsage() {

        MutablePropertySources mutablePropertySources = configurableEnvironment.getPropertySources();

        try {
            Map<String, String> collect = configRepository.findByEnv(Config.EnvEnum.valueOf(env)).stream().collect(Collectors.toMap(Config::getConfigName, Config::getConfigValue));

            Properties properties = new Properties();
            properties.putAll(collect);

            PropertiesPropertySource constants = new PropertiesPropertySource(Constants.CONFIG, properties);

            Pattern p = Pattern.compile("^applicationConfig.*");

            String name = null;
            boolean flag = false;

            for (PropertySource<?> source : mutablePropertySources) {
                if (p.matcher(source.getName()).matches()) {

                    name = source.getName();
                    flag = true;

                    break;
                }
            }

            if (flag) {
                mutablePropertySources.addBefore(name, constants);
            } else {
                mutablePropertySources.addFirst(constants);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
