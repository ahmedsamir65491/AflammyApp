package com.example.aflammy.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.example.aflammy.BR
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseDialogFragment<VDB : ViewDataBinding> : BottomSheetDialogFragment() {

    abstract val layoutIdFragment: Int
    abstract val viewModel: ViewModel

    private lateinit var _binding: VDB
    protected val binding: VDB
        get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutIdFragment, container, false)
        _binding.apply {
            lifecycleOwner = this@BaseDialogFragment
            setVariable(BR.viewModel, viewModel)
            return root
        }
    }
}