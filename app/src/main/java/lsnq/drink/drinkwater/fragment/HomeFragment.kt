package lsnq.drink.drinkwater.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.RecyclerView
import lsnq.drink.drinkwater.*
import lsnq.drink.drinkwater.adapter.DrinkValueAdapter
import lsnq.drink.drinkwater.databinding.FragmentHomeBinding
import lsnq.drink.drinkwater.mmkv.Mmkv
import lsnq.drink.drinkwater.view.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun layoutId() = R.layout.fragment_home
    private val drinkValueAdapter: DrinkValueAdapter by lazy { DrinkValueAdapter(requireContext()) }

    override fun onViewCreated() {
        initView()
        click()
    }

    private fun showSoftInput(view: View) {
        val manager =
            appContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.showSoftInput(view, 0)
    }


    @SuppressLint("SetTextI18n")
    private fun click() {

        binding.apply {
            ivChange.setOnClickListener {

                DialogGoalFragment {
                    tvGoalValue.text = "${it}ml"
                    Mmkv.goal = it
                    roomRuning {
                        val drinkValue = getDrinkValue()
                        drinkValue.addRecordDate()
                    }
                    initView()
                }.show(childFragmentManager, "goal")


            }

            clAddWater.setOnClickListener {
                var sum = tvDrinkSumValue.text.toString().split("ml")[0].toInt()
                val drinkValue = tvDrinkValue.text.toString().replace("ml", "").toInt()
                sum += drinkValue
                tvDrinkSumValue.text = "${sum}ml(${sum.myrate(Mmkv.goal)}%)"
                ivFinish.visibility = if (sum < Mmkv.goal) {
                    View.INVISIBLE
                } else View.VISIBLE

                roomRuning {
                    drinkValue.addRecordToday()
                    sum.addRecordDate()

                    val drinkTime = getTodayRecordLast()
                    roomMainRuning {
                        tvTime.text = if (sum < Mmkv.goal) {
                            drinkTime
                        } else {
                            "Finish"
                        }
                    }
                }
            }


        }
    }


    private fun initView() {
        binding.apply {
            tvTitle.text = appContext.resources.getString(R.string.app_name)
            val list = arrayListOf("100ml", "200ml", "250ml", "300ml", "400ml", "500ml")
            if (recyclerView.itemDecorationCount == 0) {
                recyclerView.addItemDecoration(Decoration(width))
            }
            recyclerView.adapter = drinkValueAdapter
            drinkValueAdapter.setData(list, 2)
            drinkValueAdapter.setListener(object : DrinkValueAdapter.WaterValueListener {
                override fun value(s: String) {
                    tvDrinkValue.text = s
                }
            })

            tvGoalValue.text = "${Mmkv.goal}ml"


            roomRuning {
                val drinkValue = getDrinkValue()
                roomMainRuning {
                    tvDrinkSumValue.text = "${drinkValue}ml(${drinkValue.myrate(Mmkv.goal)}%)"
                    ivFinish.visibility = if (drinkValue < Mmkv.goal) {
                        View.INVISIBLE
                    } else View.VISIBLE
                }
                val drinkTime = getTodayRecordLast()
                roomMainRuning {

                    tvTime.text = if (drinkValue < Mmkv.goal) {
                        drinkTime
                    } else {
                        "Finish"
                    }
                }
            }

        }
    }


    class Decoration(private val width: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            val widthRecycle = (width - (48 * 5 + 64).toFloat().dpToPx()) / 4
            val position = parent.getChildAdapterPosition(view)
            if (position != 0) {
                outRect.left = widthRecycle
            }
        }
    }

}