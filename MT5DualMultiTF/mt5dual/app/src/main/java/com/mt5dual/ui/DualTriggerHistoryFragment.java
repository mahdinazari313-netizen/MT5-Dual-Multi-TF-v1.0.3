package com.mt5dual.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mt5dual.DualApplication;
import com.mt5dual.R;
import com.mt5dual.dual.DualTriggerHistoryEntry;

import java.util.Comparator;
import java.util.List;

/**
 * تب سوم (B-26): مرور فقط-خواندنی تاریخچه Triggerها، مستقل از
 * activeTriggers. برخلاف DualTriggersFragment، این صفحه DualAlarmListener
 * ثبت نمی‌کند - فقط یک Tick دوره‌ای هر ۳۰ ثانیه لازم است تا رکوردهایی
 * که از پنجره Retention خارج شده‌اند، از نمایش خارج شوند حتی وقتی هیچ
 * سیگنال جدیدی نمی‌رسد.
 */
public class DualTriggerHistoryFragment extends Fragment {
    private static final long REFRESH_INTERVAL_MILLIS = 30_000L;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable refreshRunnable = new Runnable() {
        @Override
        public void run() {
            refresh();
            handler.postDelayed(this, REFRESH_INTERVAL_MILLIS);
        }
    };

    private RecyclerView recycler;
    private TextView empty;
    private DualTriggerHistoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dual_trigger_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler = view.findViewById(R.id.triggerHistoryRecyclerView);
        empty = view.findViewById(R.id.triggerHistoryEmptyText);
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new DualTriggerHistoryAdapter();
        recycler.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
        handler.removeCallbacks(refreshRunnable);
        handler.postDelayed(refreshRunnable, REFRESH_INTERVAL_MILLIS);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(refreshRunnable);
    }

    public void refresh() {
        if (recycler == null || !isAdded()) return;
        List<DualTriggerHistoryEntry> entries =
                DualApplication.getInstance().getDualEngineManager().getTriggerHistory();
        entries.sort(Comparator.comparingLong(DualTriggerHistoryEntry::getCreatedAt).reversed());
        adapter.submitEntries(entries);

        boolean isEmpty = entries.isEmpty();
        recycler.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        empty.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
    }
}
