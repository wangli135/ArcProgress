package xingfeng.arcprogress;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.concurrent.TimeUnit;

import xingfeng.library.ArcProgress;

public class MainActivity extends AppCompatActivity {

    private ArcProgress mArcProgress;

    private int progress;

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mArcProgress.setProgress(msg.what);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mArcProgress=(ArcProgress)findViewById(R.id.arcprogress);
        progress=mArcProgress.getProgress();

    }

    public void startDownload(View view){

        new Thread(new Runnable() {
            @Override
            public void run() {

                while(progress<100){

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    progress+=5;
                    mHandler.sendEmptyMessage(progress);
                }

            }
        }).start();

    }

}
