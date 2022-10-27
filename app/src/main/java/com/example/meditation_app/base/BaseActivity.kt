package com.example.meditation_app.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB: ViewBinding, VM: BaseViewModel> : AppCompatActivity() {

    abstract fun getInflater(): (inflater: LayoutInflater) -> VB
    private lateinit var _binding: VB
    protected val baseBinding by lazy { _binding }

    abstract fun getViewModel(): VM
    private lateinit var _viewModel: VM
    protected val baseViewModel by lazy { _viewModel }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewModel = getViewModel()
        _binding = getInflater()(layoutInflater)
        setContentView(_binding.root)
        setup()
    }

    abstract fun setup()

}

