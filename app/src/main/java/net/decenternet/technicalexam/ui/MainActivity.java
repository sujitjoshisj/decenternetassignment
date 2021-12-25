package net.decenternet.technicalexam.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import net.decenternet.technicalexam.R;
import net.decenternet.technicalexam.ui.tasks.TasksActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView ivBrandingLogo;
    public static Boolean theCatalyst = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivBrandingLogo = (ImageView) findViewById(R.id.ivBrandingLogo);

        /**
         * Tasks
         * 1. Change the text "Put your favorite quote here" with your own quote.
         * 2. Set any image that best illustrate the quote to ivBrandingLogo.
         * 3. Display this screen for 3 seconds, then redirect to TasksActivity and close this MainActivity.
         */


    }

    @Override
    protected void onResume() {
        super.onResume();
        animate(ivBrandingLogo);
    }

    private void animate(final ImageView imageView) {

        int fadeInDuration = 3000; // Configure time values

        imageView.setVisibility(View.INVISIBLE);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); // add this
        fadeIn.setDuration(fadeInDuration);

        AnimationSet animation = new AnimationSet(false); // change to false
        animation.addAnimation(fadeIn);
        animation.setRepeatCount(1);
        imageView.setAnimation(animation);
        Log.i("Animation Start", "TaskMainActivity");
        animation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                Log.i("Animation End", "TaskMainActivity");
                startActivity(new Intent(MainActivity.this, TasksActivity.class));
            }

            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }
        });

    }
}