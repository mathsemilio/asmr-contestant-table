package br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import br.com.mathsemilio.asmrcontestanttable.R
import br.com.mathsemilio.asmrcontestanttable.domain.model.WeekHighlights
import br.com.mathsemilio.asmrcontestanttable.ui.common.view.BaseView

class WeekHighlightsListItemView(layoutInflater: LayoutInflater, parent: ViewGroup?) : BaseView(),
    WeekHighlightsContract.ListItem {

    private lateinit var textViewWeekHighlightsItemWeekNumber: TextView
    private lateinit var textViewWeekHighlightsItemContestants: TextView

    init {
        rootView = layoutInflater.inflate(R.layout.week_highlights_list_item, parent, false)
        initializeViews()
    }

    private fun initializeViews() {
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