package br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable

import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant

interface ContestantsTableContract {

    interface ListItemView {
        interface Listener {
            fun onContestantClicked(contestant: ASMRContestant)
        }


    }
}