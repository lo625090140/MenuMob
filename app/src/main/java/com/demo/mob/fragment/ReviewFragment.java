package com.demo.mob.fragment;

import android.os.Bundle;
import android.os.Message;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.demo.mob.activity.R;
import com.demo.mob.utils.BaseFragment;
import com.demo.mob.utils.GetSignatures;

import cn.sharerec.recorder.OnRecorderStateListener;
import cn.sharerec.recorder.Recorder;
import cn.sharerec.recorder.impl.SystemRecorder;
import cn.sharerec.recorder.impl.ViewRecorder;

/**
 * Created by chenjt on 2017/1/11.
 */

public class ReviewFragment extends BaseFragment{
    private EditText etPkg;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_review;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        init();
    }

    private void init() {
        etPkg = (EditText) view.findViewById(R.id.etPkg);
        view.findViewById(R.id.md5).setOnClickListener(this);
        view.findViewById(R.id.keyhash).setOnClickListener(this);
        view.findViewById(R.id.sha1).setOnClickListener(this);
        view.findViewById(R.id.copy).setOnClickListener(this);
    }



    @Override
    public boolean handleMessage(Message msg) {

        return false;
    }


    @SuppressWarnings({"deprecation", "WrongConstant"})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.md5:
                ((TextView)view.findViewById(R.id.tvHash)).setText(GetSignatures.signatureMD5(context, etPkg.getText().toString().trim()));
                break;
            case R.id.keyhash:
                ((TextView)view.findViewById(R.id.tvHash)).setText(GetSignatures.signatureKeyHash(context, etPkg.getText().toString().trim()));
                break;
            case R.id.sha1:
                ((TextView)view.findViewById(R.id.tvHash)).setText(GetSignatures.signatureSHA1(context, etPkg.getText().toString().trim()));
                break;
            case R.id.copy:
                ((ClipboardManager)context.getSystemService("clipboard")).setText(((TextView)view.findViewById(R.id.tvHash)).getText());
                break;
        }
    }
}
