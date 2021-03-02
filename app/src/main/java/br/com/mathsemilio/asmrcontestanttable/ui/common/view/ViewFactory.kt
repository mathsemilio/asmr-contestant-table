package br.com.mathsemilio.asmrcontestanttable.ui.common.view

import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.mathsemilio.asmrcontestanttable.ui.MainActivityView
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addcontestant.AddContestantView
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addweekhighlights.AddWeekHighlightsView
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.contestantdetails.ContestantDetailsView
import br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable.ContestantsTableScreenView
import br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist.WeekHighlightsViewView

class ViewFactory(private val layoutInflater: LayoutInflater) {

    fun getMainActivityView(parent: ViewGroup?) =
        MainActivityView(layoutInflater, parent)

    fun getContestantsTableScreenView(container: ViewGroup?) =
        ContestantsTableScreenView(layoutInflater, container)

    fun getWeekHighlightsListScreenView(container: ViewGroup?) =
        WeekHighlightsViewView(layoutInflater, container)

    fun getAddContestView(container: ViewGroup?) =
        AddContestantView(layoutInflater, container)

    fun getAddWeekHighlightsView(container: ViewGroup?) =
        AddWeekHighlightsView(layoutInflater, container)

    fun getContestantsDetailsView(container: ViewGroup?) =
        ContestantDetailsView(layoutInflater, container)
}