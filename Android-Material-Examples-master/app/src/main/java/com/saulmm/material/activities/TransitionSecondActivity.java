package com.saulmm.material.activities;

import android.animation.Animator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.LinearLayout;

import com.saulmm.material.R;
import com.saulmm.material.utils.AnimatorAdapter;
import com.saulmm.material.utils.TransitionAdapter;

public class TransitionSecondActivity extends Activity {

    private static final int SCALE_DELAY = 30;
    private LinearLayout rowContainer;
    private View mFabButton1;
    private View mHeader1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_second);
        mFabButton1 = findViewById(R.id.fab_button);
        mHeader1 = findViewById(R.id.activity_transition_header);

        rowContainer = (LinearLayout) findViewById(R.id.row_container2);

        Slide slideExitTransition = new Slide(Gravity.BOTTOM);
        slideExitTransition.excludeTarget(android.R.id.navigationBarBackground, true);
        slideExitTransition.excludeTarget(android.R.id.statusBarBackground, true);


        getWindow().getEnterTransition().addListener(new TransitionAdapter() {

            @Override
            public void onTransitionEnd(Transition transition) {

                super.onTransitionEnd(transition);

                getWindow().getEnterTransition().removeListener(this);

                for (int i = 0; i < rowContainer.getChildCount(); i++) {

                    View rowView = rowContainer.getChildAt(i);
                    rowView.animate().setStartDelay(i * SCALE_DELAY)
                        .scaleX(1).scaleY(1);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

        for (int i = 0; i < rowContainer.getChildCount(); i++) {

            View rowView = rowContainer.getChildAt(i);

            ViewPropertyAnimator propertyAnimator = rowView.animate()
                .setStartDelay(i * SCALE_DELAY)
                .scaleX(0).scaleY(0)
                .setListener(new AnimatorAdapter() {

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        super.onAnimationEnd(animation);
                        finishAfterTransition();
                    }
                });
        }
    }
    public void onFabPressed1(View view) {

        Intent i  = new Intent (TransitionSecondActivity.this,
                TransitionThirdActivity.class);

        ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(
                TransitionSecondActivity.this, Pair.create(mFabButton1, "fab"), Pair.create(mHeader1, "holder1"));

        startActivity(i, transitionActivityOptions.toBundle());
    }

}
