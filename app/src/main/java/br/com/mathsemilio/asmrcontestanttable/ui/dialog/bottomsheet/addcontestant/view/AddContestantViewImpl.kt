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

package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addcontestant.view

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import br.com.mathsemilio.asmrcontestanttable.R
import br.com.mathsemilio.asmrcontestanttable.common.onAfterTextChangedListener
import br.com.mathsemilio.asmrcontestanttable.common.setDisabledState
import br.com.mathsemilio.asmrcontestanttable.common.setEnabledState
import br.com.mathsemilio.asmrcontestanttable.common.showErrorState
import com.google.android.material.button.MaterialButton

class AddContestantViewImpl(
    inflater: LayoutInflater,
    container: ViewGroup?
) : AddContestantView() {

    private lateinit var imageViewContestantProfilePicture: ImageView
    private lateinit var textViewAddContestantName: TextView
    private lateinit var editTextContestantName: EditText
    private lateinit var buttonAddContestant: MaterialButton

    private var profilePictureUri: Uri? = null

    init {
        rootView = inflater.inflate(R.layout.bottom_sheet_add_contestant, container, false)

        initializeViews()

        attachContestantNameEditTextTextWatcher()

        imageViewContestantProfilePicture.setOnClickListener {
            onContestantProfilePictureImageViewClicked()
        }

        buttonAddContestant.setOnClickListener {
            onAddContestantButtonClicked()
        }
    }

    private fun initializeViews() {
        imageViewContestantProfilePicture = findViewById(R.id.image_view_contestant_profile_picture)
        textViewAddContestantName = findViewById(R.id.text_view_add_contestant_name)
        editTextContestantName = findViewById(R.id.edit_text_contestant_name)
        buttonAddContestant = findViewById(R.id.button_add_contestant)
    }

    private fun onContestantProfilePictureImageViewClicked() {
        notify { listener ->
            listener.onAddProfilePictureButtonClicked()
        }
    }

    private fun onAddContestantButtonClicked() {
        val contestantName = editTextContestantName.text.toString()

        if (contestantName.isBlank()) {
            editTextContestantName.showErrorState(getString(R.string.please_enter_contestant_name))
            revertAddButtonState()
            return
        } else {
            notify { listener ->
                listener.onAddButtonClicked(contestantName, profilePictureUri)
            }
        }
    }

    private fun attachContestantNameEditTextTextWatcher() {
        editTextContestantName.onAfterTextChangedListener { editable ->
            editable?.let {
                if (it.isEmpty()) {
                    textViewAddContestantName.isVisible = false
                } else {
                    textViewAddContestantName.apply {
                        isVisible = true
                        text = editable.toString()
                    }
                }
            }
        }
    }

    override fun bind(profilePictureUri: Uri?) {
        this.profilePictureUri = profilePictureUri
        imageViewContestantProfilePicture.setImageURI(profilePictureUri)
    }

    override fun changeAddButtonState() {
        buttonAddContestant.setDisabledState(getString(R.string.adding_contestant))
    }

    override fun revertAddButtonState() {
        buttonAddContestant.setEnabledState(getString(R.string.add))
    }
}
