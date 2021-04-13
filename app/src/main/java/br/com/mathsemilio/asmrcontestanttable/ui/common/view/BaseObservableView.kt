package br.com.mathsemilio.asmrcontestanttable.ui.common.view

import br.com.mathsemilio.asmrcontestanttable.common.observable.Observable

abstract class BaseObservableView<Listener> : Observable<Listener>, BaseView() {

    private val listenersSet = mutableSetOf<Listener>()

    override fun addListener(listener: Listener) {
        listenersSet.add(listener)
    }

    override fun removeListener(listener: Listener) {
        listenersSet.remove(listener)
    }

    val listeners get() = listenersSet.toSet()
}