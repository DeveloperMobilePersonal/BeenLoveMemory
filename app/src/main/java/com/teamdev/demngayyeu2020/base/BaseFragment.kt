package com.teamdev.demngayyeu2020.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.teamdev.demngayyeu2020.ui.main.MainActivity
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope

abstract class BaseFragment<T : ViewDataBinding> : Fragment(), AndroidScopeComponent {

    override val scope by fragmentScope()

    private var isResume = true
    lateinit var viewBinding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (isActive()) {
            viewBinding = DataBindingUtil.inflate(inflater, loadUI(), container, false)
            viewBinding.lifecycleOwner = this
            createUI()
        }
        return viewBinding.root
    }

    override fun onResume() {
        isResume = true
        super.onResume()
    }

    override fun onPause() {
        isResume = false
        super.onPause()
    }

    override fun onDestroy() {
        destroyUI()
        super.onDestroy()
    }

    protected abstract fun loadUI(): Int
    protected abstract fun createUI()
    protected abstract fun destroyUI()

    /**
    * true : Activity run
    * false : Activity destroy
    * */
    fun isActive(): Boolean {
        val activity = activity
        return activity != null && !activity.isFinishing && !activity.isDestroyed && isAdded
    }

    /*
    * true : Activity Foreground
    * false : Activity Background
    * */
    fun isResume(): Boolean {
        return isResume && isActive()
    }

    fun runActivity(result: ((AppCompatActivity) -> Unit)? = null) {
        val activity = activity
        if (isActive() && activity is AppCompatActivity) {
            result?.let { it(activity) }
        }
    }

    fun runMainActivity(result: ((MainActivity) -> Unit)? = null) {
        val activity = activity
        if (isActive() && activity is MainActivity) {
            result?.let { it(activity) }
        }
    }
}