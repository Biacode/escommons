package org.biacode.escommons.core.test.configuration

import org.biacode.escommons.persistence.repository.configuration.EsCommonsRepositoryAnnotationDrivenConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

/**
 * Created by Arthur Asatryan.
 * Date: 3/29/18
 * Time: 11:27 AM
 */
@Configuration
@ComponentScan(basePackages = ["org.biacode.escommons.core.test.configuration"])
@Import(EsCommonsRepositoryAnnotationDrivenConfiguration::class)
class EsCommonsTestAnnotationDrivenConfiguration