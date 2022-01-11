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

package br.com.mathsemilio.asmrcontestanttable.storage.endpoint

import kotlinx.coroutines.*
import br.com.mathsemilio.asmrcontestanttable.data.dao.*
import br.com.mathsemilio.asmrcontestanttable.domain.model.*

open class ContestantsEndpoint(
    private val contestantsDAO: ContestantDAO?,
    private val weekHighlightsDAO: WeekHighlightsDAO?
) {
    open suspend fun addContestant(contestant: ASMRContestant): Result<Nothing> {
        return withContext(Dispatchers.IO) {
            try {
                contestantsDAO?.insert(contestant)
                Result.Completed(data = null)
            } catch (exception: Exception) {
                Result.Failed(exception = exception)
            }
        }
    }

    open suspend fun updateContestant(contestant: ASMRContestant): Result<Nothing> {
        return withContext(Dispatchers.IO) {
            try {
                contestantsDAO?.update(contestant)
                Result.Completed(data = null)
            } catch (exception: Exception) {
                Result.Failed(exception = exception)
            }
        }
    }

    open suspend fun fetchContestants(): Result<List<ASMRContestant>> {
        return withContext(Dispatchers.IO) {
            try {
                Result.Completed(data = contestantsDAO?.fetchContestants())
            } catch (exception: Exception) {
                Result.Failed(exception = exception)
            }
        }
    }

    open suspend fun deleteAllContestants(): Result<Nothing> {
        return withContext(Dispatchers.IO) {
            try {
                contestantsDAO?.deleteAllContestants()
                weekHighlightsDAO?.deleteAllWeekHighlights()
                Result.Completed(data = null)
            } catch (exception: Exception) {
                Result.Failed(exception = exception)
            }
        }
    }
}
