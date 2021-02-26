package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addweekhighlights

interface AddWeekHighlightsContract {

    interface View {

        interface Listener {
            fun onAddButtonClicked(contestantName: String)
        }

        fun changeAddButtonState()

        fun revertAddButtonState()
    }
}