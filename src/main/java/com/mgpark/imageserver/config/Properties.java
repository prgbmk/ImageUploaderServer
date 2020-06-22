package com.mgpark.imageserver.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources(
        @PropertySource("properties/root.properties")
)
@Data
public class Properties {
    @Value("${imageSaveFolder}")
    private String imageSaveFolder;
}
