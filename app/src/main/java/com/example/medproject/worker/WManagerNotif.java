package com.example.medproject.worker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.medproject.R;

import java.util.concurrent.TimeUnit;

public class WManagerNotif extends Worker {
    public WManagerNotif(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    public static void GuardarNotificacion(Long duracion, Data data, String tag){
        OneTimeWorkRequest noti = new OneTimeWorkRequest.Builder(WManagerNotif.class)
                .setInitialDelay(duracion, TimeUnit.MILLISECONDS).addTag(tag)
                .setInputData(data).build();

        WorkManager instance = WorkManager.getInstance();
        instance.enqueue(noti);
    }

    @NonNull
    @Override
    public Result doWork() {
        String titulo = getInputData().getString("titulo");
        String detalle = getInputData().getString("detalle");
        int id = (int) getInputData().getLong("idnoti", 0);

        mostrarNotificacion(titulo, detalle);

        return Result.success();
    }


    private void mostrarNotificacion(String titulo, String detalle) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("id", "Mi Canal", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "mi_canal_id")
                .setContentTitle(titulo)
                .setContentText(detalle)
                .setSmallIcon(R.mipmap.ic_launcher);

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }

}
