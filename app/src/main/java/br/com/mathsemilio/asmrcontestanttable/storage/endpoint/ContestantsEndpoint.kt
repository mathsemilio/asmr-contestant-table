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

import br.com.mathsemilio.asmrcontestanttable.data.dao.ContestantDAO
import br.com.mathsemilio.asmrcontestanttable.data.dao.WeekHighlightsDAO
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.domain.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class ContestantsEndpoint(
    private val contestantsDAO: ContestantDAO?,
    private val weekHighlightsDAO: WeekHighlightsDAO?
) {
    open suspend fun insertContestant(contestant: ASMRContestant): Result<Nothing> {
        return withContext(Dispatchers.IO) {
            try {
                contestantsDAO?.insertData(contestant)
                Result.Completed(data = null)
            } catch (e: Exception) {
                Result.Failed(errorMessage = e.message!!)
            }
        }
    }

    open suspend fun updateContestant(contestant: ASMRContestant): Result<Nothing> {
        return withContext(Dispatchers.IO) {
            try {
                contestantsDAO?.updateData(contestant)
                Result.Completed(data = null)
            } catch (e: Exception) {
                Result.Failed(errorMessage = e.message!!)
            }
        }
    }

    open suspend fun fetchContestants(): Result<List<ASMRContestant>> {
        return withContext(Dispatchers.IO) {
            try {
                Result.Completed(data = contestantsDAO?.fetchContestants())
            } catch (e: Exception) {
                Result.Failed(errorMessage = e.message!!)
            }
        }
    }

    open suspend fun deleteAllContestants(): Result<Nothing> {
        return withContext(Dispatchers.IO) {
            try {
                contestantsDAO?.deleteAllContestants()
                weekHighlightsDAO?.deleteAllWeekHighlights()
                Result.Completed(data = null)
            } catch (e: Exception) {
                Result.Failed(errorMessage = e.message!!)
            }
        }
    }
}