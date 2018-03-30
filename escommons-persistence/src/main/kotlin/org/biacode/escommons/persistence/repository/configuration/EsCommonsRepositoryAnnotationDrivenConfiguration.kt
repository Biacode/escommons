package org.biacode.escommons.persistence.repository.configuration

import org.biacode.escommons.toolkit.configuration.EsCommonsToolkitAnnotationDrivenConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

/**
 * Created by Arthur Asatryan.
 * Date: 3/30/18
 * Time: 2:48 PM
 */
@Configuration
@ComponentScan("org.biacode.escommons.persistence.repository")
@Import(EsCommonsToolkitAnnotationDrivenConfiguration::class)
class EsCommonsRepositoryAnnotationDrivenConfiguration