package br.com.mathsemilio.asmrcontestanttable.ui

import androidx.appcompat.app.AppCompatActivity
import br.com.mathsemilio.asmrcontestanttable.ASMRContestantsTableApplication
import br.com.mathsemilio.asmrcontestanttable.common.di.ActivityCompositionRoot

abstract class BaseActivity : AppCompatActivity() {

    private val _compositionRoot by lazy {
        ActivityCompositionRoot(
            this,
            (application as ASMRContestantsTableApplication).compositionRoot
        )
    }
    val compositionRoot get() = _compositionRoot
}