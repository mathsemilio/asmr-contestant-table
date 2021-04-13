package br.com.mathsemilio.asmrcontestanttable.ui.common.event

sealed class ContestantsModifiedEvent {
    object OnContestantAdded : ContestantsModifiedEvent()
    object OnContestantModified : ContestantsModifiedEvent()
}