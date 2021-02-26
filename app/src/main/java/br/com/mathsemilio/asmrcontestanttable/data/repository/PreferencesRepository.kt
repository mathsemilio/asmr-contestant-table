package br.com.mathsemilio.asmrcontestanttable.data.repository

import br.com.mathsemilio.asmrcontestanttable.storage.preferences.PreferenceContract
import br.com.mathsemilio.asmrcontestanttable.storage.preferences.PreferencesSource

class PreferencesRepository(private val preferencesSource: PreferencesSource) : PreferenceContract {

    override fun setContestantsNumber(number: Int) = preferencesSource.setContestantsNumber(number)

    override fun getContestantsNumber() = preferencesSource.getContestantsNumber()

    override fun clearContestantsNumber() = preferencesSource.clearContestantsNumber()

    override fun setContestDeadline(deadline: Long) = preferencesSource.setContestDeadline(deadline)

    override fun getContestDeadline() = preferencesSource.getContestDeadline()

    override fun clearContestDeadline() = preferencesSource.clearContestDeadline()
}