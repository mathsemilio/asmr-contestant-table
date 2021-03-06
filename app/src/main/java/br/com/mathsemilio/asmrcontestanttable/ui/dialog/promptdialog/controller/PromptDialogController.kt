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

package br.com.mathsemilio.asmrcontestanttable.ui.dialog.promptdialog.controller

import br.com.mathsemilio.asmrcontestanttable.common.eventbus.EventPublisher
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.promptdialog.PromptDialogEvent
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.promptdialog.view.PromptDialogView

class PromptDialogController(
    private var eventPublisher: EventPublisher
) : PromptDialogView.Listener {

    private lateinit var view: PromptDialogView

    private var _delegate: PromptDialogControllerEventDelegate? = null
    private val delegate: PromptDialogControllerEventDelegate
        get() = _delegate!!

    override fun onPositiveButtonClicked() {
        delegate.onPromptDialogButtonClicked()
        eventPublisher.publish(PromptDialogEvent.PositiveButtonClicked)
    }

    override fun onNegativeButtonClicked() {
        delegate.onPromptDialogButtonClicked()
        eventPublisher.publish(PromptDialogEvent.NegativeButtonClicked)
    }

    fun bindView(view: PromptDialogView) {
        this.view = view
    }

    fun bindTitle(title: String) = view.setTitle(title)

    fun bindMessage(message: String) = view.setMessage(message)

    fun bindPositiveButtonText(positiveButtonText: String) {
        view.setPositiveButtonText(positiveButtonText)
    }

    fun bindNegativeButtonText(negativeButtonText: String?) {
        view.setNegativeButtonText(negativeButtonText)
    }

    fun addDelegate(delegate: PromptDialogControllerEventDelegate) {
        _delegate = delegate
    }

    fun removeDelegate() {
        _delegate = null
    }

    fun onStart() = view.addObserver(this)

    fun onStop() = view.removeObserver(this)
}
