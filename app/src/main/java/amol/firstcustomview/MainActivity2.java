package amol.firstcustomview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    RandomDotsAnim randomDotsAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        randomDotsAnim = new RandomDotsAnim(this);
        setContentView(randomDotsAnim);

    }

    @Override
    protected void onPause() {
        super.onPause();
        randomDotsAnim.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        randomDotsAnim.resume();
    }
}
