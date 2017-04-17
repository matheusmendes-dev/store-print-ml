package br.com.magazineluiza.storeprintml.activities.Printers;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import br.com.magazineluiza.storeprintml.R;
import br.com.magazineluiza.storeprintml.StorePrintML;
import br.com.magazineluiza.storeprintml.adapters.PrintersAdapter;

public class PrintersActivity extends AppCompatActivity implements PrintersContract.View {

    private PrintersContract.Presenter mPresenter;

    private RecyclerView mRecyclerPrinters;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printers);

        mPresenter = new PrintersPresenter(this);

        mPresenter.onCreate();

        loadUI();
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
    public void finish() {

        mPresenter.dismiss();

        super.finish();
    }

    private void loadUI() {

        mRecyclerPrinters = (RecyclerView) findViewById(R.id.recyclerView);

    }

    private void initViews() {

        mProgressDialog = new ProgressDialog(this);

        mRecyclerPrinters.setHasFixedSize(true);

        LinearLayoutManager _linearLayoutManager = new LinearLayoutManager(this);
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

            mProgressDialog = new ProgressDialog(this);

        }

        mProgressDialog.setMessage(msg);
        mProgressDialog.show();

    }

    @Override
    public void showMessage(String msg) {

        AlertDialog.Builder _alert = new AlertDialog.Builder(this);
        _alert.setMessage(msg);
        _alert.setNeutralButton("OK", null);
        _alert.show();

    }
}
