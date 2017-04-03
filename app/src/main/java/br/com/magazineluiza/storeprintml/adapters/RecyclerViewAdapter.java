package br.com.magazineluiza.storeprintml.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * Created by matheusmendes on 14/03/17.
 */

public abstract class RecyclerViewAdapter<T extends RecyclerView.ViewHolder, O> extends RecyclerView.Adapter<T> {

    public List<O> mObjectList;

    private OnItemClickListener<O> mOnItemClickListener;

    RecyclerViewAdapter(List<O> objectList) {
        this.mObjectList = objectList;
    }

    @Override
    public int getItemCount() {
        return mObjectList.size();
    }

    O getItem(int position) {
        return mObjectList.get(position);
    }

    T createItemClick(View view, final T holder) {

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                mOnItemClickListener.onItemClick(mObjectList.get(holder.getAdapterPosition()));

            }

        });

        return holder;
    }

    public void setOnItemClickListener(OnItemClickListener<O> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener<O> {

        void onItemClick(O object);

    }

}
