package org.biacode.escommons.example.test

import org.biacode.escommons.core.test.AbstractEsCommonsIntegrationTest
import org.springframework.test.context.ContextConfiguration

/**
 * Created by Vasil Mamikonyan
 * Company: SFL LLC
 * Date: 4/4/2018
 * Time: 11:06 AM
 */
@ContextConfiguration(classes = [ServiceIntegrationTestConfiguration::class])
abstract class AbstractServiceIntegrationTest : AbstractEsCommonsIntegrationTest()