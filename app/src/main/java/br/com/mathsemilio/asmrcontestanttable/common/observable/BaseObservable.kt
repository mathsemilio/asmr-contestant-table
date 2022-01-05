/*
Copyright 2021 Matheus Menezes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package br.com.mathsemilio.asmrcontestanttable.common.observable

abstract class BaseObservable<Observer> : Observable<Observer> {

    private val _observers = mutableSetOf<Observer>()

    protected val observers
        get() = _observers.toSet()

    override fun addObserver(observer: Observer) {
        _observers.add(observer)
    }

    override fun removeObserver(observer: Observer) {
        _observers.remove(observer)
    }

    protected inline fun notify(crossinline body: (Observer) -> Unit) {
        observers.forEach { observer -> body(observer) }
    }
}
