package lsnq.drink.drinkwater.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import lsnq.drink.drinkwater.*
import lsnq.drink.drinkwater.adapter.GenderAdapter
import lsnq.drink.drinkwater.databinding.DialogWeightGenderBinding
import lsnq.drink.drinkwater.mmkv.Mmkv

class DialogWeightGender(private val confirm: () -> Unit) : DialogFragment() {

    private val binding: DialogWeightGenderBinding by lazy {
        DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.dialog_weight_gender, null, false
        )
    }

    private val genderAdapter: GenderAdapter by lazy { GenderAdapter(requireContext()) }
    private val weightValueAdapter: GenderAdapter by lazy { GenderAdapter(requireContext()) }
    private val weightUnitAdapter: GenderAdapter by lazy { GenderAdapter(requireContext()) }

    private var gender: String = ""
    private var weight: String = ""
    private var unit: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val lineSnapHelper = LinearSnapHelper()
        val lineSnapHelper1 = LinearSnapHelper()
        val lineSnapHelper2 = LinearSnapHelper()
        val genderList = arrayListOf("", "Male", "Female", "")
        var weightValue = arrayListOf<String>()
        weightValue.add("")
        for (index in 10..300) {
            weightValue.add("$index")
        }
        weightValue.add("")
        val unitList = arrayListOf("", "Kg", "Lbs", "")


        binding.apply {
            lineSnapHelper.attachToRecyclerView(rvGender)
            rvGender.adapter = genderAdapter
            if (Mmkv.isMale) {
                gender = "Male"
                genderAdapter.setData(genderList, 1)
            } else {
                gender = "Female"
                rvGender.scrollToPosition(2)
                genderAdapter.setData(genderList, 2)
            }

            rvGender.addOnScrollListener(object : OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        val currentPosition =
                            (lineSnapHelper.findSnapView(recyclerView.layoutManager)!!.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition
                        genderAdapter.setData(genderList, currentPosition)
                        gender = genderList[currentPosition]
                    }
                }
            })


            lineSnapHelper1.attachToRecyclerView(rvWeightValue)
            rvWeightValue.adapter = weightValueAdapter

            val weightShow = Mmkv.unit.weightShow()
            "weightShow=$weightShow".drinkLog()
            weight = "$weightShow"
            val index = weightValue.indexOf(weightShow.toString())
            rvWeightValue.scrollToPosition(index - 1)
            weightValueAdapter.setData(weightValue, index)

            rvWeightValue.addOnScrollListener(object : OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        val currentPosition =
                            (lineSnapHelper1.findSnapView(recyclerView.layoutManager)!!.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition
                        weightValueAdapter.setData(weightValue, currentPosition)
                        weight = weightValue[currentPosition]
                    }
                }
            })

            lineSnapHelper2.attachToRecyclerView(rvWeightUnit)
            rvWeightUnit.adapter = weightUnitAdapter
            if (Mmkv.unit == "Kg") {
                unit = "Kg"
                weightUnitAdapter.setData(unitList, 1)
            } else {
                unit = "Lbs"
                rvWeightUnit.scrollToPosition(2)
                weightUnitAdapter.setData(unitList, 2)
            }

            rvWeightUnit.addOnScrollListener(object : OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        val currentPosition =
                            (lineSnapHelper2.findSnapView(recyclerView.layoutManager)!!.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition
                        weightUnitAdapter.setData(unitList, currentPosition)
                        unit = unitList[currentPosition]
                    }
                }
            })

            ivCancel.setOnClickListener {
                dismiss()
            }

            tvConfirm.setOnClickListener {
                "gender=$gender weight=$weight unit=$unit".drinkLog()
                Mmkv.unit = unit
                Mmkv.isMale = gender == "Male"
                if (unit != "Kg") {
                    Mmkv.weightLbs = weight.toInt()
                    Mmkv.weight = weight.toInt().lbsToKg()
                } else {
                    Mmkv.weight = weight.toInt()
                }
                confirm()
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
                it.decorView.setPadding(20f.dpToPx(), 0, 20f.dpToPx(), 0)
            }
        }
    }
}