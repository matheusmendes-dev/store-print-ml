package br.com.magazineluiza.storeprintml.interfaces;

import android.net.nsd.NsdServiceInfo;

import br.com.magazineluiza.storeprintml.models.PrinterVO;

/**
 * Created by matheusmendes on 14/03/17.
 */

public interface IPrinters {

    void addPrinter(PrinterVO printer);

    void removePrinter(PrinterVO printer);

    void onServiceResolved(NsdServiceInfo nsdServiceInfo);

}
