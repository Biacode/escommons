package org.biacode.escommons.core.configuration

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource


/**
 * Created by Arthur Asatryan.
 * Date: 3/28/18
 * Time: 3:38 PM
 */
@ComponentScan(basePackages = [
    "org.biacode.escommons.core.client",
    "org.biacode.escommons.core.configuration"
])
@PropertySource(value = [
    "classpath:escommons.properties",
    "classpath:escommons-custom.properties",
    "file:\${user.home}/escommons.properties",
    "file:\${user.home}/escommons-custom.properties",
    "file:\${user.home}/escommons/escommons.properties",
    "file:\${user.home}/escommons/escommons-custom.properties",
    "file:\${user.home}/escommons/escommons-custom.properties",
    "classpath:escommons-test.properties"
], ignoreResourceNotFound = true)
@Configuration
class EsCommonsCoreAnnotationDrivenConfiguration