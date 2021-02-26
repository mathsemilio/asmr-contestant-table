package br.com.mathsemilio.asmrcontestanttable.ui.dialog.dialog.contestantsnumber

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.dialog.common.BaseDialogFragment

class ContestantsNumberDialog : BaseDialogFragment(), ContestantsNumberContract.View.Listener {

    interface OnContestantsNumberSetListener {
        fun onContestantsNumberSet(number: Int)
    }

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

    override fun onDialogPositiveButtonClicked() {
        TODO("Not yet implemented")
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }
}