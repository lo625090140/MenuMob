package com.demo.mob.utils;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by chenjt on 2017/1/18.
 */

public class InitShareSDK {

    public static void Init(HashMap<String, String> map,String name) {
        try {
            Method method = PlatformInit.class.getMethod(name, HashMap.class);
            method.setAccessible(true);
            method.invoke(PlatformInit.class,map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class PlatformInit {
        public static void SinaWeibo(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "3");
            plat.put("SortId", "1");
            plat.put("AppKey", "568898243");
            plat.put("AppSecret", "38a4f8204cc784f81f9f0daaf31e02e3");
            plat.put("RedirectUrl", "http://www.sharesdk.cn");
            plat.put("ShareByAppClient", !map.isEmpty() ? map.get("ShareByAppClient") != null ? map.get("ShareByAppClient") : "true" : "true");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("SinaWeibo", plat);
        }

        public static void TencentWeibo(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "2");
            plat.put("SortId", "2");
            plat.put("AppKey", "801307650");
            plat.put("AppSecret", "ae36f4ee3946e1cbb98d6965b0b2ff5c");
            plat.put("RedirectUrl", "http://sharesdk.cn");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("TencentWeibo", plat);
        }

        public static void QZone(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "1");
            plat.put("SortId", "3");
            plat.put("AppId", "1105558150");
            plat.put("AppKey", "6llIFMQ0MEgd8HHR");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("QZone", plat);
        }

        public static void Wechat(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "4");
            plat.put("SortId", "4");
            plat.put("AppId", "wx4868b35061f87885");
            plat.put("AppSecret", "64020361b8ec4c99936c0e3999a9f249");
            plat.put("BypassApproval", !map.isEmpty() ? map.get("BypassApproval") != null ? map.get("BypassApproval") : "false" : "false");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("Wechat", plat);
        }

