package br.com.magazineluiza.storeprintml.views;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import br.com.magazineluiza.storeprintml.R;
import br.com.magazineluiza.storeprintml.StorePrintML;
import br.com.magazineluiza.storeprintml.models.PrinterVO;
import br.com.magazineluiza.storeprintml.services.StorePrintTask;

/**
 * Created by matheusmendes on 14/03/17.
 */

public class DialogPrinterConfirm extends Dialog {

    private Context mContext;

    private TextView mTvPrinterName;
    private TextView mTvPrinterIP;

    private Button mBtPrint;

    private PrinterVO mPrinter;

    public DialogPrinterConfirm(@NonNull Context context, @StyleRes int themeResId, PrinterVO printerSel) {
        super(context, themeResId);

        mContext = context;
        mPrinter = printerSel;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(false);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.layout_printer_confirm, null);

        setContentView(dialogView);

        loadUI(dialogView);
        loadListeners();
        loadInfo();

    }

    private void loadUI(View view) {

        mTvPrinterName = (TextView) view.findViewById(R.id.tvPrinterName);
        mTvPrinterIP = (TextView) view.findViewById(R.id.tvPrinterIP);
        mBtPrint = (Button) view.findViewById(R.id.btPrint);

    }

    private void loadListeners() {

        mBtPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StorePrintTask _storePrintTask = new StorePrintTask(mContext, mPrinter.getIp());
                _storePrintTask.execute();

                dismiss();

            }
        });

    }

    private void loadInfo() {

        mTvPrinterName.setText(mPrinter.getName());
        mTvPrinterIP.setText(mPrinter.getIp());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        StorePrintML.CALLBACK_PRINTING.dismissDialog();

    }
}
