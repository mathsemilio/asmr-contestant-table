package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.contestantdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.common.BaseBottomSheetDialogFragment

class ContestantDetailsBottomSheet : BaseBottomSheetDialogFragment(),
    ContestantDetailsContract.View.Listener {

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

    override fun onIncrementTimesSleptButtonClicked() {
        TODO("Not yet implemented")
    }

    override fun onIncrementTimesDidNotSleptButtonClicked() {
        TODO("Not yet implemented")
    }

    override fun onIncrementTimesFeltTiredButtonClicked() {
        TODO("Not yet implemented")
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }
}