package com.demo.mob.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.mob.activity.R;
import com.demo.mob.adapter.SmsAdapter;
import com.demo.mob.bean.SmsItem;
import com.demo.mob.utils.App;
import com.demo.mob.utils.BaseFragment;
import com.demo.mob.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;

/**
 * Created by chenjt on 2017/1/11.
 */

public class SmsFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private EditText phone, provingCode;
    private Button submit, proving;
    private DrawerLayout dLayout;
    private ListView sms_menu;
    private List<SmsItem> menu;
    private View lv;
    private static final int SUBMIT_VERIFICATION_CODE_COMPLETE = 1;
    private static final int GET_VERIFICATION_CODE_COMPLETE = 2;
    private static final int RESULT_ERROR = 3;
    private static final int SERVE_RESULT_COMPLETE = 4;
    private static final int SERVE_RESULT_ERROR = 5;
    private String COUNTRY_NUMBER = "86";
    private EventHandler eh = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {

            if (result == SMSSDK.RESULT_COMPLETE) {
                // 回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    // 提交验证码正确成功
                    handler.sendEmptyMessage(GET_VERIFICATION_CODE_COMPLETE,SmsFragment.this);
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE || event == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE) {
                    // 获取验证码成功
                    handler.sendEmptyMessage(SUBMIT_VERIFICATION_CODE_COMPLETE,SmsFragment.this);
                }
            } else if (result == SMSSDK.RESULT_ERROR) {// 错误情况
                Throwable throwable = (Throwable) data;
                throwable.printStackTrace();
                JSONObject object;
                try {
                    object = new JSONObject(throwable.getMessage());
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putInt("status", object.optInt("status"));// 错误代码
                    bundle.putString("detail", object.optString("detail"));// 错误描述
                    msg.setData(bundle);
                    msg.what = RESULT_ERROR;
                    handler.sendMessage(msg,SmsFragment.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    };




    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sms;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        SMSSDK.initSDK(context,App.SMS_KEY,App.SMS_SECRET);
        initView();
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case SUBMIT_VERIFICATION_CODE_COMPLETE:
                Toast.makeText(context, "获取成功", Toast.LENGTH_SHORT).show();
                break;
            case GET_VERIFICATION_CODE_COMPLETE:
                Toast.makeText(context, "提交成功", Toast.LENGTH_SHORT).show();
                break;
            case RESULT_ERROR:
                Toast.makeText(
                        context,
                        msg.getData().getInt("status")
                                + msg.getData().getString("detail"),
                        Toast.LENGTH_SHORT).show();
                break;
            case SERVE_RESULT_COMPLETE:
                Toast.makeText(context, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                break;
            case SERVE_RESULT_ERROR:
                if (msg.obj == null) {
                    Toast.makeText(context, "请求错误", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "错误码 : " + Integer.parseInt(msg.obj.toString()), Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return false;
    }




    private void initView() {
        phone = (EditText) view.findViewById(R.id.edit_phone);
        submit = (Button) view.findViewById(R.id.btn_submit);
        provingCode = (EditText) view.findViewById(R.id.edit_proving);
        proving = (Button) view.findViewById(R.id.btn_proving);
        dLayout = (DrawerLayout) view.findViewById(R.id.smsdrawer);
        lv = view.findViewById(R.id.mmc_ok);
        (sms_menu = (ListView) view.findViewById(R.id.fragment_sms_menu)).setOnItemClickListener(this);
        menu = new ArrayList<SmsItem>();
        proving.setOnClickListener(this);
        submit.setOnClickListener(this);
        view.findViewById(R.id.btn_serve).setOnClickListener(this);
        view.findViewById(R.id.btn_Voice).setOnClickListener(this);
        // 注册回调
        SMSSDK.registerEventHandler(eh);
        //取对应国家和国家区号
        for (Map.Entry<Character, ArrayList<String[]>> ent : SMSSDK.getGroupedCountryList().entrySet()) {
            ArrayList<String[]> cl = ent.getValue();
            for (String[] paire : cl) {
                menu.add(new SmsItem("国家 (" + paire[0] + ")",paire[1]));
            }
        }
        SmsAdapter adapter = new SmsAdapter(context,menu);
        sms_menu.setAdapter(adapter);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Voice:
                // 获取语音短信
                SMSSDK.getVoiceVerifyCode(COUNTRY_NUMBER, phone.getText().toString().trim());
                break;
            case R.id.btn_submit:
                // 获取文字短信
                SMSSDK.getVerificationCode(COUNTRY_NUMBER, phone.getText().toString().trim());
                break;
            case R.id.btn_proving:
                // 提交验证
                SMSSDK.submitVerificationCode(COUNTRY_NUMBER, phone.getText().toString()
                        .trim(), provingCode.getText().toString().trim());
                break;
            case R.id.btn_serve:
                //服务器验证
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String address = "https://webapi.sms.mob.com/sms/verify";
                        String params = "appkey=" + App.SMS_KEY + "&amp;phone=" + phone.getText().toString().trim() + "&amp;zone="+ COUNTRY_NUMBER + "&amp;&amp;code=" + provingCode.getText().toString().trim();
                        String result = requestData(address, params);
                        if (result != null) {
                            try {
                                JSONObject object = new JSONObject(result);
                                if (object.optInt("status") == 200) {
                                    Message msg = new Message();
                                    msg.obj = "验证成功";
                                    msg.what = SERVE_RESULT_COMPLETE;
                                    handler.sendMessage(msg,SmsFragment.this);
                                }else {
                                    Message msg = new Message();
                                    msg.obj = object.optInt("status");
                                    msg.what = SERVE_RESULT_ERROR;
                                    handler.sendMessage(msg,SmsFragment.this);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else {
                            handler.sendEmptyMessage(SERVE_RESULT_ERROR,SmsFragment.this);
                        }
                    }
                }).start();
                break;
        }
    }

    /**
     * 发起https 请求
     * @param address
     * @param params
     * @return
     */
    private String requestData(String address ,String params){
        HttpURLConnection conn = null;
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager(){
                public X509Certificate[] getAcceptedIssuers(){return null;}
                public void checkClientTrusted(X509Certificate[] certs, String authType){}
                public void checkServerTrusted(X509Certificate[] certs, String authType){}
            }};

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());

            //ip host verify
            HostnameVerifier hv = new HostnameVerifier() {
                @Override
                public boolean verify(String urlHostName, SSLSession session)  {
                    return urlHostName.equals(session.getPeerHost());
                }
            };

            //set ip host verify
            HttpsURLConnection.setDefaultHostnameVerifier(hv);

            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            URL url = new URL(address);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");// POST
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            // set params ;post params
            if (params!=null) {
                conn.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                out.write(params.getBytes(Charset.forName("UTF-8")));
                out.flush();
                out.close();
            }
            conn.connect();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String result = inputStream2String(conn.getInputStream());
                return result;
            } else {
                System.out.println(conn.getResponseCode() + " "+ conn.getResponseMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null)
                conn.disconnect();
        }
        return null;
    }


    private String inputStream2String(InputStream inputStream) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder str = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            str.append(line);
        }
        br.close();
        return str.toString();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SMSSDK.unregisterEventHandler(eh);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        COUNTRY_NUMBER =((TextView)(view.findViewById(R.id.sms_right_menu_item_number))).getText().toString();
        ToastUtil.show(context,"当前区号" + COUNTRY_NUMBER);
        dLayout.closeDrawer(lv);
    }
}

