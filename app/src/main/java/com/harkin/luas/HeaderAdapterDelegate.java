package com.harkin.luas;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hannesdorfmann.adapterdelegates.AbsAdapterDelegate;
import com.harkin.luas.network.models.Tram;

import java.util.List;

public class HeaderAdapterDelegate extends AbsAdapterDelegate<List<Tram>> {
    private final LayoutInflater inflater;

    public HeaderAdapterDelegate(LayoutInflater inflater, int viewType) {
        super(viewType);
        this.inflater = inflater;
    }

    @Override public boolean isForViewType(@NonNull List<Tram> trams, int position) {
        Tram tram = trams.get(position);
        return !tram.getDueTime().isEmpty() && tram.getDestination().isEmpty();
    }

    @NonNull @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new HeaderViewHolder(inflater.inflate(R.layout.row_header, parent, false));
    }

    @Override public void onBindViewHolder(@NonNull List<Tram> trams, int position, @NonNull RecyclerView.ViewHolder viewHolder) {
        HeaderViewHolder holder = (HeaderViewHolder) viewHolder;
        Tram tram = trams.get(position);

        holder.message.setText(tram.getDueTime());
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public final TextView message;

        public HeaderViewHolder(View v) {
            super(v);
            message = v.findViewById(R.id.destination);
        }
    }
}
