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

package br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants

import br.com.mathsemilio.asmrcontestanttable.domain.model.Result
import br.com.mathsemilio.asmrcontestanttable.storage.endpoint.ContestantsEndpoint

open class DeleteContestantsUseCase(private val endpoint: ContestantsEndpoint?) {

    sealed class DeleteContestantsResult {
        object Completed : DeleteContestantsResult()
        object Failed : DeleteContestantsResult()
    }

    suspend fun deleteAllContestants(): DeleteContestantsResult {
        var result: DeleteContestantsResult

        endpoint?.deleteAllContestants().also { endpointResult ->
            result = when (endpointResult) {
                is Result.Completed -> DeleteContestantsResult.Completed
                is Result.Failed -> DeleteContestantsResult.Failed
                null -> DeleteContestantsResult.Failed
            }
        }

        return result
    }
}
