package org.biacode.escommons.core.starter;

import org.biacode.escommons.core.starter.annotation.EnableEsCommons;
import org.biacode.escommons.core.starter.configuration.EsCommonsStarterCoreAnnotationDrivenConfiguration;
import org.biacode.escommons.core.starter.properties.EsCommonsProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Arthur Asatryan.
 * Date: 3/30/18
 * Time: 4:33 PM
 */
@Configuration
@ConditionalOnClass(EnableEsCommons.class)
@EnableConfigurationProperties(EsCommonsProperties.class)
@Import(EsCommonsStarterCoreAnnotationDrivenConfiguration.class)
public class EsCommonsStarterCoreAutoConfiguration {
}
