package br.com.mathsemilio.asmrcontestanttable.ui.common.manager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class FragmentManager(
    private val fragmentManager: FragmentManager,
    private val fragmentContainerManager: FragmentContainerManager
) {
    fun replaceFragmentAtContainerWith(fragment: Fragment) {
        fragmentManager.beginTransaction().apply {
            setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            replace(fragmentContainerManager.fragmentContainer.id, fragment)
            commitNow()
        }
    }
}