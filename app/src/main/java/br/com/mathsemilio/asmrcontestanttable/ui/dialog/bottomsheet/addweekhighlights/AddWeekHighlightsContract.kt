package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addweekhighlights

interface AddWeekHighlightsContract {

    interface BottomSheet {
        fun onAddWeekHighlightsStarted()

        fun onAddWeekHighlightsCompleted()

        fun onAddWeekHighlightsFailed(errorMessage: String)
    }

    interface View {
        interface Listener {
            fun onAddButtonClicked(firstContestantName: String, secondContestantName: String)
        }

        fun changeAddButtonState()

        fun revertAddButtonState()
    }
}