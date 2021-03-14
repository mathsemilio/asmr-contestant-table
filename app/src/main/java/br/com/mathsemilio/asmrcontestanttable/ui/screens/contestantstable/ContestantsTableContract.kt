package br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable

import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.ui.ToolbarAction

interface ContestantsTableContract {

    interface Screen {
        fun fetchContestants()

        fun onContestantsFetchStarted()

        fun onContestantsFetchCompleted(contestants: List<ASMRContestant>)

        fun onContestantsFetchFailed(errorMessage: String)

        fun onContestantsDeleteCompleted()

        fun onContestantsDeleteFailed(errorMessage: String)

        fun onToolbarActionClicked(action: ToolbarAction)
    }

    interface View {
        interface Listener {
            fun onAddButtonClicked()
            fun onContestantClicked(contestant: ASMRContestant)
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