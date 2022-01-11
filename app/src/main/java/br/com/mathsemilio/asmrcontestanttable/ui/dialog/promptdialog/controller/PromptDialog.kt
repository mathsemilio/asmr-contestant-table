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

import android.os.Bundle
import android.app.Dialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.common.BaseDialogFragment
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.promptdialog.view.PromptDialogView

class PromptDialog : BaseDialogFragment(), PromptDialogControllerEventDelegate {

    companion object {
        private const val ARG_DIALOG_TITLE = "ARG_DIALOG_TITLE"
        private const val ARG_DIALOG_MESSAGE = "ARG_DIALOG_MESSAGE"
        private const val ARG_POSITIVE_BUTTON_TEXT = "ARG_POSITIVE_BUTTON_TEXT"
        private const val ARG_NEGATIVE_BUTTON_TEXT = "ARG_NEGATIVE_BUTTON_TEXT"
        private const val ARG_IS_CANCELABLE = "ARG_IS_CANCELABLE"

        fun newInstance(
            dialogTitle: String,
            dialogMessage: String,
            positiveButtonText: String,
            negativeButtonText: String?,
            isCancelable: Boolean = false
        ): PromptDialog {
            return PromptDialog().apply {
                arguments = Bundle(5).apply {
                    putString(ARG_DIALOG_TITLE, dialogTitle)
                    putString(ARG_DIALOG_MESSAGE, dialogMessage)
                    putString(ARG_POSITIVE_BUTTON_TEXT, positiveButtonText)
                    putString(ARG_NEGATIVE_BUTTON_TEXT, negativeButtonText)
                    putBoolean(ARG_IS_CANCELABLE, isCancelable)
                }
            }
        }
    }

    private lateinit var view: PromptDialogView

    private lateinit var controller: PromptDialogController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = compositionRoot.controllerFactory.promptDialogController
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        view = compositionRoot.viewFactory.promptDialogView

        bindDataToController()

        controller.addDelegate(this)

        return getDialogBuilder().create()
    }

    private fun bindDataToController() {
        controller.run {
            bindView(view)
            bindTitle(requireArguments().getString(ARG_DIALOG_TITLE, ""))
            bindMessage(requireArguments().getString(ARG_DIALOG_MESSAGE, ""))
            bindPositiveButtonText(requireArguments().getString(ARG_POSITIVE_BUTTON_TEXT, ""))
            bindNegativeButtonText(requireArguments().getString(ARG_NEGATIVE_BUTTON_TEXT))
        }
    }

    private fun getDialogBuilder(): MaterialAlertDialogBuilder {
        return MaterialAlertDialogBuilder(requireContext()).apply {
            setView(view.rootView)
            isCancelable = requireArguments().getBoolean(ARG_IS_CANCELABLE, false)
        }
    }

    override fun onPromptDialogButtonClicked() = dismiss()

    override fun onStart() {
        super.onStart()
        controller.onStart()
    }

    override fun onStop() {
        super.onStop()
        controller.onStop()
        controller.removeDelegate()
    }
}
