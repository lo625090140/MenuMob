package com.demo.mob.fragment;

import android.os.Bundle;
import android.os.Message;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.demo.mob.activity.R;
import com.demo.mob.utils.BaseFragment;
import com.demo.mob.utils.ToastUtil;

import cn.sharerec.recorder.OnRecorderStateListener;
import cn.sharerec.recorder.Recorder;
import cn.sharerec.recorder.impl.SystemRecorder;
import cn.sharerec.recorder.impl.ViewRecorder;

/**
 * Created by chenjt on 2017/1/11.
 */

public class RecFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {
    private EditText path;
    private SystemRecorder recorder;
    private ViewRecorder recorders;
    private int type = 0;
    private RadioGroup rg;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rec;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        init();
    }

    private void init() {
        path = (EditText) view.findViewById(R.id.path_rec);
        view.findViewById(R.id.start_rec).setOnClickListener(this);
        view.findViewById(R.id.stop_rec).setOnClickListener(this);
        view.findViewById(R.id.pause_rec).setOnClickListener(this);
        view.findViewById(R.id.restore_rec).setOnClickListener(this);
        (rg =((RadioGroup) view.findViewById(R.id.tvExpl_rec))).setOnCheckedChangeListener(this);
    }


    @Override
    public boolean handleMessage(Message msg) {

        return false;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (recorder != null) {
            view.setTag(recorder);
        }
        if (recorders != null) {
            view.setTag(recorders);
        }
    }

    @SuppressWarnings({"deprecation", "WrongConstant"})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.restore_rec:
                if (type == 0) {
                    recorder.resume();
                } else if (type == 1) {
                    recorders.resumeRecorder();
                }
                ToastUtil.show(context, "恢复录制");
                break;
            case R.id.pause_rec:
                if (type == 0) {
                    recorder.pause();
                } else if (type == 1) {
                    recorders.pauseRecorder();
                }
                ToastUtil.show(context, "暂停录制");
                break;
            case R.id.stop_rec:
                if (type == 0) {
                    recorder.stop();
                } else if (type == 1) {
                    recorders.stopRecorder();
                }
                ToastUtil.show(context, "停止录制");
                break;
            case R.id.start_rec:
                //系统录制
                if (type == 0) {
                    if (recorder == null) {
                        recorder = new SystemRecorder(getActivity(), "76684bc49b3", "cc162a0c24a4928e215a4b99ceffb425");
                    } else {
                        recorder = (SystemRecorder) view.getTag();
                    }
                    // 设置视频的最大尺寸
                    recorder.setMaxFrameSize(Recorder.LevelMaxFrameSize.LEVEL_1280_720);
                    // 设置视频的质量（高、中、低）
                    recorder.setVideoQuality(Recorder.LevelVideoQuality.LEVEL_HIGH);
                    // 设置视频的最短时长
                    recorder.setMinDuration(4 * 1000);
                    // 设置视频的输出路径
                    if (!TextUtils.isEmpty(path.getText().toString().trim())) {
                        recorder.setCacheFolder(path.getText().toString().trim());
                    }

                    if (recorder.isAvailable()) {
                        recorder.setOnRecorderStateListener(new OnRecorderStateListener() {
                            @Override
                            public void onStateChange(Recorder s, int i) {
                                if (i == Recorder.STATE_STOPPED) {
                                    // show share page
                                    recorder.setText("Eraser Demo");
                                    recorder.addCustomAttr("score", "5000");
                                    recorder.addCustomAttr("name", "ShareRec Developer");
                                    recorder.addCustomAttr("brand", "hehe!");
                                    recorder.addCustomAttr("level", "10");
                                    recorder.showShare();
                                }
                            }
                        });
                        recorder.start();
                    }else{
                        ToastUtil.show(context,"不支持该手机");
                        return;
                    }
                }
                //View录制
                else if (type == 1) {
                    if (recorders == null) {
                        recorders = new ViewRecorder(getActivity().findViewById(R.id.view), "76684bc49b3", "cc162a0c24a4928e215a4b99ceffb425");
                    } else {
                        recorders = (ViewRecorder) view.getTag();
                    }
                    // 设置视频的最大尺寸
                    recorders.setMaxFrameSize(Recorder.LevelMaxFrameSize.LEVEL_1280_720);
                    // 设置视频的质量（高、中、低）
                    recorders.setVideoQuality(Recorder.LevelVideoQuality.LEVEL_HIGH);
                    // 设置视频的最短时长
                    recorders.setMinDuration(4 * 1000);
                    // 设置视频的输出路径
                    if (!TextUtils.isEmpty(path.getText().toString().trim())) {
                        recorders.setCacheFolder(path.getText().toString().trim());
                    }
                    // 设置是否强制使用软件编码器对视频进行编码（兼容性更高）
                    recorders.setForceSoftwareEncoding(true, true);


                    if (recorders.isAvailable()) {
                        recorders.setOnRecorderStateListener(new OnRecorderStateListener() {
                            @Override
                            public void onStateChange(Recorder s, int i) {
                                if (i == Recorder.STATE_STOPPED) {
                                    // show share page
                                    recorders.setText("Eraser Demo");
                                    recorders.addCustomAttr("score", "5000");
                                    recorders.addCustomAttr("name", "ShareRec Developer");
                                    recorders.addCustomAttr("brand", "hehe!");
                                    recorders.addCustomAttr("level", "10");
                                    recorders.showShare();
                                }
                            }
                        });
                        recorders.startRecorder();
                        // 如果您不知道什么时候您的画面会刷新，则可以使用下面的方式，让录像模块自动抓图，
                        // 否则可以调用onTheEndOfTheFrame()方法来手动驱动录像模块抓图 (If you don't know
                        // when your view will refresh, you can use the following way, let ShareRec
                        // capture the frame automatically. or you can call onTheEndOfTheFrame()
                        // manually when your frame refreshing)
                        recorders.startAuotRefreshRate(15);
                    }else{
                        ToastUtil.show(context,"不支持该手机");
                        return;
                    }
                    ToastUtil.show(context, "启动录制");

                    break;
                }
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.SystemRecorder_rec) {
            type = 0;
        } else if (checkedId == R.id.ViewRecorder_rec) {
            type = 1;
        }
    }
}