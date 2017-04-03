package br.com.magazineluiza.storeprintml.views.Printers;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import br.com.magazineluiza.storeprintml.R;
import br.com.magazineluiza.storeprintml.StorePrintML;
import br.com.magazineluiza.storeprintml.adapters.PrintersAdapter;

/**
 * Created by matheusmendes on 14/03/17.
 */

public class DialogPrinters extends Dialog implements DialogPrintersContract.View {

    private DialogPrintersContract.Presenter mPresenter;

    private RecyclerView mRecyclerPrinters;

    private ProgressDialog mProgressDialog;

    public DialogPrinters(@NonNull Context context) {
        super(context);

        setDialogView(context);

    }

    public DialogPrinters(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);

        setDialogView(context);

    }

    private void setDialogView(Context context) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(false);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.layout_printers, null);

        setContentView(dialogView);

        mPresenter = new DialogPrintersPresenter(this, (Activity) context);

        mPresenter.onCreate();

        loadUI(dialogView);
        initViews();
        initListeners();

        mPresenter.searchPrinters();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        StorePrintML.CALLBACK_PRINTING.dismissDialog();

    }

    @Override
    public void dismiss() {

        mPresenter.dismiss();

        super.dismiss();
    }

    private void loadUI(View view) {

        mRecyclerPrinters = (RecyclerView) view.findViewById(R.id.recyclerView);

    }

    private void initViews() {

        Context _context = getContext();

        mProgressDialog = new ProgressDialog(_context);

        mRecyclerPrinters.setHasFixedSize(true);

        LinearLayoutManager _linearLayoutManager = new LinearLayoutManager(_context);
        _linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerPrinters.setLayoutManager(_linearLayoutManager);

        mPresenter.initViews();

    }

    private void initListeners() {

        mPresenter.initListeners();

    }

    @Override
    public void setRecyclerAdapter(PrintersAdapter adapter) {

        mRecyclerPrinters.setAdapter(adapter);

    }

    @Override
    public void dismissProgress() {

        if (mProgressDialog.isShowing()) {

            mProgressDialog.dismiss();

        }

    }

    @Override
    public void showProgress(String msg) {

        if (mProgressDialog == null) {

            mProgressDialog = new ProgressDialog(getContext());

        }

        mProgressDialog.setMessage(msg);
        mProgressDialog.show();

    }

    @Override
    public void showMessage(String msg) {

        AlertDialog.Builder _alert = new AlertDialog.Builder(getContext());
        _alert.setMessage(msg);
        _alert.setNeutralButton("OK", null);
        _alert.show();

    }
}
