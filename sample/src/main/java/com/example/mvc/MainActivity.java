package com.example.mvc;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import io.tjeubaoit.android.mvc.MvcActivity;
import io.tjeubaoit.android.mvc.annotation.OnAction;
import io.tjeubaoit.android.mvc.async.AsyncResult;
import io.tjeubaoit.android.mvc.async.Handler;
import io.tjeubaoit.android.mvc.message.Message;

public class MainActivity extends MvcActivity {

    static final String TAG = MainActivity.class.getSimpleName();

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textResult = (TextView) findViewById(R.id.text_result);
        final EditText edit1 = (EditText) findViewById(R.id.edit1);
        final EditText edit2 = (EditText) findViewById(R.id.edit2);

        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setIndeterminate(false);
        pd.setCancelable(false);

        findViewById(R.id.start_calculate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] input = new String[] {edit1.getText().toString(), edit2.getText().toString()};
                post(MainController.ACTION_PLUS, input, new Handler<AsyncResult<String>>() {
                    @Override
                    public void handle(AsyncResult<String> event) {
                        if (event.failed()) {
                            Log.e(TAG, "Error", event.cause());
                        } else {
                            textResult.setText(event.result());
                        }
                    }
                });
            }
        });
        Log.d(TAG, "Main thread: " + Thread.currentThread().getId());
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (pd.isShowing()) {
            pd.dismiss();
        }
    }

    @OnAction(MainController.ACTION_PROGRESS)
    public void onProgress(Message msg) {
        Log.d(TAG, msg.body().toString() + "," + Thread.currentThread().getId());
        if (!pd.isShowing()) {
            pd.setMax(100);
            pd.setProgress(0);
            pd.show();
        }
        pd.setProgress((int) msg.body());
        if (pd.getProgress() == 100) {
            pd.dismiss();
        }
    }
}
