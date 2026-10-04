package com.mt5dual.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mt5dual.R;
import com.mt5dual.dual.DualTriggerHistoryEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * B-26: لیست تخت (بدون گروه‌بندی) رکوردهای DualTriggerHistoryEntry،
 * جدیدترین اول. item_dual_trigger.xml دوباره استفاده می‌شود، اما هر دو
 * دکمه (Silent و Dismiss) همیشه مخفی هستند - یک رکورد تاریخچه مربوط به
 * گذشته است و هیچ‌کدام از این دو عمل برایش معنی ندارد.
 */
public class DualTriggerHistoryAdapter extends RecyclerView.Adapter<DualTriggerHistoryAdapter.ViewHolder> {

    private final List<DualTriggerHistoryEntry> entries = new ArrayList<>();
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

    public void submitEntries(List<DualTriggerHistoryEntry> newEntries) {
        entries.clear();
        if (newEntries != null) entries.addAll(newEntries);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dual_trigger, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DualTriggerHistoryEntry entry = entries.get(position);

        String time = timeFormat.format(new Date(entry.getCreatedAt()));
        String header = holder.itemView.getContext().getString(
                R.string.dual_trigger_history_row_main,
                time, entry.getSymbol(), entry.getTimeframe().name(), entry.getDirection().name());
        holder.mainText.setText(header);

        holder.priceText.setText(holder.itemView.getContext().getString(
                R.string.trigger_row_prices,
                String.valueOf(entry.getReferenceSignal().getPrice()),
                String.valueOf(entry.getIncomingSignal().getPrice())));

        holder.silentButton.setVisibility(View.GONE);
        holder.dismissButton.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mainText;
        final TextView priceText;
        final Button silentButton;
        final Button dismissButton;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainText = itemView.findViewById(R.id.triggerMainText);
            priceText = itemView.findViewById(R.id.triggerPriceText);
            silentButton = itemView.findViewById(R.id.triggerSilentButton);
            dismissButton = itemView.findViewById(R.id.triggerDismissButton);
        }
    }
}
