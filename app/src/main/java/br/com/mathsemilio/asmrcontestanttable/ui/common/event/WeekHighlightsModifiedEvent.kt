package br.com.mathsemilio.asmrcontestanttable.ui.common.event

sealed class WeekHighlightsModifiedEvent {
    object OnWeekHighlightAdded : WeekHighlightsModifiedEvent()
}