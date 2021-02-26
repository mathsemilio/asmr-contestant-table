package br.com.mathsemilio.asmrcontestanttable.ui.dialog.dialog.common

import androidx.fragment.app.DialogFragment
import br.com.mathsemilio.asmrcontestanttable.common.di.ControllerCompositionRoot
import br.com.mathsemilio.asmrcontestanttable.ui.MainActivity

abstract class BaseDialogFragment : DialogFragment() {

    private val _compositionRoot by lazy {
        ControllerCompositionRoot((requireActivity() as MainActivity).compositionRoot)
    }
    val compositionRoot get() = _compositionRoot
}