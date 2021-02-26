package br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.mathsemilio.asmrcontestanttable.domain.model.WeekHighlights

class WeekHighlightsListAdapter(private val layoutInflater: LayoutInflater) :
    ListAdapter<WeekHighlights, WeekHighlightsListAdapter.ViewHolder>(WeekHighlightsDiffUtilCallback()) {

    class ViewHolder(listItemView: WeekHighlightsListItemView) :
        RecyclerView.ViewHolder(listItemView.rootView) {
        val weekHighlightsListItemView = listItemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val weekHighlightsListItemView = WeekHighlightsListItemView(layoutInflater, parent)
        return ViewHolder(weekHighlightsListItemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.weekHighlightsListItemView.bindWeekHighlight(getItem(position))
    }
}