package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.contestantdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.ui.common.view.BaseObservableView

class ContestantDetailsView(layoutInflater: LayoutInflater, container: ViewGroup?) :
    BaseObservableView<ContestantDetailsContract.View.Listener>(), ContestantDetailsContract.View {

    override fun bindContestantsDetails(contestant: ASMRContestant) {
        TODO("Not yet implemented")
    }
}