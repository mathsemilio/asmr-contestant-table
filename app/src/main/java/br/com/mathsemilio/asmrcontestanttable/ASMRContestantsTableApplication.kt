package br.com.mathsemilio.asmrcontestanttable

import android.app.Application
import br.com.mathsemilio.asmrcontestanttable.common.di.CompositionRoot

class ASMRContestantsTableApplication : Application() {

    private lateinit var _compositionRoot: CompositionRoot
    val compositionRoot get() = _compositionRoot

    override fun onCreate() {
        super.onCreate()
        _compositionRoot = CompositionRoot()
    }
}