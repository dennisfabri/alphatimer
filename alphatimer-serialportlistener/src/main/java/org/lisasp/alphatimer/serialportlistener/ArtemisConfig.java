package org.lisasp.alphatimer.serialportlistener;

import org.apache.activemq.artemis.api.core.TransportConfiguration;
import org.apache.activemq.artemis.core.config.BridgeConfiguration;
import org.apache.activemq.artemis.core.remoting.impl.netty.NettyConnectorFactory;
import org.apache.activemq.artemis.core.server.ComponentConfigurationRoutingType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisConfigurationCustomizer;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ArtemisConfig implements ArtemisConfigurationCustomizer {

    @Value("${spring.artemis.bridge.remote-queue:ares-messages}")
    private String remoteQueue;

    @Value("${spring.jms.template.default-destination:ares-messages-local}")
    private String localQueue;

    @Value("${spring.artemis.bridge.remote-host:localhost}")
    private String host;

    @Value("${spring.artemis.bridge.remote-port:61616}")
    private String port;

    @Value("${spring.artemis.user:dev}")
    private String username;

    @Value("${spring.artemis.password:dev}")
    private String password;

    @Override
    public void customize(org.apache.activemq.artemis.core.config.Configuration configuration) {
        configuration.addConnectorConfiguration("remote-connector", transportConfiguration());
        configuration.getBridgeConfigurations().add(bridgeConfiguration());
    }

    private TransportConfiguration transportConfiguration() {
        Map<String, Object> params = new HashMap<>();
        params.put("host", host);
        params.put("port", port);
        return new TransportConfiguration(NettyConnectorFactory.class.getName(), params);
    }

    private BridgeConfiguration bridgeConfiguration() {
        BridgeConfiguration config = new BridgeConfiguration();
        config.setName("serialReaderBridge");
        config.setQueueName(localQueue);
        config.setForwardingAddress(remoteQueue);
        config.setUser(username);
        config.setPassword(password);
        config.setRetryInterval(10000);
        config.setRetryIntervalMultiplier(1.0);
        config.setUseDuplicateDetection(true);
        config.setRoutingType(ComponentConfigurationRoutingType.PASS);
        config.setStaticConnectors(Arrays.asList("remote-connector"));
        return config;
    }
}
