package br.com.mathsemilio.asmrcontestanttable.storage.preferences

interface PreferenceContract {

    fun setContestantsNumber(number: Int)

    fun getContestantsNumber(): Int

    fun clearContestantsNumber()

    fun setContestDeadline(deadline: Long)

    fun getContestDeadline(): Long

    fun clearContestDeadline()
}