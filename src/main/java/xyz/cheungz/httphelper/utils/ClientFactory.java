package xyz.cheungz.httphelper.utils;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * 获取单例的HttpClient
 *
 * @Program: HttpClientHelper
 * @Author: Zhang Zhe
 * @Create: 2022-03-13 12:50
 * @Version: 1.0.0
 **/
public class ClientFactory {

    private ClientFactory(){}

    /**
     * 全局唯一的CloseableHttpClient
     */
    private static volatile CloseableHttpClient closeableHttpClient = null;

    /**
     * 全局唯一的HttpClient
     */
    private static volatile HttpClient MultiHttpClient = null;

    /**
     * 读取超时
     */
    private final static int SOCKET_TIMEOUT = 10000;

    /**
     * 连接超时
     */
    private final static int CONNECTION_TIMEOUT = 10000;


    /**
     * 每个HOST的最大连接数量
     */
    private final static int MAX_CONN_PRE_HOST = 20;

    /**
     * 连接池最大连接数
     */
    private final static int MAX_CONN = 60;

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
                    HttpConnectionManagerParams params = manager.getParams();
                    params.setConnectionTimeout(CONNECTION_TIMEOUT);
                    params.setSoTimeout(SOCKET_TIMEOUT);
                    params.setDefaultMaxConnectionsPerHost(MAX_CONN_PRE_HOST);
                    params.setMaxTotalConnections(MAX_CONN);
                    MultiHttpClient = new HttpClient(manager);
                    return MultiHttpClient;
                }
            }
        }
        return MultiHttpClient;
    }

}
