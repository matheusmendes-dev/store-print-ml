package br.com.magazineluiza.storeprintml.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import br.com.magazineluiza.storeprintml.R;
import br.com.magazineluiza.storeprintml.StorePrintML;
import br.com.magazineluiza.storeprintml.interfaces.IPrinting;
import br.com.magazineluiza.storeprintml.utils.StorePrintHelper;

/**
 * Created by matheusmendes on 15/03/17.
 */

public class StorePrintTask extends AsyncTask<Void, Void, String> {

    private Context mContext;
    private ProgressDialog mProgressDialog;

    private String mIPAddress;

    private IPrinting mIPrinting;

    public StorePrintTask(Context context, String ipAddress) {

        mContext = context;
        mProgressDialog = new ProgressDialog(context);
        mIPAddress = ipAddress;
        mIPrinting = StorePrintML.CALLBACK_PRINTING;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mProgressDialog.setMessage(mContext.getString(R.string.msg_printing_sent));
        mProgressDialog.show();

    }

    @Override
    protected String doInBackground(Void... voids) {

        String _resultPrint = null;

        if (StorePrintHelper.PDF_FILE.exists()) {

            StorePrintHelper _storePrintHelper = new StorePrintHelper(mIPAddress);
            _resultPrint = _storePrintHelper.sendPDFFileToPrinter();

        } else {

            _resultPrint = mContext.getString(R.string.msg_pdf_not_found);

        }

        return _resultPrint;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        mProgressDialog.dismiss();

        Log.i(StorePrintML.TAG, "Result Print: " + result);

        if (!result.equals(StorePrintHelper.SUCCESSFULLY_SENT)) {

            showAlert(result);

            mIPrinting.onError();

        } else {

            showToast(mContext.getString(R.string.msg_printing_sent_successfully));

            mIPrinting.onSucessPrinting();

        }

    }

    private void showAlert(String msg) {

        AlertDialog.Builder _alert = new AlertDialog.Builder(mContext);
        _alert.setMessage(msg);
        _alert.setNeutralButton("OK", null);
        _alert.show();

    }

    private void showToast(String msg) {

        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();

    }

}
