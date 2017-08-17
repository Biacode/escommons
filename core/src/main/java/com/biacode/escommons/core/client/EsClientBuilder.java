package com.biacode.escommons.core.client;

import org.elasticsearch.client.Client;

/**
 * Created by Arthur Asatryan.
 * Date: 8/17/17
 * Time: 11:45 AM
 */
public interface EsClientBuilder {
    Client build();
}
