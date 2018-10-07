package org.biacode.escommons.core.starter.configuration;

import org.biacode.escommons.persistence.configuration.EsCommonsRepositoryAnnotationDrivenConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Arthur Asatryan.
 * Date: 3/30/18
 * Time: 4:34 PM
 */
@Configuration
@ComponentScan("org.biacode.escommons.core.starter")
@Import(EsCommonsRepositoryAnnotationDrivenConfiguration.class)
public class EsCommonsStarterCoreAnnotationDrivenConfiguration {
}
