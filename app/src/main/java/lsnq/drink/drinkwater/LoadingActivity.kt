package lsnq.drink.drinkwater

import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import lsnq.drink.drinkwater.databinding.ActivityLoadingBinding
import lsnq.drink.drinkwater.mmkv.Mmkv
import lsnq.drink.drinkwater.view.BaseActivity

class LoadingActivity : BaseActivity<ActivityLoadingBinding>() {

    override fun layoutId() = R.layout.activity_loading
    private var fromBackground = false

    override fun onCreate() {
        fromBackground = intent.getBooleanExtra("", true) == true
        if (Mmkv.isPlanFinish) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    repeat(100) {
                        delay(100)
                        binding.progressBar.progress = it
                        if (it == 20) {
                            if (fromBackground) {
                                startActivity(
                                    Intent(
                                        this@LoadingActivity,
                                        MainActivity::class.java
                                    )
                                )
                            }
                            finish()
                        }
                    }
                }
            }
        } else {
            startActivity(Intent(this, PlanActivity::class.java))
            finish()
        }
    }


}