package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.contestantdetails

import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant

interface ContestantDetailsContract {

    interface BottomSheet {
        fun getContestant(): ASMRContestant

        fun bindContestant()
    }

    interface View {
        interface Listener {
            fun onIncrementTimesSleptButtonClicked()
            fun onIncrementTimesDidNotSleptButtonClicked()
            fun onIncrementTimesFeltTiredButtonClicked()
        }

        fun bindContestantsDetails(contestant: ASMRContestant)
    }
}