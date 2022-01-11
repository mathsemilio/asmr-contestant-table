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

import br.com.mathsemilio.asmrcontestanttable.domain.model.*
import br.com.mathsemilio.asmrcontestanttable.storage.endpoint.WeekHighlightsEndpoint

open class AddWeekHighlightsUseCase(private val endpoint: WeekHighlightsEndpoint?) {

    companion object {
        const val FETCH_WEEK_NUMBER_FAILED = "Failed to fetch the week's number from the database"
    }

    sealed class AddWeekHighlightsResult {
        object Completed : AddWeekHighlightsResult()
        object Failed : AddWeekHighlightsResult()
    }

    open suspend fun addWeekHighlights(contestantsNames: List<String>): AddWeekHighlightsResult {
        var result: AddWeekHighlightsResult

        val weekHighlights = WeekHighlights(
            id = 0,
            weekNumber = getWeekNumber() ?: throw RuntimeException(FETCH_WEEK_NUMBER_FAILED),
            firstContestantName = contestantsNames[0],
            secondContestantName = contestantsNames[1]
        )

        endpoint?.addWeekHighlights(weekHighlights).also { endpointResult ->
            result = when (endpointResult) {
                is Result.Completed -> AddWeekHighlightsResult.Completed
                is Result.Failed -> AddWeekHighlightsResult.Failed
                null -> AddWeekHighlightsResult.Failed
            }
        }

        return result
    }

    private suspend fun getWeekNumber(): Int? {
        var weekNumber: Int? = 0

        endpoint?.getWeekNumber().also { endpointResult ->
            when (endpointResult) {
                is Result.Completed -> weekNumber = endpointResult.data
                is Result.Failed -> throw RuntimeException(endpointResult.exception?.message)
            }
        }

        return weekNumber
    }
}
