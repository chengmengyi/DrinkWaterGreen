package lsnq.drink.drinkwater

import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import lsnq.drink.drinkwater.databinding.ActivityMainBinding
import lsnq.drink.drinkwater.view.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun layoutId() = R.layout.activity_main

    override fun onCreate() {
        viewInit()
    }


    private fun viewInit() {
        binding.apply {
            val navControl = Navigation.findNavController(this@MainActivity, R.id.fgNavigation)
            bnvButton.itemIconTintList = null
            NavigationUI.setupWithNavController(bnvButton, navControl)
        }
    }
}