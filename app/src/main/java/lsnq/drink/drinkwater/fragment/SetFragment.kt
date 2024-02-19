package lsnq.drink.drinkwater.fragment

import android.content.Intent
import lsnq.drink.drinkwater.*
import lsnq.drink.drinkwater.databinding.FragmentSetBinding
import lsnq.drink.drinkwater.mmkv.Mmkv
import lsnq.drink.drinkwater.view.BaseFragment

class SetFragment : BaseFragment<FragmentSetBinding>() {

    override fun layoutId() = R.layout.fragment_set

    override fun onViewCreated() {
        binding.apply {
            tvInfo.text = "${Mmkv.isMale.isMale()}/${Mmkv.unit.weightShow()}${Mmkv.unit}"

            clGender.setOnClickListener {
                DialogWeightGender {
                    tvInfo.text = "${Mmkv.isMale.isMale()}/${Mmkv.unit.weightShow()}${Mmkv.unit}"
                    Mmkv.goal = Mmkv.isMale.goalValue(Mmkv.weight)
                }.show(childFragmentManager, "weight")
            }
            tvPrivacy.setOnClickListener {
                startActivity(
                    Intent(activity, WebViewActivity::class.java).putExtra(
                        "webUrl",
                        Value.webUrl
                    )
                )
            }
        }
    }


}