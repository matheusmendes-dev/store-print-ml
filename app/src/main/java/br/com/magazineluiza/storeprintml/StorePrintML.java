package br.com.magazineluiza.storeprintml;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import java.io.File;

import br.com.magazineluiza.storeprintml.activities.Printers.PrintersActivity;
import br.com.magazineluiza.storeprintml.interfaces.IAsyncTasks;
import br.com.magazineluiza.storeprintml.interfaces.IPrinting;
import br.com.magazineluiza.storeprintml.services.DownloadFile;
import br.com.magazineluiza.storeprintml.utils.StorePrintHelper;
import br.com.magazineluiza.storeprintml.utils.Utils;

/**
 * Created by matheusmendes on 14/03/17.
 */

public class StorePrintML {

    public static IPrinting CALLBACK_PRINTING;

    public static final String TAG = "StorePrint";

    public static void printPDF(Context context, File pdfFile, IPrinting iPrinting) {

        Utils.requestPermissions((Activity) context);

        CALLBACK_PRINTING = iPrinting;

        if (pdfFile.toString().endsWith(".pdf")) {

            showPrinters(context, pdfFile);

        } else {

            showAlert(context, context.getString(R.string.msg_not_pdf_file));

        }

    }

    public static void printPDF(final Context context, String urlPdf, IPrinting iPrinting) {

        Utils.requestPermissions((Activity) context);

        CALLBACK_PRINTING = iPrinting;

        String _fileName = "contrato.pdf";

        new DownloadFile(context, _fileName, new IAsyncTasks() {
            @Override
            public void onBeforeExecute() {

            }

            @Override
            public void onAfterExecute(Object pdfFile) {

                showPrinters(context, (File) pdfFile);

            }

            @Override
            public void onErrorExecute(String msgError) {

                showAlert(context, msgError);

            }
        }).execute(urlPdf);

    }

    private static void showPrinters(Context context, File pdfFile) {

        StorePrintHelper.PDF_FILE = pdfFile;

        Intent _intent = new Intent(context, PrintersActivity.class);
        context.startActivity(_intent);

    }

    private static void showAlert(Context context, String msg) {

        AlertDialog.Builder _alert = new AlertDialog.Builder(context);
        _alert.setMessage(msg);
        _alert.setNeutralButton("OK", null);
        _alert.show();

    }

}
