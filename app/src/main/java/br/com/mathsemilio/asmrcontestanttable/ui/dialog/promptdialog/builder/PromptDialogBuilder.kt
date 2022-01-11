package br.com.mathsemilio.asmrcontestanttable.ui.dialog.promptdialog.builder

import br.com.mathsemilio.asmrcontestanttable.ui.dialog.promptdialog.controller.PromptDialog

object PromptDialogBuilder {

    private lateinit var title: String
    private lateinit var message: String
    private lateinit var positiveButtonText: String
    private var negativeButtonText: String? = null
    private var isCancelable = false

    fun withTitle(title: String): PromptDialogBuilder {
        this.title = title
        return this
    }

    fun withMessage(message: String): PromptDialogBuilder {
        this.message = message
        return this
    }

    fun withPositiveButtonText(positiveButtonText: String): PromptDialogBuilder {
        this.positiveButtonText = positiveButtonText
        return this
    }

    fun withNegativeButtonText(negativeButtonText: String): PromptDialogBuilder {
        this.negativeButtonText = negativeButtonText
        return this
    }

    fun setIsCancelable(isCancelable: Boolean): PromptDialogBuilder {
        this.isCancelable = isCancelable
        return this
    }

    fun build(): PromptDialog {
        return PromptDialog.newInstance(
            title,
            message,
            positiveButtonText,
            negativeButtonText,
            isCancelable
        )
    }
}
