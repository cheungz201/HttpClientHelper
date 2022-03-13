package xyz.cheungz.httphelper.utils;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * @Program: HttpClientHelper
 * @Author: Zhang Zhe
 * @Create: 2022-03-13 12:50
 * @Version: 1.0.0
 * @Description: 获取单例的HttpClient
 **/
public class ClientFactory {

    private ClientFactory(){}

    /**
     * 全局唯一的CloseableHttpClient
     */
    private static CloseableHttpClient closeableHttpClient = null;

    /**
     * 全局唯一的HttpClient
     */
    private static HttpClient MultiHttpClient = null;

    /**
     * 获取单例CloseableHttpClient
     * @return CloseableHttpClient
     */
    public static CloseableHttpClient getPoolHttpClient(){
        if (closeableHttpClient == null){
            synchronized (ClientFactory.class){
                if (closeableHttpClient == null){
                    PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
                    manager.setDefaultSocketConfig(SocketConfig.custom().setSoTimeout(5000).build());
                    closeableHttpClient = HttpClients.custom().setConnectionManager(manager).build();
                    return closeableHttpClient;

                }
            }
        }
        return closeableHttpClient;
    }


    /**
     * 获取单例MultiHttpClient
     * @return MultiHttpClient
     */
    public static HttpClient getMultiHttpClient(){

        if (MultiHttpClient == null){
            synchronized (ClientFactory.class){
                if (MultiHttpClient == null){
                    MultiThreadedHttpConnectionManager manager = new MultiThreadedHttpConnectionManager();
                    MultiHttpClient = new HttpClient(manager);
                    return MultiHttpClient;
                }
            }
        }
        return MultiHttpClient;

    }
}
