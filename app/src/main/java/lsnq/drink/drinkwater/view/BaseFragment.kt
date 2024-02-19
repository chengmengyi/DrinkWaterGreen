package lsnq.drink.drinkwater.view

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<VIEW : ViewDataBinding> : Fragment() {


    lateinit var binding: VIEW

    @LayoutRes
    abstract fun layoutId(): Int
    abstract fun onViewCreated()
    private lateinit var windowManager: WindowManager
    var width: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        binding.lifecycleOwner = this
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        windowManager = activity?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            width = windowManager.currentWindowMetrics.bounds.width()
        } else {
            val dm = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(dm)
            width = dm.widthPixels
        }
        onViewCreated()
    }

}