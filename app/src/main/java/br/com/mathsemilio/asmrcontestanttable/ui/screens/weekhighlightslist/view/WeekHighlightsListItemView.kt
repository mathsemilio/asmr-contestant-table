package br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist.view

import br.com.mathsemilio.asmrcontestanttable.domain.model.WeekHighlights
import br.com.mathsemilio.asmrcontestanttable.ui.common.view.BaseView

abstract class WeekHighlightsListItemView : BaseView() {

    abstract fun bindWeekHighlight(weekHighlight: WeekHighlights)
}