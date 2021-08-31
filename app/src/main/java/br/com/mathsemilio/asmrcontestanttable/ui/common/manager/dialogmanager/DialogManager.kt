package br.com.mathsemilio.asmrcontestanttable.ui.common.manager.dialogmanager

import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant

interface DialogManager {
    fun showAddContestantBottomSheet()

    fun showAddWeekHighlightsBottomSheet()

    fun showContestantDetailsBottomSheet(contestant: ASMRContestant)

    fun showResetContestDialog()
}