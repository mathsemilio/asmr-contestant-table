package br.com.mathsemilio.asmrcontestanttable.storage.preferences

import android.content.Context
import br.com.mathsemilio.asmrcontestanttable.common.*

class PreferencesSource(context: Context) : PreferenceContract {

    private val sharedPreferences = context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    override fun setContestantsNumber(number: Int) = editor.putValue(CONTESTANTS_NUMBER, number)

    override fun getContestantsNumber() = sharedPreferences.getValue(CONTESTANTS_NUMBER, 0)

    override fun clearContestantsNumber() = editor.putValue(CONTESTANTS_NUMBER, 0)

    override fun setContestDeadline(deadline: Long) = editor.putValue(CONTEST_DEADLINE, deadline)

    override fun getContestDeadline() = sharedPreferences.getValue(CONTEST_DEADLINE, 0L)

    override fun clearContestDeadline() = editor.putValue(CONTEST_DEADLINE, 0L)
}