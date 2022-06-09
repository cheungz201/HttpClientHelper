## 关于
对HttpClient进一步封装的请求发送器   

## 联系我
email:cheungz201@163.com

## 开源许可
[Apache2.0](https://www.apache.org/licenses/LICENSE-2.0.html)

## 计划  
1.支持文件传输功能...
## Demo 
PoolHttpClient
~~~
PoolHttpClient poolHttpClient = new PoolHttpClient();  
ResponseBody responseBody = poolHttpClient.sendGet(new RequestBody(url_get));
~~~
MultiHttpClient
~~~
MultiHttpClient multiHttpClient = new MultiHttpClient();  
ResponseBody responseBody = multiHttpClient.sendGet(new RequestBody(url_get));
~~~