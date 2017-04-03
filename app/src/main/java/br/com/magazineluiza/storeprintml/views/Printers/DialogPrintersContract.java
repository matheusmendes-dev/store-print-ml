package br.com.magazineluiza.storeprintml.views.Printers;

import br.com.magazineluiza.storeprintml.adapters.PrintersAdapter;

/**
 * Created by matheusmendes on 24/03/17.
 */

public interface DialogPrintersContract {

    interface View {

        void setRecyclerAdapter(PrintersAdapter adapter);

        void dismissProgress();

        void showProgress(String msg);

        void showMessage(String msg);

    }

    interface Presenter {

        void onCreate();

        void initViews();

        void initListeners();

        void searchPrinters();

        void dismiss();

    }

}
