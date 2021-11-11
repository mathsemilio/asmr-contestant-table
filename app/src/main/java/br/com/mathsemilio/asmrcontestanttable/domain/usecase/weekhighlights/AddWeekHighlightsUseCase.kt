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

package br.com.mathsemilio.asmrcontestanttable.domain.usecase.weekhighlights

import br.com.mathsemilio.asmrcontestanttable.common.EXCEPTION_GET_WEEK_NUMBER_FAILED
import br.com.mathsemilio.asmrcontestanttable.domain.model.Result
import br.com.mathsemilio.asmrcontestanttable.domain.model.WeekHighlights
import br.com.mathsemilio.asmrcontestanttable.storage.endpoint.WeekHighlightsEndpoint

open class AddWeekHighlightsUseCase(private val endpoint: WeekHighlightsEndpoint?) {

    sealed class AddWeekHighlightsResult {
        object Completed : AddWeekHighlightsResult()
        object Failed : AddWeekHighlightsResult()
    }

    open suspend fun addWeekHighlights(
        firstContestantName: String,
        secondContestantName: String
    ): AddWeekHighlightsResult {
        var weekHighlightsResult: AddWeekHighlightsResult

        val weekHighlights = WeekHighlights(
            id = 0,
            weekNumber = getWeekNumber() ?: throw RuntimeException(EXCEPTION_GET_WEEK_NUMBER_FAILED),
            firstContestantName = firstContestantName,
            secondContestantName = secondContestantName
        )

        endpoint?.addWeekHighlights(weekHighlights).also { result ->
            weekHighlightsResult = when (result) {
                is Result.Completed -> AddWeekHighlightsResult.Completed
                is Result.Failed -> AddWeekHighlightsResult.Failed
                null -> AddWeekHighlightsResult.Failed
            }
        }

        return weekHighlightsResult
    }

    private suspend fun getWeekNumber(): Int? {
        var weekNumber: Int? = 0

        endpoint?.getWeekNumber().also { result ->
            when (result) {
                is Result.Completed -> weekNumber = result.data
                is Result.Failed -> throw RuntimeException(result.exception?.message)
            }
        }

        return weekNumber
    }
}
