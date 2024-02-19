package lsnq.drink.drinkwater.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import lsnq.drink.drinkwater.R
import lsnq.drink.drinkwater.databinding.DialogLoadingBinding
import lsnq.drink.drinkwater.dpToPx

class LoadingDialogFragment(val close: () -> Unit) : DialogFragment() {

    private val binding: DialogLoadingBinding by lazy {
        DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.dialog_loading, null, false
        )
    }
    private val animation = RotateAnimation(
        0f,
        360f,
        Animation.RELATIVE_TO_SELF,
        0.5f,
        Animation.RELATIVE_TO_SELF,
        0.5f
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            animation.apply {
                duration = 1000
                repeatCount = 3
                interpolator = LinearInterpolator()
                setAnimationListener(object : AnimationListener {
                    override fun onAnimationStart(p0: Animation?) = Unit
                    override fun onAnimationEnd(p0: Animation?) {
                        dismiss()
                        close()
                    }

                    override fun onAnimationRepeat(p0: Animation?) = Unit
                })
            }
            ivLoading.startAnimation(animation)
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.apply {
            window?.let {
                val lp = it.attributes
                lp.width = ViewGroup.LayoutParams.MATCH_PARENT
                it.setLayout(lp.width, lp.height)
                it.decorView.background = ColorDrawable(Color.TRANSPARENT)
                it.decorView.setPadding(20f.dpToPx(), 0, 20f.dpToPx(), 0)
            }
        }
        dialog?.setCanceledOnTouchOutside(false)
    }

    override fun onPause() {
        super.onPause()
        animation.cancel()
    }
}