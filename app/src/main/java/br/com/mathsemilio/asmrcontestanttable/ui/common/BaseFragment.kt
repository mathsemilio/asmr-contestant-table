package br.com.mathsemilio.asmrcontestanttable.ui.common

import androidx.fragment.app.Fragment
import br.com.mathsemilio.asmrcontestanttable.ui.MainActivity
import br.com.mathsemilio.asmrcontestanttable.common.di.ControllerCompositionRoot

abstract class BaseFragment : Fragment() {

    private val _compositionRoot by lazy {
        ControllerCompositionRoot((requireActivity() as MainActivity).compositionRoot)
    }
    val compositionRoot get() = _compositionRoot
}
