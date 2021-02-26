package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.common

import br.com.mathsemilio.asmrcontestanttable.common.di.ControllerCompositionRoot
import br.com.mathsemilio.asmrcontestanttable.ui.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private val _compositionRoot by lazy {
        ControllerCompositionRoot((requireActivity() as MainActivity).compositionRoot)
    }
    val compositionRoot get() = _compositionRoot
}