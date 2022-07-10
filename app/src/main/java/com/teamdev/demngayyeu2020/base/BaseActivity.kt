package com.teamdev.demngayyeu2020.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.teamdev.demngayyeu2020.`object`.statusTransparent
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.core.scope.Scope

/*
*  buildFeatures {
        dataBinding true
    }
* */
abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity(), AndroidScopeComponent {

    override val scope: Scope by activityScope()

    private var isResume = true
    lateinit var viewBinding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isActive()) {
            if (isStatusTransparent()){
                statusTransparent()
            }
            viewBinding = DataBindingUtil.setContentView(this, loadUI())
            viewBinding.lifecycleOwner = this
            createUI()
        }
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

    protected open fun isStatusTransparent(): Boolean {
        return false
    }
    /*
    * true : Activity run
    * false : Activity destroy
    * */
    fun isActive(): Boolean {
        return !isFinishing && !isDestroyed
    }

    /*
    * true : Activity Foreground
    * false : Activity Background
    * */
    fun isResume(): Boolean {
        return isResume && isActive()
    }

}