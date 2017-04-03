package br.com.magazineluiza.storeprintml.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.magazineluiza.storeprintml.R;
import br.com.magazineluiza.storeprintml.models.PrinterVO;

/**
 * Created by matheusmendes on 14/03/17.
 */

public class PrintersAdapter extends RecyclerViewAdapter<PrintersAdapter.ViewHolder, PrinterVO> {

    public PrintersAdapter(List<PrinterVO> objectList) {
        super(objectList);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvPrinterName;

        public ViewHolder(View itemView) {
            super(itemView);

            tvPrinterName = (TextView) itemView.findViewById(R.id.tvPrinterName);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View _view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_printers, parent, false);

        return createItemClick(_view, new ViewHolder(_view));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        PrinterVO _printer = getItem(position);

        holder.tvPrinterName.setText(_printer.getName());

    }

}
