package lsnq.drink.drinkwater.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import lsnq.drink.drinkwater.databinding.DrinkItemBinding

class DrinkValueAdapter(private val context: Context) :
    RecyclerView.Adapter<DrinkValueAdapter.ViewHolder>() {

    private var data: MutableList<String> = mutableListOf()
    private var positionColor: Int = 0
    private var listener: WaterValueListener? = null

    interface WaterValueListener {
        fun value(s: String)
    }

    fun setListener(listener: WaterValueListener) {
        this.listener = listener
    }

    fun setData(data: MutableList<String>, positionColor: Int) {
        this.positionColor = positionColor
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(var binding: DrinkItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DrinkItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val value = data[position]
        holder.binding.apply {
            tvValue.isSelected = position == positionColor
            tvValue.text = value
            llValue.setOnClickListener {
                positionColor = position
                listener?.value(value)
                notifyDataSetChanged()
            }
        }

    }

    override fun getItemCount() = data.size

}