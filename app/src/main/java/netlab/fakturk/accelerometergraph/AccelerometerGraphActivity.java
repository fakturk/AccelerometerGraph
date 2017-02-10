package netlab.fakturk.accelerometergraph;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class AccelerometerGraphActivity extends AppCompatActivity
{

    GLSurfaceView mView;
    RelativeLayout activityAccelerometer;
    TextView accText;
    float[] acc;

    @Override protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_accelerometer_graph);
        activityAccelerometer = (RelativeLayout) findViewById(R.id.activity_accelerometer_graph);
        accText = (TextView) findViewById(R.id.acc_text);
        accText.setText("Bele vaziyetin icine sokum");
        acc = new float[3];
        acc[0] = AccelerometerGraphJNI.getAccDataX();
        acc[1] = AccelerometerGraphJNI.getAccDataY();
        acc[2] = AccelerometerGraphJNI.getAccDataZ();
        accText.setText(acc[0]+", "+acc[1]+", "+acc[2]+", ");



        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;

        mView = new GLSurfaceView(getApplication());
//        mView = (GLSurfaceView) findViewById(R.id.GLSurfaceView);
        mView.setEGLContextClientVersion(2);
        mView.setRenderer(new GLSurfaceView.Renderer() {
            @Override
            public void onSurfaceCreated(GL10 gl, EGLConfig config) {
                AccelerometerGraphJNI.surfaceCreated();
            }

            @Override
            public void onSurfaceChanged(GL10 gl, int width, int height) {
                AccelerometerGraphJNI.surfaceChanged(width, height);
            }

            @Override
            public void onDrawFrame(GL10 gl) {
                AccelerometerGraphJNI.drawFrame();
            }
        });
        mView.queueEvent(new Runnable() {
            @Override
            public void run() {
                AccelerometerGraphJNI.init(getAssets());
            }
        });
//        addContentView(accText,params);

//        addContentView(mView, params);

    }

    @Override protected void onPause() {
        super.onPause();
        mView.onPause();
        mView.queueEvent(new Runnable() {
            @Override
            public void run() {
                AccelerometerGraphJNI.pause();
            }
        });
    }

    @Override protected void onResume() {
        super.onResume();
        mView.onResume();
        mView.queueEvent(new Runnable() {
            @Override
            public void run() {
                AccelerometerGraphJNI.resume();
            }
        });
    }
}
