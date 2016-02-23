package com.example.mvc;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;

import io.tjeubaoit.android.mvc.ActivityController;
import io.tjeubaoit.android.mvc.View;
import io.tjeubaoit.android.mvc.annotation.OnAction;
import io.tjeubaoit.android.mvc.annotation.ViewMapping;
import io.tjeubaoit.android.mvc.message.Message;

/**
 * TODO: Class description here.
 *
 * @author <a href="https://github.com/tjeubaoit">tjeubaoit</a>
 */
@ViewMapping(MainActivity.class)
public class MainController extends ActivityController {

    public static final String ACTION_PLUS = "action.PLUS";
    public static final String ACTION_PROGRESS = "action.PROGRESS";

    public MainController(Context context, View view) {
        super(context, view);
    }

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        super.onActivityCreate(savedInstanceState);
        new CountDownTimer(100 * 100, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                post(ACTION_PROGRESS, (10000 - millisUntilFinished) / 100);
            }

            @Override
            public void onFinish() {
                post(ACTION_PROGRESS, 100);
            }
        }.start();
    }

    @OnAction(ACTION_PLUS)
    public void handeActionPlus(Message msg) {
        String[] input = (String[]) msg.body();
        try {
            if (input.length < 2)
                throw new IllegalArgumentException("Not enough params");
            msg.reply(String.valueOf(Integer.parseInt(input[0]) + Integer.parseInt(input[1])));
        } catch (IllegalArgumentException e) {
            msg.fail(e);
        }
    }
}
