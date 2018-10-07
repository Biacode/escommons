package org.biacode.escommons.toolkit.configuration

import org.biacode.escommons.core.configuration.EsCommonsCoreAnnotationDrivenConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

/**
 * Created by Arthur Asatryan.
 * Date: 3/30/18
 * Time: 12:12 PM
 */
@Configuration
@ComponentScan(basePackages = ["org.biacode.escommons.toolkit.component", "org.biacode.escommons.toolkit.configuration"])
@Import(EsCommonsCoreAnnotationDrivenConfiguration::class)
class EsCommonsToolkitAnnotationDrivenConfiguration