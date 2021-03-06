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

package br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.update

import br.com.mathsemilio.asmrcontestanttable.domain.model.*
import br.com.mathsemilio.asmrcontestanttable.storage.endpoint.ContestantsEndpoint

open class UpdateContestantTimesFeltTiredUseCase(private val endpoint: ContestantsEndpoint?) {

    sealed class UpdateContestantTimesFeltTiredResult {
        object Completed : UpdateContestantTimesFeltTiredResult()
        object Failed : UpdateContestantTimesFeltTiredResult()
    }

    open suspend fun updateContestantTimesFeltTired(
        contestant: ASMRContestant
    ): UpdateContestantTimesFeltTiredResult {
        var result: UpdateContestantTimesFeltTiredResult

        val updatedContestant = contestant.copy(
            score = contestant.score.inc(),
            timesFeltTired = contestant.timesFeltTired.inc()
        )

        endpoint?.updateContestant(updatedContestant).also { endpointResult ->
            result = when (endpointResult) {
                is Result.Completed -> UpdateContestantTimesFeltTiredResult.Completed
                is Result.Failed -> UpdateContestantTimesFeltTiredResult.Failed
                null -> UpdateContestantTimesFeltTiredResult.Failed
            }
        }

        return result
    }
}
