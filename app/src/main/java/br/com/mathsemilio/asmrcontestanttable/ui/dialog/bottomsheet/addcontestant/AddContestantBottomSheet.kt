package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addcontestant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.common.BaseBottomSheetDialogFragment

class AddContestantBottomSheet : BaseBottomSheetDialogFragment(), AddContestantContract.View.Listener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onAddButtonClicked(contestantName: String) {
        TODO("Not yet implemented")
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }
}