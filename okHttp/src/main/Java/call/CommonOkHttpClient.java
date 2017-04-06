package call;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import https.HttpsUtils;
import listener.DisposeDataHandle;
import listener.DisposeDataListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import response.CommonJsonCallback;

/**
 * @author qndroid
 * @function 用来发送get, post请求的工具类，包括设置一些请求的共用参数
 */
public class CommonOkHttpClient {
    private static final int TIME_OUT = 30;//超时参数
    private static OkHttpClient mOkHttpClient;

    static {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();



        /**
         *  为所有请求添加请求头，看个人需求
         */
        okHttpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("User-Agent", "Imooc-Mobile") // 标明发送本次请求的客户端
                        .build();
                return chain.proceed(request);
            }
        });
//        okHttpClientBuilder.cookieJar(new SimpleCookieJar());
        //超时时间 前面时间后面设置秒
        okHttpClientBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        //读超时间
        okHttpClientBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        //写超时间
        okHttpClientBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        //允许重定向 默认为true  写上保险点
        okHttpClientBuilder.followRedirects(true);
        //设置Https请求支持
        okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;//不管是什么主机都是true
            }
        });
        /**
         * 设置https加密协议 指定证书还是全部证书 这里设置的是全部证书
         */
        okHttpClientBuilder.sslSocketFactory(HttpsUtils.initSSLSocketFactory(), HttpsUtils.initTrustManager());
        mOkHttpClient = okHttpClientBuilder.build();
    }

    public static OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

//    /**
//     * 指定cilent信任指定证书
//     *
//     * @param certificates
//     */
//    public static void setCertificates(InputStream... certificates) {
//        mOkHttpClient.newBuilder().sslSocketFactory(HttpsUtils.getSslSocketFactory(certificates, null, null)).build();
//    }

    /**
     * @param request
     * @param callback
     * 发送具体的http/https的请求
     */
    public static Call sendRequest(Request request, DisposeDataListener callback) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(new DisposeDataHandle(callback)));
        return call;
    }

}