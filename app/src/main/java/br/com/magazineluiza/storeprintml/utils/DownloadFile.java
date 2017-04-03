package br.com.magazineluiza.storeprintml.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import br.com.magazineluiza.storeprintml.R;
import br.com.magazineluiza.storeprintml.interfaces.IAsyncTasks;

/**
 * Created by matheusmendes on 24/03/17.
 */

public class DownloadFile extends AsyncTask<String, Void, File> {

    private final String FOLDER_NAME = "StorePrintML_PDFs";
    private String mFileName;

    private Context mContext;
    private ProgressDialog mProgressDialog;

    private IAsyncTasks mIAsyncTasks;

    public DownloadFile(Context context, String fileName, IAsyncTasks iAsyncTasks) {

        mContext = context;
        mIAsyncTasks = iAsyncTasks;

        if (!fileName.endsWith(".pdf")) {

            fileName = fileName + ".pdf";

        }

        mFileName = fileName;

    }

    @Override
    protected void onPreExecute() {

        mIAsyncTasks.onBeforeExecute();

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage(mContext.getString(R.string.msg_download_pdf));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

    }

    @Override
    protected File doInBackground(String... strings) {

        String fileUrl = strings[0];

        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File folder = new File(extStorageDirectory, FOLDER_NAME);

        if (!folder.exists())
            folder.mkdir();


        File pdfFile = new File(folder, mFileName);

        try{
            pdfFile.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }

        if (!downloadFile(fileUrl, pdfFile)) {
            return null;
        }

        return pdfFile;
    }

    @Override
    protected void onPostExecute(File pdfFile) {

        mProgressDialog.dismiss();

        if (pdfFile != null) {

            mIAsyncTasks.onAfterExecute(pdfFile);

        } else {

            mIAsyncTasks.onErrorExecute(mContext.getString(R.string.msg_download_pdf_failed));

        }

    }

    private boolean downloadFile(String fileUrl, File directory){

        int MEGABYTE = 1024 * 1024;

        try {

            URL url = new URL(fileUrl);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            //urlConnection.setRequestMethod("GET");
            //urlConnection.setDoOutput(true);
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(directory);
            int totalSize = urlConnection.getContentLength();

            byte[] buffer = new byte[MEGABYTE];
            int bufferLength = 0;
            while((bufferLength = inputStream.read(buffer))>0 ){
                fileOutputStream.write(buffer, 0, bufferLength);
            }
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
