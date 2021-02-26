package br.com.mathsemilio.asmrcontestanttable.ui.common.view

import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.mathsemilio.asmrcontestanttable.ui.MainActivityView
import br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable.ContestantsTableScreenView
import br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist.WeekHighlightsListScreenView

class ViewFactory(private val layoutInflater: LayoutInflater) {

    fun getMainActivityView(parent: ViewGroup?): MainActivityView {
        return MainActivityView(layoutInflater, parent)
    }

    fun getContestantsTableScreenView(container: ViewGroup?): ContestantsTableScreenView {
        return ContestantsTableScreenView(layoutInflater, container)
    }

    fun getWeekHighlightsListScreenView(container: ViewGroup?): WeekHighlightsListScreenView {
        return WeekHighlightsListScreenView(layoutInflater, container)
    }
}