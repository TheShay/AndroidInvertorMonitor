package theshaybi.androidinvertormonitor.classes;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;


import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import theshaybi.androidinvertormonitor.R;
import theshaybi.androidinvertormonitor.BuildConfig;
import theshaybi.androidinvertormonitor.MainActivity;
import theshaybi.androidinvertormonitor.MainActivity;

public class DownloadFile extends AsyncTask<String, Integer, String> {

    ProgressDialog progress;
    Context context;
    File file;
    NotificationCompat.Builder notificationBuilder;
    NotificationManager notificationManager;
    int notificationId = 101011;

    public DownloadFile(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        try {
            progress = new ProgressDialog(context);
            progress.setTitle("Downloading");
            progress.setMessage("Downloading File");
            progress.setIndeterminate(false);
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.show();

            Intent intent = new Intent();
            final PendingIntent pendingIntent = PendingIntent.getActivity(
                    context, 0, intent, 0);
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancelAll();
            notificationBuilder = new NotificationCompat.Builder(context)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setProgress(100, 0, false)
                    .setContentTitle("PIM Status App Update")
//                    .setSmallIcon(R.drawable.download)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(false)
                    .setOngoing(true);

            notificationManager.notify(notificationId, notificationBuilder.build());
            super.onPreExecute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected String doInBackground(String... urls) {
        int count;

        try {
            File SDCardRoot = Environment.getExternalStorageDirectory();

            file = new File(SDCardRoot, "banner.apk");

            if (file.exists()) {
                boolean isdelete = file.delete();
            }

            URL url = new URL(urls[0]);
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.connect();

            int lenghtOfFile = conexion.getContentLength();
            Log.w("DownloadFilesTask", "Length of file: " + lenghtOfFile / 1000 + "Kbytes");

            InputStream input = new BufferedInputStream(url.openStream());
            FileOutputStream output = new FileOutputStream(file);

            byte data[] = new byte[1024];
            long total = 0;

            int percentageDone = -1, latestPercentDone;
            while ((count = input.read(data)) != -1) {
                total += count;
                latestPercentDone = (int) ((total * 100) / lenghtOfFile);
                if (percentageDone != latestPercentDone) {
                    percentageDone = latestPercentDone;
                    publishProgress(latestPercentDone);
                }
                output.write(data, 0, count);
            }

            input.close();
            output.flush();
            output.close();
            conexion.disconnect();
        } catch (IOException e) {
            StackTraceElement x = Thread.currentThread().getStackTrace()[0];
            String exception = "[Exception in " + x.getFileName() + ":" + x.getMethodName() + "]\n" + "[" + e.getLocalizedMessage() + "]";
            e.printStackTrace();
            MainActivity.downloadFile = null;
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... progres) {
        try {
            progress.setProgress(progres[0]);
            notificationBuilder.setContentText("" + progres[0] + "%");
            notificationBuilder.setProgress(100, progres[0], false);
            notificationManager.notify(notificationId, notificationBuilder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            progress.dismiss();
            if (file != null && file.exists()) {

                try {
                    Uri path = Build.VERSION.SDK_INT > Build.VERSION_CODES.M ? FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file) : Uri.fromFile(file);
                    Intent pIntent = new Intent(Intent.ACTION_VIEW, path);
                    pIntent.setDataAndType(path, "application/vnd.android.package-archive");
                    pIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    pIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    Toast.makeText(context, "Installing new Application", Toast.LENGTH_SHORT).show();
                    context.startActivity(pIntent);
                    final PendingIntent pendingIntent = PendingIntent.getActivity(
                            context, 0, pIntent, 0);
                    notificationBuilder.setContentText("Download Complete, Tap to install Update.")
                            .setContentIntent(pendingIntent);
                    notificationManager.notify(notificationId, notificationBuilder.build());
//                    isAppalreadyDownloaded=true;
                } catch (ActivityNotFoundException e) {
                    String exception = "[Exception in DownloadFile:onPostExecute(ActivityNotFoundException)] \n[" + e.getLocalizedMessage() + "]";
                    e.printStackTrace();
                    MainActivity.downloadFile = null;
//                    isAppalreadyDownloaded=false;
                }
            }else {
                MainActivity.downloadFile=null;
            }

        } catch (Exception e) {
            String exception = "[Exception in DownloadFile:onPostExecute] \n[" + e.getLocalizedMessage() + "]";
            MainActivity.downloadFile = null;
            e.printStackTrace();
        }
    }
}// DownloadFilesTask Class