
package com.demo.mob.utils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.widget.Toast;
@SuppressWarnings("WrongConstant")
public class GetSignatures{
    private static final char hexDigits[] = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f'
    };
    private static Signature asignature[] = null;
    private static PackageInfo b;
    private static MessageDigest m;
    private static Object o;

    /**
     * 进行转换
     */
    public static String toHexString(byte[] bData) {
        StringBuilder sb = new StringBuilder(bData.length * 2);
        for (int i = 0; i < bData.length; i++) {
            sb.append(hexDigits[(bData[i] & 0xf0) >>> 4]);
            Log.e("hex", bData[i] + "---" + 0xf0 + "---" + (bData[i] & 0xf0) + "---" + ((bData[i] & 0xf0) >>> 4)  + "");
            Log.e("hex", Integer.toBinaryString(bData[i]) + "---" + Integer.toBinaryString(0xf0) + "");
            //(bData[i] & 0xf0) 转成二进制比较位数不一样的地方取0然后转成十进制
            //((bData[i] & 0xf0) >>> 4) 不管符号把左边位数换成二进制 先右边移动 4位前面补4个0计算结果
            sb.append(hexDigits[bData[i] & 0x0f]);
        }
        return sb.toString();
    }

    /**
     * 返回MD5
     */
    public static String signatureMD5(Context context, String str) {
        try {
            Signature[] signatures;
            signatures = context.getPackageManager().getPackageInfo(str, 64).signatures;
            MessageDigest digest = MessageDigest.getInstance("MD5");
            if (signatures != null) {
                for (Signature s : signatures)
                    digest.update(s.toByteArray());
            }
            return toHexString(digest.digest());
        } catch (Exception e) {
            Toast.makeText(context, "不存在此包", Toast.LENGTH_SHORT).show();
            return "";
        }
    }
    /**
     * 返回SHA1
     */
    public static String signatureSHA1(Context context,String str) {
        try {
            Signature[] signatures = context.getPackageManager().getPackageInfo(str,64).signatures;

            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            if (signatures != null) {
                for (Signature s : signatures)
                    digest.update(s.toByteArray());
            }
            return toHexString(digest.digest());
        } catch (Exception e) {
            Toast.makeText(context, "不存在此包", Toast.LENGTH_SHORT).show();
            return "";
        }
    }

    /**
     * 返回KeyHash
     */

    public static String signatureKeyHash(Context context,String s){
        int i = 0,j;
        try {
            b = context.getPackageManager().getPackageInfo(s, 64);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }if (b != null) {
            asignature = b.signatures;
            j = asignature.length;
        }else {
            j = 0;
        }if(i >= j){
            Toast.makeText(context, "不存在此包", Toast.LENGTH_SHORT).show();
            return "";
        }else {
            try {
                m = java.security.MessageDigest.getInstance("SHA");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            m.update((asignature[i]).toByteArray());
            o = android.util.Base64.encodeToString(m.digest(), 0);
            return o.toString().trim();
        }
    }
}
