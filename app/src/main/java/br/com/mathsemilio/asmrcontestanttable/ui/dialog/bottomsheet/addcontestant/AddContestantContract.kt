package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addcontestant

interface AddContestantContract {

    interface View {

        interface Listener {
            fun onAddButtonClicked(contestantName: String)
        }

        fun changeAddButtonState()

        fun revertAddButtonState()
    }
}