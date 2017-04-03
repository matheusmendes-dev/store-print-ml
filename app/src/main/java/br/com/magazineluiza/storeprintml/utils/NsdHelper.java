package br.com.magazineluiza.storeprintml.utils;

import android.app.Activity;
import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

import br.com.magazineluiza.storeprintml.StorePrintML;
import br.com.magazineluiza.storeprintml.interfaces.IPrinters;
import br.com.magazineluiza.storeprintml.models.PrinterVO;

/**
 * Created by matheusmendes on 14/03/17.
 */

public class NsdHelper {

    private String SERVICE_TYPE = "_ipp._tcp.";

    private NsdManager mNsdManager;
    private NsdManager.ResolveListener mResolveListener;
    private NsdManager.DiscoveryListener mDiscoveryListener;

    private Activity mActivity;

    private IPrinters mIPrinters;

    public NsdHelper(Activity activity, IPrinters iPrinters) {

        mActivity = activity;
        mNsdManager = (NsdManager) activity.getSystemService(Context.NSD_SERVICE);
        mIPrinters = iPrinters;

        initializeNsd();

    }

    private void initializeNsd() {

        initializeDiscoveryListener();
        initializeResolveListener();

    }

    private void initializeDiscoveryListener() {

        mDiscoveryListener = new NsdManager.DiscoveryListener() {
            @Override
            public void onStartDiscoveryFailed(String s, int i) {

            }

            @Override
            public void onStopDiscoveryFailed(String s, int i) {

            }

            @Override
            public void onDiscoveryStarted(String s) {

            }

            @Override
            public void onDiscoveryStopped(String s) {

            }

            @Override
            public void onServiceFound(NsdServiceInfo nsdServiceInfo) {

                Log.i(StorePrintML.TAG, "Service Found\nName: " + nsdServiceInfo.getServiceName() + "\n" +
                                        "Service Type: " + nsdServiceInfo.getServiceType());

                final PrinterVO _printerVO = new PrinterVO();
                _printerVO.setName(nsdServiceInfo.getServiceName());
                _printerVO.setServiceInfo(nsdServiceInfo);

                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mIPrinters.addPrinter(_printerVO);

                    }
                });

            }

            @Override
            public void onServiceLost(NsdServiceInfo nsdServiceInfo) {

                Log.i(StorePrintML.TAG, "Service Lost\nName: " + nsdServiceInfo.getServiceName() + "\n" +
                        "Service Type: " + nsdServiceInfo.getServiceType());

                final PrinterVO _printerVO = new PrinterVO();
                _printerVO.setName(nsdServiceInfo.getServiceName());
                _printerVO.setServiceInfo(nsdServiceInfo);

                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mIPrinters.removePrinter(_printerVO);

                    }
                });

            }
        };

    }

    private void initializeResolveListener() {

        mResolveListener = new NsdManager.ResolveListener() {
            @Override
            public void onResolveFailed(NsdServiceInfo nsdServiceInfo, int i) {

                mIPrinters.onServiceResolved(null);

            }

            @Override
            public void onServiceResolved(NsdServiceInfo nsdServiceInfo) {

                mIPrinters.onServiceResolved(nsdServiceInfo);

            }
        };

    }

    public void discoverServices() {

        mNsdManager.discoverServices(
                SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, mDiscoveryListener);

    }

    public void resolveService(NsdServiceInfo nsdServiceInfo) {

        mNsdManager.resolveService(nsdServiceInfo, mResolveListener);

    }

    public void stopServiceDiscovery() {

        if (mNsdManager != null) {
            mNsdManager.stopServiceDiscovery(mDiscoveryListener);
        }

    }

}
