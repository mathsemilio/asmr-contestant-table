package br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist

import br.com.mathsemilio.asmrcontestanttable.domain.model.WeekHighlights

interface WeekHighlightsContract {

    interface Screen {
        fun fetchWeekHighlights()
    }

    interface View {
        interface Listener {
            fun onAddButtonClicked()
        }

        fun bindWeekHighlights(weekHighlights: List<WeekHighlights>)

        fun showProgressIndicator()

        fun hideProgressIndicator()
    }

    interface ListItem {
        fun bindWeekHighlight(weekHighlight: WeekHighlights)
    }
}