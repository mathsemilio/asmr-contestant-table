package br.com.mathsemilio.asmrcontestanttable.ui.common.view

import android.content.Context
import android.view.View
import br.com.mathsemilio.asmrcontestanttable.common.observable.BaseObservable

abstract class BaseObservableView<Listener> : BaseObservable<Listener>(), IView {

    private lateinit var _rootView: View
    override var rootView: View
        get() = _rootView
        set(value) {
            _rootView = value
        }

    protected val context: Context get() = _rootView.context

    protected fun <T: View> findViewById(id: Int) : T = _rootView.findViewById(id)
}