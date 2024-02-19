package lsnq.drink.drinkwater.adapter

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import lsnq.drink.drinkwater.R
import lsnq.drink.drinkwater.databinding.GenderItemBinding

class GenderAdapter(private val context: Context) :
    RecyclerView.Adapter<GenderAdapter.ViewHolder>() {

    private var data: MutableList<String> = mutableListOf()
    private var positionColor: Int = 0

    fun setData(data: MutableList<String>, positionColor: Int) {
        this.positionColor = positionColor
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(var binding: GenderItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(GenderItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val value = data[position]
        holder.binding.apply {
            if (position == positionColor) {
                llBg.background = context.resources.getDrawable(R.drawable.green_dialog_radio, null)
                tvValue.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
                tvValue.setTextColor(context.resources.getColor(R.color.white, null))
            } else {
                llBg.background = context.resources.getDrawable(R.drawable.while_bg_radio, null)
                tvValue.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                tvValue.setTextColor(context.resources.getColor(R.color.dialog_text, null))
            }
            tvValue.text = value
        }

    }

    override fun getItemCount() = data.size

}