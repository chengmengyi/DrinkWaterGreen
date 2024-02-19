package lsnq.drink.drinkwater.fragment

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import lsnq.drink.drinkwater.*
import lsnq.drink.drinkwater.adapter.FinishAdapter
import lsnq.drink.drinkwater.adapter.TodayAdapter
import lsnq.drink.drinkwater.databinding.FragmentRecordsBinding
import lsnq.drink.drinkwater.room.bean.RecordsBean
import lsnq.drink.drinkwater.room.bean.TodayDrinkBean
import lsnq.drink.drinkwater.view.BaseFragment

class RecordsFragment : BaseFragment<FragmentRecordsBinding>() {

    override fun layoutId() = R.layout.fragment_records
    private val finishAdapter: FinishAdapter by lazy { FinishAdapter(requireContext()) }
    private val todayAdapter: TodayAdapter by lazy { TodayAdapter(requireContext()) }
    private var lastPosition = 4
    private var firstPosition = 0
    private var dateList: MutableList<RecordsBean> = mutableListOf()
    override fun onViewCreated() {
        initView()
        click()
    }

    private fun click() {
        binding.apply {
            ivLeft.setOnClickListener {
                if (firstPosition > 0 && dateList.size > 5) {
                    firstPosition--
                    recyclerView.smoothScrollToPosition(firstPosition)
                }
            }

            ivRight.setOnClickListener {
                if (lastPosition < 6 && dateList.size > 5 && lastPosition < dateList.size - 1) {
                    lastPosition++
                    recyclerView.smoothScrollToPosition(lastPosition)
                }
            }
        }
    }

    private fun initView() {
        dateList.clear()
        binding.apply {
            roomRuning {
                val value = getAvgDrinkDay()
                roomMainRuning {
                    tvIntakeValue.text = value
                }
            }
            roomRuning {
                val value = getAvgDrinkTime()
                roomMainRuning {
                    tvFrequencyValue.text = value
                }
            }
            roomRuning {
                val value = getAvgRate()
                roomMainRuning {
                    tvFrequencyValue1.text = value
                }
            }

            if (recyclerView.itemDecorationCount == 0) {
                recyclerView.addItemDecoration(Decoration(width))
            }
            recyclerView.adapter = finishAdapter
            roomRuning {
                for (index in 0..6) {
                    dateList.add(roomDao.getRecordFromDate(index.timeForYMD7().timeFMD()))
                }
                roomMainRuning {
                    finishAdapter.setData(dateList)
                }
            }


            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    firstPosition =
                        (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    lastPosition =
                        (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                }
            })


            rvToday.adapter = todayAdapter
            roomRuning {
                val recordToday = roomDao.getTodayRecord(timeForYMD())
                roomMainRuning {
                    todayAdapter.setData(recordToday)
                }
            }

            todayAdapter.setListener(object : TodayAdapter.IClickTodayRecord {
                override fun clickPosition(bean: TodayDrinkBean) {
                    roomRuning {
                        bean.drinkWater.deleteRecordDate()
                        roomDao.deleteTodayRecord(bean.uid)
                        roomMainRuning {
                            initView()
                        }
                    }
                }
            })

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
            val widthRecycle = (width - (45 * 5 + 80).toFloat().dpToPx()) / 4
            val position = parent.getChildAdapterPosition(view)
            if (position != 0) {
                outRect.left = widthRecycle
            }
        }
    }

}