package com.demo.mob.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.mob.activity.R;
import com.demo.mob.utils.App;
import com.demo.mob.utils.BaseFragment;
import com.demo.mob.utils.BitmapUtil;
import com.demo.mob.utils.GetSignatures;
import com.demo.mob.utils.Logs;
import com.demo.mob.utils.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by chenjt on 2017/1/11.
 */

public class HttpFragment extends BaseFragment{
    private OkHttpClient okHttpClient = new OkHttpClient();
    private TextView result;
    private ImageView img;
    //http://localhost:8185/Test/login?username=haha&password=123
    private String baseurl = "http://192.168.44.191:8185/Test/";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_http;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {



        view.findViewById(R.id.okhttp_GET).setOnClickListener(this);
        view.findViewById(R.id.okhttp_PostString).setOnClickListener(this);
        view.findViewById(R.id.okhttp_Post).setOnClickListener(this);
        view.findViewById(R.id.okhttp_PostFile).setOnClickListener(this);
        view.findViewById(R.id.okhttp_Upload).setOnClickListener(this);
        view.findViewById(R.id.okhttp_Download).setOnClickListener(this);
        result = (TextView) view.findViewById(R.id.okhttp_Text);
        img = (ImageView) view.findViewById(R.id.okhttp_Image);
    }


    //get请求
    private void doGet(){
        //1.拿到httpClient对象
        //OkHttpClient okHttpClient = new OkHttpClient(); 提升为全局不至于每次都new
        //2.构造Request
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(baseurl + "login?username=haha&password=123").build();

        executeRequest(request);
    }

    //post请求
    private void doPost() {
        //2.构造Request和RequestBody
        RequestBody body = new FormBody.Builder()
                .add("username","chenjt")
                .add("password","123456")
                .build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(baseurl + "login").post(body).build();

        executeRequest(request);
    }


    //提交字符串
    private void doPostString() {
        //2.构造Request和RequestBody
        RequestBody body = RequestBody.create(MediaType.parse("text/plain;chaset=utf-8"),"{username:chenjt,password:123456}");
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(baseurl + "postString").post(body).build();

        executeRequest(request);
    }


    //提交文件
    private void doPostFile() {
        File file = new File(App.PATH);
        if (!file.exists()){
            Logs.e(Tag,"图片不存在");
            return;
        }

        //百度搜索MediaType表
        RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"),file);
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(baseurl + "postFile").post(body).build();
        executeRequest(request);
    }

    //上传文件
    private void doUpload(){
        File file = new File(App.PATH);
        if (!file.exists()){
            Logs.e(Tag,"图片不存在");
            return;
        }
        //百度搜索MediaType表
        MultipartBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username","chenjt")
                .addFormDataPart("password","123456")
                .addFormDataPart("photo","tp.jpg",RequestBody.create(MediaType.parse("application/octet-stream"),file))
                .build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(baseurl + "uploadInfo").post(body).build();
        executeRequest(request);
    }

    //下载文件
    private void doDownload(){
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(baseurl + "demo/tp.jpg").build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            //失败_啊
            @Override
            public void onFailure(Call call,final IOException e) {
                Logs.e(Tag,"onFailure : " + e.getMessage());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.setText("onFailure : \n" + e.getMessage());
                    }
                });
                e.printStackTrace();
            }
            //响应,不是UI线程的回调
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                long total = response.body().contentLength();
                long sum = 0L;
                InputStream is = response.body().byteStream();
                FileOutputStream fos = new FileOutputStream(new File(App.PATH));
                int len = 0;
                byte[] bt = new byte[128];
                while ((len = is.read(bt)) != -1) {
                    fos.write(bt,0,len);
                    sum += len;
                    Logs.e(Tag,sum + "/" + total);
                }
                fos.flush();
                fos.close();
                is.close();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = null;
                        try {
                            bitmap = BitmapUtil.getBitmapFormPath(App.PATH);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        img.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }



    //请求封装类
    private void executeRequest(Request request) {
        //3.将Request封装成Call
        Call call = okHttpClient.newCall(request);

        //4.执行Call
//        Response response = call.enqueue();
        //异步操作
        call.enqueue(new Callback() {
            //失败
            @Override
            public void onFailure(Call call,final IOException e) {
                Logs.e(Tag,"onFailure : " + e.getMessage());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.setText("onFailure : \n" + e.getMessage());
                    }
                });
                e.printStackTrace();
            }
            //响应,不是UI线程的回调
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.setText("onResponse : \n" + str);
                    }
                });
                Logs.e(Tag,"onResponse : " + str);
            }
        });
    }




    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.okhttp_GET:
                doGet();
                break;
            case R.id.okhttp_Post:
                doPost();
                break;
            case R.id.okhttp_PostString:
                doPostString();
                break;
            case R.id.okhttp_PostFile:
                doPostFile();
                break;
            case R.id.okhttp_Upload:
                doUpload();
                break;
            case R.id.okhttp_Download:
                doDownload();
                break;
        }
    }



}
