package lsnq.drink.drinkwater.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import lsnq.drink.drinkwater.databinding.RecordComplateItemBinding
import lsnq.drink.drinkwater.drinkLog
import lsnq.drink.drinkwater.room.bean.RecordsBean
import lsnq.drink.drinkwater.timeFMD
import lsnq.drink.drinkwater.timeForMD
import lsnq.drink.drinkwater.timeForYMD7

class FinishAdapter(private val context: Context) :
    RecyclerView.Adapter<FinishAdapter.ViewHolder>() {

    private var data: MutableList<RecordsBean> = mutableListOf()

    fun setData(data: MutableList<RecordsBean>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(var binding: RecordComplateItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RecordComplateItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val value = data[position]
        "value=$value".drinkLog()
        holder.binding.apply {
            if (value == null) {
                ivFinish.isSelected = false
                tvData.text = position.timeForYMD7().timeFMD().timeForMD()
            } else {
                ivFinish.isSelected = value.drinkWaterSumValue >= value.drinkGoal
                tvData.text = value.dataTime.timeForMD()
            }
        }
    }

    override fun getItemCount() = data.size

}