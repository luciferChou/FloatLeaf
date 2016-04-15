package mm.zeus.cn.floatleaf;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FloatLeafActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {

    private static final int REFRESH_PROGRESS = 0x10;
    private int mProgress = 0;
    @Bind(R.id.leaf_loading)
    LeafLoadingView leafLoading;
    @Bind(R.id.fan_pic)
    ImageView fanPic;
    @Bind(R.id.leaf_content)
    RelativeLayout leafContent;
    @Bind(R.id.text_ampair)
    TextView textAmpair;
    @Bind(R.id.seekBar_ampair)
    SeekBar seekBarAmpair;
    @Bind(R.id.seek_content_one)
    LinearLayout seekContentOne;
    @Bind(R.id.text_disparity)
    TextView textDisparity;
    @Bind(R.id.seekBar_distance)
    SeekBar seekBarDistance;
    @Bind(R.id.text_float_time)
    TextView textFloatTime;
    @Bind(R.id.seekBar_float_time)
    SeekBar seekBarFloatTime;
    @Bind(R.id.text_rotate_time)
    TextView textRotateTime;
    @Bind(R.id.seekBar_rotate_time)
    SeekBar seekBarRotateTime;
    @Bind(R.id.clear_progress)
    Button clearProgress;
    @Bind(R.id.add_progress)
    Button addProgress;
    @Bind(R.id.text_progress)
    TextView textProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_leaf);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        RotateAnimation rotateAnimation = AnimationUtils.initRotateAnimation(false, 1500, true, Animation.INFINITE);
        fanPic.startAnimation(rotateAnimation);
        clearProgress.setOnClickListener(this);
//        textAmpair.setText(R.string.current_mplitude);
//        leafLoading.getMiddleAmplitude();
        textAmpair.setText(getString(R.string.current_Disparity,
                leafLoading.getMplitudeDisparity()));

        seekBarAmpair.setOnSeekBarChangeListener(this);
        seekBarAmpair.setProgress(leafLoading.getMiddleAmplitude());
        seekBarAmpair.setMax(50);

        seekBarDistance.setOnSeekBarChangeListener(this);
        seekBarDistance.setProgress(leafLoading.getMplitudeDisparity());
        seekBarDistance.setMax(20);

        seekBarFloatTime.setOnSeekBarChangeListener(this);
        seekBarFloatTime.setMax(5000);
        seekBarFloatTime.setProgress((int) leafLoading.getLeafFloatTime());

        seekBarRotateTime.setOnSeekBarChangeListener(this);
        seekBarRotateTime.setMax(5000);
        seekBarRotateTime.setProgress((int) leafLoading.getLeafFloatTime());


        addProgress.setOnClickListener(this);


    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REFRESH_PROGRESS:
                    if (mProgress < 40) {
                        mProgress += 1;
                        mHandler.sendEmptyMessageDelayed(REFRESH_PROGRESS, new Random().nextInt(800));
                    } else {
                        mProgress += 1;
                        mHandler.sendEmptyMessageDelayed(REFRESH_PROGRESS, new Random().nextInt(1200));
                        leafLoading.setProgress(mProgress);
                    }
                    break;
            }
        }
    };

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar == seekBarAmpair) {
            leafLoading.setMiddleAmplitude(progress);
            textAmpair.setText(getString(R.string.current_mplitude,
                    progress));
        } else if (seekBar == seekBarDistance) {
            leafLoading.setMplitudeDisparity(progress);
            textDisparity.setText(getString(R.string.current_Disparity,
                    progress));
        } else if (seekBar == seekBarFloatTime) {
            leafLoading.setLeafFloatTime(progress);
            textFloatTime.setText(getString(R.string.current_float_time,
                    progress));
        } else if (seekBar == seekBarRotateTime) {
            leafLoading.setLeafRotateTime(progress);
            textRotateTime.setText(getString(R.string.current_rotate_time,
                    progress));
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View v) {
        if (v == clearProgress) {
            leafLoading.setProgress(0);
            mHandler.removeCallbacksAndMessages(null);
            mProgress = 0;
        } else if (v == addProgress) {
            mProgress++;
            leafLoading.setProgress(mProgress);
            textProgress.setText(String.valueOf(mProgress));
        }
    }
}
