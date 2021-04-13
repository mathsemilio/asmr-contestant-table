package br.com.mathsemilio.asmrcontestanttable.ui.common.view

import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.mathsemilio.asmrcontestanttable.ui.MainActivityViewImpl
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addcontestant.AddContestantViewImpl
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addweekhighlights.AddWeekHighlightsViewImpl
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.contestantdetails.ContestantDetailsViewImpl
import br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable.view.ContestantsListItemViewImpl
import br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable.view.ContestantsTableScreenViewImpl
import br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist.view.WeekHighlightsListItemViewImpl
import br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist.view.WeekHighlightsViewImpl

class ViewFactory(private val inflater: LayoutInflater) {

    fun getMainActivityView(parent: ViewGroup?) =
        MainActivityViewImpl(inflater, parent)

    fun getContestantsTableScreenView(container: ViewGroup?) =
        ContestantsTableScreenViewImpl(inflater, container, this)

    fun getContestantsListItemView(parent: ViewGroup?) =
        ContestantsListItemViewImpl(inflater, parent)

    fun getWeekHighlightsListScreenView(container: ViewGroup?) =
        WeekHighlightsViewImpl(inflater, container, this)

    fun getWeekHighlightsListItemView(parent: ViewGroup?) =
        WeekHighlightsListItemViewImpl(inflater, parent)

    fun getAddContestView(container: ViewGroup?) =
        AddContestantViewImpl(inflater, container)

    fun getAddWeekHighlightsView(container: ViewGroup?) =
        AddWeekHighlightsViewImpl(inflater, container)

    fun getContestantsDetailsView(container: ViewGroup?) =
        ContestantDetailsViewImpl(inflater, container)
}