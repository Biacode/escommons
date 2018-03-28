package org.biacode.escommons.core.configuration

import org.biacode.escommons.core.client.EsCommonsRestClientConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

/**
 * Created by Arthur Asatryan.
 * Date: 3/28/18
 * Time: 3:38 PM
 */
@Configuration
@ComponentScan(basePackages = [
    "org.biacode.escommons.core.client",
    "org.biacode.escommons.core.component",
    "org.biacode.escommons.core.configuration",
    "org.biacode.escommons.core.repository"
])
@Import(EsCommonsRestClientConfiguration::class)
class EsCommonsCoreAnnotationDrivenConfiguration