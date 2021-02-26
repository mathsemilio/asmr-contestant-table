package br.com.mathsemilio.asmrcontestanttable.ui.dialog.dialog.contestdeadline

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.dialog.common.BaseDialogFragment

class ContestDeadlineDatePickerDialog : BaseDialogFragment(), DatePickerDialog.OnDateSetListener {

    interface OnContestDateSetListener {
        fun onContestDateSet(year: Int, month: Int, dayOfMonth: Int)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        TODO("Not yet implemented")
    }
}