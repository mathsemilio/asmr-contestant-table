package br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist.view

import br.com.mathsemilio.asmrcontestanttable.domain.model.WeekHighlights
import br.com.mathsemilio.asmrcontestanttable.ui.common.view.BaseObservableView

abstract class WeekHighlightsView : BaseObservableView<WeekHighlightsView.Listener>() {

    interface Listener {
        fun onAddWeekHighlightsButtonClicked()
    }

    abstract fun bindWeekHighlights(weekHighlights: List<WeekHighlights>)

    abstract fun showProgressIndicator()

    abstract fun hideProgressIndicator()
}