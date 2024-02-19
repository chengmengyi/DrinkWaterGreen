package lsnq.drink.drinkwater.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import lsnq.drink.drinkwater.databinding.TodayRecordItemBinding
import lsnq.drink.drinkwater.room.bean.TodayDrinkBean

class TodayAdapter(private val context: Context) :
    RecyclerView.Adapter<TodayAdapter.ViewHolder>() {

    private var data: MutableList<TodayDrinkBean> = mutableListOf()
    private var iClickTodayRecord: IClickTodayRecord? = null

    fun setData(data: MutableList<TodayDrinkBean>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    interface IClickTodayRecord {
        fun clickPosition(bean: TodayDrinkBean)
    }

    fun setListener(iClickTodayRecord: IClickTodayRecord) {
        this.iClickTodayRecord = iClickTodayRecord
    }

    class ViewHolder(var binding: TodayRecordItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TodayRecordItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val bean = data[position]
        holder.binding.apply {
            tvTime.text = bean.drinkTime
            tvWaterValue.text = "${bean.drinkWater}"
            ivDelete.setOnClickListener {
                iClickTodayRecord?.clickPosition(bean)
            }
        }
    }

    override fun getItemCount() = data.size

}