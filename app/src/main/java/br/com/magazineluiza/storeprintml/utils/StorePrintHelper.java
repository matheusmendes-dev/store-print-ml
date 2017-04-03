package br.com.magazineluiza.storeprintml.utils;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import static br.com.magazineluiza.storeprintml.StorePrintML.TAG;

/**
 * Created by matheusmendes on 14/03/17.
 */

public class StorePrintHelper {

    public static File PDF_FILE;

    public static final String SUCCESSFULLY_SENT = "Successfully sent to printer";

    public static final int BUFFER_SIZE = 3000;

    private String mIpAddress;
    private int mPort = 9100;

    public StorePrintHelper(String ipAddress) {

        mIpAddress = ipAddress;

    }

    public String sendPDFFileToPrinter() {

        DataOutputStream outToServer = null;
        Socket clientSocket;
        String result;

        InputStream fileInputStream = null;

        try {

            fileInputStream = new FileInputStream(PDF_FILE);

            clientSocket = new Socket(mIpAddress, mPort);
            outToServer = new DataOutputStream(clientSocket.getOutputStream());

            byte[] buffer = new byte[BUFFER_SIZE];
            while (fileInputStream.read(buffer) != -1) {

                outToServer.write(buffer);

            }

            outToServer.flush();

            result = SUCCESSFULLY_SENT;

        } catch (ConnectException connectException){

            Log.e(TAG, connectException.toString());
            result = connectException.toString();

        } catch (UnknownHostException unknownHostException) {

            Log.e(TAG, unknownHostException.toString());
            result = unknownHostException.toString();

        } catch (IOException ioException) {

            Log.e(TAG, ioException.toString());
            result = ioException.toString();

        } finally{

            try {
                if (outToServer!=null){
                    outToServer.close();
                }
                if (fileInputStream!=null){
                    fileInputStream.close();
                }
            }catch (IOException ioException){
                result = ioException.toString();
            }

        }

        return result;

    }

}
