package br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.mathsemilio.asmrcontestanttable.domain.model.WeekHighlights
import br.com.mathsemilio.asmrcontestanttable.ui.common.view.ViewFactory
import br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist.view.WeekHighlightsListItemViewImpl

class WeekHighlightsListAdapter(private val viewFactory: ViewFactory) :
    ListAdapter<WeekHighlights, WeekHighlightsListAdapter.ViewHolder>(WeekHighlightsDiffUtilCallback()) {

    class ViewHolder(private val listItemView: WeekHighlightsListItemViewImpl) :
        RecyclerView.ViewHolder(listItemView.rootView) {

        fun bind(weekHighlight: WeekHighlights) = listItemView.bindWeekHighlight(weekHighlight)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(viewFactory.getWeekHighlightsListItemView(parent))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}