package lsnq.drink.drinkwater

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import kotlinx.coroutines.*

class LifecycleDrink : Application.ActivityLifecycleCallbacks {
    private var index = 0
    private var timeOver = false
    private var job: Job? = null

    override fun onActivityCreated(p0: Activity, p1: Bundle?) = Unit

    override fun onActivityStarted(p0: Activity) {
        index++
        if (index == 1) {
            job?.cancel()
            if (timeOver) {
                if (p0 !is PlanActivity) {
                    p0.startActivity(
                        Intent(
                            p0,
                            LoadingActivity::class.java
                        ).putExtra("isBackground", false)
                    )
                }
            }
            timeOver = false
        }
    }

    override fun onActivityResumed(p0: Activity) = Unit
    override fun onActivityPaused(p0: Activity) = Unit

    override fun onActivityStopped(p0: Activity) {
        index--
        if (index == 0) {
            job = CoroutineScope(Dispatchers.IO).launch {
                delay(3000)
                timeOver = true
            }
        }
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) = Unit
    override fun onActivityDestroyed(p0: Activity) = Unit

}