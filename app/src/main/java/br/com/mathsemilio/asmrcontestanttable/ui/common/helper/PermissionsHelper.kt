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

package br.com.mathsemilio.asmrcontestanttable.ui.common.helper

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import br.com.mathsemilio.asmrcontestanttable.common.observable.BaseObservable

class PermissionsHelper(
    private val activity: AppCompatActivity
) : BaseObservable<PermissionsHelper.Listener>() {

    interface Listener {
        fun onPermissionRequestResult(result: PermissionResult)
    }

    enum class PermissionResult {
        GRANTED,
        DENIED,
        DENIED_PERMANENTLY
    }

    private var permissionRequestCode = 0

    fun hasPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            activity,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(permissions: Array<String>, requestCode: Int) {
        permissionRequestCode = requestCode
        ActivityCompat.requestPermissions(activity, permissions, requestCode)
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        androidPermissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (androidPermissions.isEmpty() || grantResults.isEmpty())
            onPermissionResult(PermissionResult.DENIED)

        when (requestCode) {
            permissionRequestCode -> {
                when {
                    grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                        onPermissionResult(PermissionResult.GRANTED)
                    }
                    ActivityCompat.shouldShowRequestPermissionRationale(
                        activity,
                        androidPermissions[0]
                    ) -> {
                        onPermissionResult(PermissionResult.DENIED)
                    }
                    else -> {
                        onPermissionResult(PermissionResult.DENIED_PERMANENTLY)
                    }
                }
            }
        }
    }

    private fun onPermissionResult(result: PermissionResult) {
        notify { listener ->
            listener.onPermissionRequestResult(result)
        }
    }
}
