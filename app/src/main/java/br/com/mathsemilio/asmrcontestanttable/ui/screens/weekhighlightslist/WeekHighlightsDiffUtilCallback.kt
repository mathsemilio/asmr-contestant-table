package br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist

import androidx.recyclerview.widget.DiffUtil
import br.com.mathsemilio.asmrcontestanttable.domain.model.WeekHighlights

class WeekHighlightsDiffUtilCallback : DiffUtil.ItemCallback<WeekHighlights>() {
    override fun areItemsTheSame(oldItem: WeekHighlights, newItem: WeekHighlights): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: WeekHighlights, newItem: WeekHighlights): Boolean {
        return oldItem == newItem
    }
}