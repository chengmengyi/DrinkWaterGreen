package lsnq.drink.drinkwater

import android.content.Intent
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import lsnq.drink.drinkwater.databinding.ActivityPlanBinding
import lsnq.drink.drinkwater.fragment.LoadingDialogFragment
import lsnq.drink.drinkwater.mmkv.Mmkv
import lsnq.drink.drinkwater.view.BaseActivity

class PlanActivity : BaseActivity<ActivityPlanBinding>() {

    override fun layoutId() = R.layout.activity_plan

    override fun onCreate() {
        initView()
    }

    private fun initView() {
        binding.apply {
            tvMale.isSelected = true

            tvStart.setOnClickListener {
                if (!TextUtils.isEmpty(etWeight.text.toString()) &&
                    etWeight.text.toString().toInt() > 0
                ) {
                    "time".drinkLog()
                    lifecycleScope.launch {
                        val dialogFragment = LoadingDialogFragment {}
                        dialogFragment.show(supportFragmentManager, "loading")
                        delay(3000)
                        Mmkv.isPlanFinish = true
                        Mmkv.isMale = tvMale.isSelected
                        Mmkv.weight = etWeight.text.toString().toInt()
                        Mmkv.goal = Mmkv.isMale.goalValue(Mmkv.weight)
                        dialogFragment.dismiss()
                        startActivity(Intent(this@PlanActivity, MainActivity::class.java))
                        finish()
                        "finish".drinkLog()
                    }
                } else {
                    Toast.makeText(this@PlanActivity,resources.getString(R.string.warn),Toast.LENGTH_SHORT).show()
                }
            }

            tvMale.setOnClickListener {
                tvMale.isSelected = true
                tvFemale.isSelected = false
            }

            tvFemale.setOnClickListener {
                tvMale.isSelected = false
                tvFemale.isSelected = true
            }
        }
    }
}