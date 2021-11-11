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

package br.com.mathsemilio.asmrcontestanttable.ui.common.helper.imagepicker

import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import br.com.mathsemilio.asmrcontestanttable.common.observable.BaseObservable

class ImagePickerHelper(
    fragment: Fragment
) : BaseObservable<ImagePickerHelper.Listener>() {

    interface Listener {
        fun onImagePickedSuccessfully(imageUri: Uri?)
    }

    private val resultLauncher = fragment.registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        notify { listener ->
            listener.onImagePickedSuccessfully(uri)
        }
    }

    fun launchImagePicker() {
        resultLauncher.launch("image/*")
    }
}
