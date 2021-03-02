package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addcontestant

interface AddContestantContract {

    interface BottomSheet {
        fun onAddContestantStarted()

        fun onAddContestantsCompleted()

        fun onAddContestantFailed(errorMessage: String)
    }

    interface View {
        interface Listener {
            fun onAddButtonClicked(contestantName: String)
        }

        fun changeAddButtonState()

        fun revertAddButtonState()
    }
}