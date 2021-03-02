package br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist

import br.com.mathsemilio.asmrcontestanttable.domain.model.WeekHighlights
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.ModelModifiedEvent

interface WeekHighlightsContract {

    interface Screen {
        fun fetchWeekHighlights()

        fun onWeekHighlightsFetchStarted()

        fun onWeekHighlightsFetchCompleted(weekHighlights: List<WeekHighlights>)

        fun onWeekHighlightsFetchFailed(errorMessage: String)

        fun onWeekHighlightsModified(event: ModelModifiedEvent.Event)
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