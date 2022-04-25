package com.radwaelsahn.currencyapp.presentation.history

import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater

import androidx.databinding.DataBindingUtil

import android.view.ViewGroup
import androidx.annotation.NonNull
import com.radwaelsahn.currencyapp.data.models.HistoryItem
import com.radwaelsahn.currencyapp.databinding.HistoryListItemBinding

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    private var historyItems: List<HistoryItem>? = null

    @NonNull
    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, i: Int): HistoryViewHolder {
        val historyListItemBinding: HistoryListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            com.radwaelsahn.currencyapp.R.layout.history_list_item, viewGroup, false
        )
        return HistoryViewHolder(historyListItemBinding)
    }

    override fun onBindViewHolder(historyViewHolder: HistoryViewHolder, i: Int) {
        val currentStudent: HistoryItem = historyItems!![i]
        historyViewHolder.historyListItemBinding.item = (currentStudent)
    }

    override fun getItemCount(): Int {
        return historyItems?.size ?: 0
    }

    fun setHistoryList(historyItems: List<HistoryItem>?) {
        this.historyItems = historyItems
        notifyDataSetChanged()
    }

    inner class HistoryViewHolder(historyListItemBinding: HistoryListItemBinding) :
        RecyclerView.ViewHolder(historyListItemBinding.root) {
        internal val historyListItemBinding: HistoryListItemBinding = historyListItemBinding
    }
}