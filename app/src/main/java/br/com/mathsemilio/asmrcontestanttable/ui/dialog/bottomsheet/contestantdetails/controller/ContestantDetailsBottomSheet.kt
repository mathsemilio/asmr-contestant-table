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

package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.contestantdetails.controller

import android.view.*
import android.os.Bundle
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.common.BaseBottomSheetDialogFragment

class ContestantDetailsBottomSheet : BaseBottomSheetDialogFragment(),
    ContestantDetailsControllerEventDelegate {

    companion object {
        private const val ARG_CONTESTANT = "ARG_CONTESTANT"

        fun with(contestant: ASMRContestant) = ContestantDetailsBottomSheet().apply {
            arguments = Bundle(1).apply { putSerializable(ARG_CONTESTANT, contestant) }
        }
    }

    private lateinit var controller: ContestantsDetailsBottomSheetController

    private lateinit var contestant: ASMRContestant

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = compositionRoot.controllerFactory.contestantDetailsBottomSheetController
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = compositionRoot.viewFactory.getContestantsDetailsView(container)
        controller.bindView(view)
        return view.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contestant = getContestantFromBundle()
        controller.bindContestant(contestant)
        controller.addDelegate(this)
    }

    private fun getContestantFromBundle(): ASMRContestant {
        return requireArguments().getSerializable(ARG_CONTESTANT) as ASMRContestant
    }

    override fun onDismissBottomSheetRequested() = dismiss()

    override fun onStart() {
        super.onStart()
        controller.onStart()
    }

    override fun onStop() {
        super.onStop()
        controller.onStop()
        controller.removeDelegate()
    }
}