        public static void WechatMoments(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "5");
            plat.put("SortId", "5");
            plat.put("AppId", "wx4868b35061f87885");
            plat.put("AppSecret", "64020361b8ec4c99936c0e3999a9f249");
            plat.put("BypassApproval", !map.isEmpty() ? map.get("BypassApproval") != null ? map.get("BypassApproval") : "false" : "false");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("WechatMoments", plat);
        }

        public static void WechatFavorite(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "6");
            plat.put("SortId", "6");
            plat.put("AppId", "wx4868b35061f87885");
            plat.put("AppSecret", "64020361b8ec4c99936c0e3999a9f249");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("WechatFavorite", plat);
        }

        public static void QQ(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "7");
            plat.put("SortId", "7");
            plat.put("AppId", "1105558150");
            plat.put("AppKey", "6llIFMQ0MEgd8HHR");
            plat.put("ShareByAppClient", !map.isEmpty() ? map.get("ShareByAppClient") != null ? map.get("ShareByAppClient") : "true" : "true");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("QQ", plat);
        }

        public static void Facebook(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "8");
            plat.put("SortId", "8");
            plat.put("ConsumerKey", "325399241129761");
            plat.put("ConsumerSecret", "f0db2bbda171638ab0f2a940a8cca1fd");
            plat.put("RedirectUrl", "https://www.baidu.com");
            plat.put("ShareByAppClient", !map.isEmpty() ? map.get("ShareByAppClient") != null ? map.get("ShareByAppClient") : "true" : "true");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("Facebook", plat);
        }

        public static void Twitter(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "9");
            plat.put("SortId", "9");
            plat.put("ConsumerKey", "PPCP1NCxh5TtGxT7lAVLFUiUO");
            plat.put("ConsumerSecret", "MhivRvNnzqoLCPrXPE5JajCeNUTt8yfM2gSnpHYO7GzFm3qjz1");
            plat.put("CallbackUrl", "https://www.baidu.com");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("Twitter", plat);
        }

        public static void Renren(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "10");
            plat.put("SortId", "10");
            plat.put("AppId", "226427");
            plat.put("ApiKey", "fc5b8aed373c4c27a05b712acba0f8c3");
            plat.put("SecretKey", "f29df781abdd4f49beca5a2194676ca4");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("Renren", plat);
        }

        public static void KaiXin(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "11");
            plat.put("SortId", "11");
            plat.put("AppKey", "358443394194887cee81ff5890870c7c");
            plat.put("AppSecret", "da32179d859c016169f66d90b6db2a23");
            plat.put("RedirectUrl", "http://www.sharesdk.cn");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("KaiXin", plat);
        }

        public static void Email(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "12");
            plat.put("SortId", "12");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("Email", plat);
        }

        public static void ShortMessage(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "13");
            plat.put("SortId", "13");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("ShortMessage", plat);
        }

        public static void Douban(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "16");
            plat.put("SortId", "16");
            plat.put("AppKey", "031a96a3aa8b28af094fc3eaffa17a0d");
            plat.put("Secret", "2e675e730571b75d");
            plat.put("RedirectUrl", "http://mob.com");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("Douban", plat);
        }

        public static void YouDao(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "17");
            plat.put("SortId", "17");
            plat.put("HostType", "product");
            plat.put("ConsumerKey", "dcde25dca105bcc36884ed4534dab940");
            plat.put("ConsumerSecret", "d98217b4020e7f1874263795f44838fe");
            plat.put("RedirectUri", "http://www.sharesdk.cn/");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("YouDao", plat);
        }

        public static void Evernote(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "19");
            plat.put("SortId", "19");
            plat.put("HostType", "sandbox");
            plat.put("ConsumerKey", "sharesdk-7807");
            plat.put("ConsumerSecret", "d05bf86993836004");
            plat.put("ShareByAppClient", !map.isEmpty() ? map.get("ShareByAppClient") != null ? map.get("ShareByAppClient") : "true" : "true");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("Evernote", plat);
        }

        public static void LinkedIn(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "20");
            plat.put("SortId", "20");
            plat.put("ApiKey", "81ebia7s2o62ou");
            plat.put("SecretKey", "Z3E7qvg3yY47qBww");
            plat.put("RedirectUrl", "https://www.baidu.com");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("LinkedIn", plat);
        }

        public static void GooglePlus(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "21");
            plat.put("SortId", "21");
            plat.put("ClientID", "682795613743-cfo3a8r4va0ujja7qofr54hobt2kv3cq.apps.googleusercontent.com");
            plat.put("RedirectUrl", "http://localhost");
            plat.put("ShareByAppClient", "false");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("GooglePlus", plat);
        }

        public static void FourSquare(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "22");
            plat.put("SortId", "22");
            plat.put("ClientID", "G0ZI20FM30SJAJTX2RIBGD05QV1NE2KVIM2SPXML2XUJNXEU");
            plat.put("ClientSecret", "3XHQNSMMHIFBYOLWEPONNV4DOTCDBQH0AEMVGCBG0MZ32XNU");
            plat.put("RedirectUrl", "http://www.sharesdk.cn");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("FourSquare", plat);
        }

        public static void Pinterest(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "23");
            plat.put("SortId", "23");
            plat.put("ClientId", "1432928");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("Pinterest", plat);
        }

        public static void Flickr(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "24");
            plat.put("SortId", "24");
            plat.put("AppKey", "33d833ee6b6fca49943363282dd313dd");
            plat.put("AppSecret", "3a2c5b42a8fbb8bb");
            plat.put("RedirectUrl", "http://www.sharesdk.cn");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("Flickr", plat);
        }

        public static void Tumblr(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "25");
            plat.put("SortId", "25");
            plat.put("OAuthConsumerKey", "2QUXqO9fcgGdtGG1FcvML6ZunIQzAEL8xY6hIaxdJnDti2DYwM");
            plat.put("SecretKey", "3Rt0sPFj7u2g39mEVB3IBpOzKnM3JnTtxX2bao2JKk4VV1gtNo");
            plat.put("CallbackUrl", "http://sharesdk.cn");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("Tumblr", plat);
        }

        public static void Dropbox(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "26");
            plat.put("SortId", "26");
            plat.put("AppKey", "i5vw2mex1zcgjcj");
            plat.put("AppSecret", "3i9xifsgb4omr0s");
            plat.put("RedirectUrl", "https://www.sharesdk.cn");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("Dropbox", plat);
        }

        public static void VKontakte(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "27");
            plat.put("SortId", "27");
            plat.put("ApplicationId", "3921561");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("VKontakte", plat);
        }

        public static void Instagram(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "28");
            plat.put("SortId", "28");
            plat.put("ClientId", "ff68e3216b4f4f989121aa1c2962d058");
            plat.put("ClientSecret", "1b2e82f110264869b3505c3fe34e31a1");
            plat.put("RedirectUrl", "http://sharesdk.cn");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("Instagram", plat);
        }

        public static void Yixin(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "29");
            plat.put("SortId", "29");
            plat.put("AppId", "yx0d9a9f9088ea44d78680f3274da1765f");
            plat.put("BypassApproval", "true");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("Yixin", plat);
        }

        public static void YixinMoments(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "30");
            plat.put("SortId", "30");
            plat.put("AppId", "yx0d9a9f9088ea44d78680f3274da1765f");
            plat.put("BypassApproval", "true");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("YixinMoments", plat);
        }

        public static void Mingdao(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "31");
            plat.put("SortId", "31");
            plat.put("AppKey", "EEEE9578D1D431D3215D8C21BF5357E3");
            plat.put("AppSecret", "5EDE59F37B3EFA8F65EEFB9976A4E933");
            plat.put("RedirectUrl", "http://sharesdk.cn");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("Mingdao", plat);
        }

        public static void Line(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "32");
            plat.put("SortId", "32");
            plat.put("ChannelID", "1477692153");
            plat.put("ChannelSecret", "f30c036370f2e04ade71c52eef73a9af");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("Line", plat);
        }

        public static void KakaoTalk(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "33");
            plat.put("SortId", "33");
            plat.put("AppKey", "48d3f524e4a636b08d81b3ceb50f1003");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("KakaoTalk", plat);
        }

        public static void KakaoStory(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "34");
            plat.put("SortId", "34");
            plat.put("AppKey", "48d3f524e4a636b08d81b3ceb50f1003");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("KakaoStory", plat);
        }

        public static void WhatsApp(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "35");
            plat.put("SortId", "35");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("WhatsApp", plat);
        }

        public static void Bluetooth(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "36");
            plat.put("SortId", "36");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("Bluetooth", plat);
        }

        public static void Pocket(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "37");
            plat.put("SortId", "37");
            plat.put("ConsumerKey", "32741-389c565043c49947ba7edf05");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("Pocket", plat);
        }

        public static void Instapaper(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "38");
            plat.put("SortId", "38");
            plat.put("ConsumerKey", "4rDJORmcOcSAZL1YpqGHRI605xUvrLbOhkJ07yO0wWrYrc61FA");
            plat.put("ConsumerSecret", "GNr1GespOQbrm8nvd7rlUsyRQsIo3boIbMguAl9gfpdL0aKZWe");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("Instapaper", plat);
        }

        public static void FacebookMessenger(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "39");
            plat.put("SortId", "39");
            plat.put("AppId", "107704292745179");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("FacebookMessenger", plat);
        }

        public static void Alipay(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "52");
            plat.put("SortId", "52");
            plat.put("AppId", "2016092701983584");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("Alipay", plat);
        }

        public static void AlipayMoments(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "51");
            plat.put("SortId", "51");
            plat.put("AppId", "2016092701983584");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("AlipayMoments", plat);
        }
        public static void Dingding(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "55");
            plat.put("SortId", "55");
            plat.put("AppId", "dingoanxyrpiscaovl4qlw");
            plat.put("BypassApproval", !map.isEmpty() ? map.get("BypassApproval") != null ? map.get("BypassApproval") : "false" : "false");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("Dingding", plat);
        }
        public static void Meipai(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "54");
            plat.put("SortId", "54");
            plat.put("ClientID", "1089867596");
            plat.put("AppSecret", "y9ym6rrjjijtkt23qtsc");
            plat.put("ShareByAppClient", !map.isEmpty() ? map.get("ShareByAppClient") != null ? map.get("ShareByAppClient") : "true" : "true");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("Meipai", plat);
        }
        public static void Youtube(HashMap<String, String> map) {
            HashMap<String, Object> plat = new HashMap<String, Object>();
            plat.put("Id", "53");
            plat.put("SortId", "53");
            plat.put("ClientID", "370141748022-bicrnsjfiije93bvdt63dh3728m4shas.apps.googleusercontent.com");
            plat.put("AppSecret", "AIzaSyAO06g-0TDpHcsXXO918a7QE3Zdct2bB5E");
            plat.put("RedirectUrl", "http://localhost");
            plat.put("ShareByAppClient", !map.isEmpty() ? map.get("ShareByAppClient") != null ? map.get("ShareByAppClient") : "true" : "true");
            plat.put("Enable", "true");
            ShareSDK.setPlatformDevInfo("Youtube", plat);
        }

    }
}
