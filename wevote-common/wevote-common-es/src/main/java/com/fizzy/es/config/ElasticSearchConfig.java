package com.fizzy.es.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;

/**
 * @author CDLX
 */
@Configuration
public class ElasticSearchConfig {

    /**
     * Bean name default  函数名字
     *
     * @return
     */
    @Bean(name = "transportClient")
    public TransportClient transportClient() {
        TransportClient transportClient = null;
        try {
            // 配置信息
            Settings esSetting = Settings.builder()
                    .put("cluster.name", "elasticsearch")
                    //集群名字
                    .put("client.transport.sniff", true)
                    //增加嗅探机制，找到ES集群
                    .put("thread_pool.search.size",5)
                    //增加线程池个数，暂时设为5
                    .build();
            //配置信息Settings自定义
            transportClient = new PreBuiltTransportClient(esSetting);
            TransportAddress transportAddress = new TransportAddress(InetAddress.getByName("127.0.0.1"), Integer.parseInt("9300"));
            transportClient.addTransportAddresses(transportAddress);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return transportClient;
    }
}
