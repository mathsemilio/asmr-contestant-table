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

import android.app.Dialog
import android.os.Bundle
import br.com.mathsemilio.asmrcontestanttable.common.*
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.commom.BaseDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class PromptDialog : BaseDialogFragment(), PromptDialogControllerEventDelegate {

    companion object {
        fun newInstance(
            dialogTitle: String,
            dialogMessage: String,
            positiveButtonText: String,
            negativeButtonText: String?,
            isCancelable: Boolean = false
        ): PromptDialog {
            val args = Bundle(5).apply {
                putString(ARG_DIALOG_TITLE, dialogTitle)
                putString(ARG_DIALOG_MESSAGE, dialogMessage)
                putString(ARG_POSITIVE_BUTTON_TEXT, positiveButtonText)
                putString(ARG_NEGATIVE_BUTTON_TEXT, negativeButtonText)
                putBoolean(ARG_IS_CANCELABLE, isCancelable)
            }
            val promptDialog = PromptDialog()
            promptDialog.arguments = args
            return promptDialog
        }
    }

    private lateinit var controller: PromptDialogController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = compositionRoot.promptDialogController
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogView = compositionRoot.viewFactory.promptDialogView

        controller.run {
            bindView(dialogView)
            bindTitle(requireArguments().getString(ARG_DIALOG_TITLE, ""))
            bindMessage(requireArguments().getString(ARG_DIALOG_MESSAGE, ""))
            bindPositiveButtonText(requireArguments().getString(ARG_POSITIVE_BUTTON_TEXT, ""))
            bindNegativeButtonText(requireArguments().getString(ARG_NEGATIVE_BUTTON_TEXT))
        }

        val dialogBuilder = MaterialAlertDialogBuilder(requireContext()).apply {
            setView(dialogView.rootView)
            isCancelable = requireArguments().getBoolean(ARG_IS_CANCELABLE, false)
        }

        controller.delegate = this

        return dialogBuilder.create()
    }

    override fun onPromptDialogButtonClicked() {
        dismiss()
    }

    override fun onStart() {
        super.onStart()
        controller.onStart()
    }

    override fun onStop() {
        super.onStop()
        controller.onStop()
    }
}
