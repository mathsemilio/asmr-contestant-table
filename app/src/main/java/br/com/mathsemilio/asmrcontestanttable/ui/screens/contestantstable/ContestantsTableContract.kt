package br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable

import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant

interface ContestantsTableContract {

    interface View {

        interface Listener {
            fun onAddButtonClicked()
            fun onContestantClicked(contestant: ASMRContestant)
            fun onNoContestantsRegistered()
        }

        fun bindContestants(contestants: List<ASMRContestant>)

        fun showProgressIndicator()

        fun hideProgressIndicator()
    }

    interface ListItemView {

        interface Listener {
            fun onContestantClicked(contestant: ASMRContestant)
        }

        fun bindContestant(contestant: ASMRContestant, contestantPosition: Int)
    }
}