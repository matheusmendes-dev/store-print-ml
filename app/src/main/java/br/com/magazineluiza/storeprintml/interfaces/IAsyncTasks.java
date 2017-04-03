package br.com.magazineluiza.storeprintml.interfaces;

/**
 * Created by matheusmendes on 24/03/17.
 */

public interface IAsyncTasks {

    void onBeforeExecute();

    void onAfterExecute(Object anyObject);

    void onErrorExecute(String msgError);

}
