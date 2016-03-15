package com.example.administrator.processdialogtest;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    final static int MAX_PROGRESS =100;
    private int[] data = new int[50];
    int progressStatus =0;
    int hasData=0;
    ProgressDialog pd1,pd2;
    Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            if(msg.what == 0x123)
            {
                pd2.setProgress(progressStatus);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void showSpinner(View source)
    {
        ProgressDialog.show(this, "The task is in execution...", "The task is in execution,pls waiting...", false, true);

    }
    public void showIndeterminate(View source)
    {
        pd1 = new ProgressDialog(MainActivity.this);
        pd1.setTitle("The task is in execution...");
        pd1.setCancelable(true);
        pd1.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd1.setIndeterminate(true);
        pd1.show();
        new Thread()
        {
            public void run()
            {
                while(progressStatus < MAX_PROGRESS)
                {
                    progressStatus = MAX_PROGRESS * doWork()/data.length;
                    handler.sendEmptyMessage(0x123);
                }
                if(progressStatus >= MAX_PROGRESS)
                {
                    pd2.dismiss();
                }
            }
        }.start();
    }
    public int doWork()
    {
        data[hasData++] = (int)(Math.random()*100);
        try
        {
            Thread.sleep(100);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        return hasData;
    }
}
