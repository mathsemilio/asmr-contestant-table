package br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import br.com.mathsemilio.asmrcontestanttable.R
import br.com.mathsemilio.asmrcontestanttable.domain.model.WeekHighlights

class WeekHighlightsListItemViewImpl(inflater: LayoutInflater, parent: ViewGroup?) :
    WeekHighlightsListItemView() {

    private var textViewWeekHighlightsItemWeekNumber: TextView
    private var textViewWeekHighlightsItemContestants: TextView

    init {
        rootView = inflater.inflate(R.layout.week_highlights_list_item, parent, false)
        textViewWeekHighlightsItemWeekNumber =
            findViewById(R.id.text_view_week_highlights_item_week)
        textViewWeekHighlightsItemContestants =
            findViewById(R.id.text_view_week_highlights_item_contestants)
    }

    override fun bindWeekHighlight(weekHighlight: WeekHighlights) {
        textViewWeekHighlightsItemWeekNumber.text = context.getString(
            R.string.week_highlights_item_week,
            weekHighlight.weekNumber
        )
        textViewWeekHighlightsItemContestants.text = context.getString(
            R.string.week_highlights_item_contestants_name,
            weekHighlight.firstContestantName,
            weekHighlight.secondContestantName
        )
    }
}