package iocompany.ideanotevayas.Servicios;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.annotation.Nullable;
import android.widget.Toast;

import iocompany.ideanotevayas.Activities.CrearNota;

/**
 * Created by PC on 14/08/2017.
 */

public class AcelerometroNotaRapida extends Service {


    private long mShakeTime = 500;
    private long mRotationTime = 0;
    //Modificar para Calibrar
    private static final float SHAKE_THRESHOLD = 1.3f;
    //lo puse en 1000 pero estaba en 2000
    private static final int SHAKE_WAIT_TIME_MS = 1000;
    private static final float ROTATION_THRESHOLD = 2.0f;
    private static final int ROTATION_WAIT_TIME_MS = 100;

    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;

    private int movimiento=0;


    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
           /* // Normally we would do some work here, like download a file.
            // For our sample, we just sleep for 5 seconds.
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // Restore interrupt status.
                Thread.currentThread().interrupt();
            }
            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
            stopSelf(msg.arg1);*/
        }
    }

    @Override
    public void onCreate() {
        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();


        //Acelerometro Prueba 2
        SensorManager sensorManager =(SensorManager) getSystemService(SENSOR_SERVICE);

        final Sensor acelerometroSensor =sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SensorEventListener acelerometroSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

                // If sensor is unreliable, then just return
                if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE)
                {
                    return;
                }

               /* Funciona muchos valores que se van viendo

               tv4.setText(
                        "x = " + Float.toString(event.values[0]) + "\n" +
                                "y = " + Float.toString(event.values[1]) + "\n" +
                                "z = " + Float.toString(event.values[2]) + "\n"
                );*/

                //Este funciona Bien Falta calibrar

                if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    detectShake(event);

                }
                //No funciona adecuadamente :/
                else if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                    detectRotation(event);
                }


            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        //Registro el acelerometro
        sensorManager.registerListener(acelerometroSensorListener,
                acelerometroSensor, SensorManager.SENSOR_DELAY_NORMAL);

        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service done Acelerometro", Toast.LENGTH_SHORT).show();
    }
    private void detectShake(SensorEvent event) {
        long now = System.currentTimeMillis();

        if ((now - mShakeTime) > SHAKE_WAIT_TIME_MS) {
            mShakeTime = now;

            float gX = event.values[0] / SensorManager.GRAVITY_EARTH;
            float gY = event.values[1] / SensorManager.GRAVITY_EARTH;
            float gZ = event.values[2] / SensorManager.GRAVITY_EARTH;

            // gForce will be close to 1 when there is no movement
            double gForce = Math.sqrt(gX * gX + gY * gY + gZ * gZ);

            // Change background color if gForce exceeds threshold;
            // otherwise, reset the color
            if (gForce > SHAKE_THRESHOLD) {

               // tv3.setText("Funciona ShakeTime");
                movimiento++;
                Toast.makeText(this, "Acelerometro"+movimiento, Toast.LENGTH_SHORT).show();
                if(movimiento>=3 ){
                    Intent intent1 = new Intent(AcelerometroNotaRapida.this, CrearNota.class);
                    intent1.setAction(Intent.ACTION_VIEW);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent1);
                    movimiento=0;
                }

            }
        }
    }

    /**
     * Detect a rotation in on the GYROSCOPE sensor
     *
     * @param event
     */
    private void detectRotation(SensorEvent event) {
        long now = System.currentTimeMillis();

        if ((now - mRotationTime) > ROTATION_WAIT_TIME_MS) {
            mRotationTime = now;

            // Change background color if rate of rotation around any
            // axis and in any direction exceeds threshold;
            // otherwise, reset the color
            if (Math.abs(event.values[0]) > ROTATION_THRESHOLD ||
                    Math.abs(event.values[1]) > ROTATION_THRESHOLD ||
                    Math.abs(event.values[2]) > ROTATION_THRESHOLD) {

               // tv2.setText("Funciona RotacionTIme");
            }
        }
    }
}
