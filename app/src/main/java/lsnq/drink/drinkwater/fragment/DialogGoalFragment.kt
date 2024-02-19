package lsnq.drink.drinkwater.fragment

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import lsnq.drink.drinkwater.R
import lsnq.drink.drinkwater.appContext
import lsnq.drink.drinkwater.databinding.DialogGoalBinding
import lsnq.drink.drinkwater.dpToPx
import lsnq.drink.drinkwater.mmkv.Mmkv

class DialogGoalFragment(val goal: (Int) -> Unit) : DialogFragment() {

    private val binding: DialogGoalBinding by lazy {
        DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.dialog_goal, null, false
        )
    }

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
            etGoal.setText("${Mmkv.goal}")
            etGoal.requestFocus()

            val manager =
                appContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
            tvConfirm.setOnClickListener {
                if (!TextUtils.isEmpty(etGoal.text.toString())) {
                    goal(etGoal.text.toString().toInt())
                    etGoal.clearFocus()
                    manager.hideSoftInputFromWindow(view.windowToken, 0)
                    dismiss()
                }
            }
            ivCancel.setOnClickListener {
                etGoal.clearFocus()
                manager.hideSoftInputFromWindow(view.windowToken, 0)
                dismiss()
            }
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
                it.decorView.setPadding(37f.dpToPx(), 0, 37f.dpToPx(), 0)
            }
        }
    }

}