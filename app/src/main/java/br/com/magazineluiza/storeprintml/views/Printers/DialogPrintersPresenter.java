package br.com.magazineluiza.storeprintml.views.Printers;

import android.app.Activity;
import android.app.Dialog;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.magazineluiza.storeprintml.R;
import br.com.magazineluiza.storeprintml.StorePrintML;
import br.com.magazineluiza.storeprintml.adapters.PrintersAdapter;
import br.com.magazineluiza.storeprintml.adapters.RecyclerViewAdapter;
import br.com.magazineluiza.storeprintml.interfaces.IPrinters;
import br.com.magazineluiza.storeprintml.models.PrinterVO;
import br.com.magazineluiza.storeprintml.utils.NsdHelper;
import br.com.magazineluiza.storeprintml.views.DialogPrinterConfirm;

/**
 * Created by matheusmendes on 24/03/17.
 */

public class DialogPrintersPresenter implements DialogPrintersContract.Presenter, IPrinters {

    private Activity mActivity;
    private Dialog mDialog;

    private PrintersAdapter mAdapter;
    private List<PrinterVO> mPrinterList;
    private PrinterVO mPrinter;

    private NsdHelper mNsdHelper;

    private DialogPrintersContract.View mView;

    public DialogPrintersPresenter(DialogPrintersContract.View view, Activity activity) {

        mView = view;
        mDialog = (Dialog) view;
        mActivity = activity;

    }

    @Override
    public void onCreate() {

        mNsdHelper = new NsdHelper(mActivity, this);

    }

    @Override
    public void initViews() {

        mPrinterList = new ArrayList<>();
        mAdapter = new PrintersAdapter(mPrinterList);

        mView.setRecyclerAdapter(mAdapter);

    }

    @Override
    public void initListeners() {

        mAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener<PrinterVO>() {
            @Override
            public void onItemClick(PrinterVO printerVO) {

                mView.showProgress(mActivity.getString(R.string.msg_connecting_printing));

                mPrinter = printerVO;

                mNsdHelper.resolveService(printerVO.getServiceInfo());

            }
        });

    }

    @Override
    public void searchPrinters() {

        mNsdHelper.discoverServices();

    }

    @Override
    public void dismiss() {

        mNsdHelper.stopServiceDiscovery();

    }


    // Methods interface IPrinters...
    @Override
    public void addPrinter(PrinterVO printer) {

        boolean add = true;

        for (PrinterVO printerVO : mPrinterList) {

            if (printerVO.getName().equals(printer.getName())) {

                add = false;
                break;
            }
        }

        if (add) {
            mPrinterList.add(printer);
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void removePrinter(PrinterVO printer) {

        Log.i(StorePrintML.TAG, "Impressora removida\n" +
                "Name: " + printer.getName());

        for (int i = 0; i < mPrinterList.size(); i++) {

            PrinterVO printerVO = mPrinterList.get(i);

            if (printerVO.getName().equals(printer.getName())) {

                mPrinterList.remove(printerVO);

                mAdapter.notifyItemRemoved(i);

                break;
            }
        }

    }

    @Override
    public void onServiceResolved(NsdServiceInfo nsdServiceInfo) {

        mView.dismissProgress();

        if (nsdServiceInfo == null) {

            mPrinter = null;

            mView.showMessage(mActivity.getString(R.string.msg_resolve_failed));

        } else {

            Log.i(StorePrintML.TAG, "Impressora selecionada\n" +
                    "IP: " + nsdServiceInfo.getHost().getHostAddress() + "\n" +
                    "Porta: " + nsdServiceInfo.getPort());

            mPrinter.setIp(nsdServiceInfo.getHost().getHostAddress());

            DialogPrinterConfirm _dialog = new DialogPrinterConfirm(mActivity, R.style.AppThemeDialog, mPrinter);
            _dialog.show();

            mDialog.dismiss();

        }

    }
}
