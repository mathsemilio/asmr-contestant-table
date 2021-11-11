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

import android.net.Uri
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.domain.model.Result
import br.com.mathsemilio.asmrcontestanttable.storage.endpoint.ContestantsEndpoint

open class AddContestantUseCase(private val endpoint: ContestantsEndpoint?) {

    sealed class AddContestantResult {
        object Completed : AddContestantResult()
        object Failed : AddContestantResult()
    }

    open suspend fun addContestant(
        contestantName: String,
        profilePictureUri: Uri?
    ): AddContestantResult {
        var addContestantResult: AddContestantResult

        val contestant = ASMRContestant(
            id = 0,
            name = contestantName,
            profilePicture = profilePictureUri?.toString() ?: ""
        )

        endpoint?.addContestant(contestant).also { result ->
            addContestantResult = when (result) {
                is Result.Completed -> AddContestantResult.Completed
                is Result.Failed -> AddContestantResult.Failed
                null -> AddContestantResult.Failed
            }
        }

        return addContestantResult
    }
}
