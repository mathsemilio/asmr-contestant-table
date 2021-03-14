package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addweekhighlights

interface AddWeekHighlightsContract {

    interface View {
        interface Listener {
            fun onAddButtonClicked(firstContestantName: String, secondContestantName: String)
        }

        fun changeAddButtonState()

        fun revertAddButtonState()
    }
}