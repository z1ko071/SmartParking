package uz.abdulaziz.smartparking.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.abdulaziz.smartparking.R
import uz.abdulaziz.smartparking.core.model.boarding.BoardingModel
import uz.abdulaziz.smartparking.databinding.ItemBoardingBinding

class BoardingAdapter : RecyclerView.Adapter<BoardingAdapter.ViewHolder>() {

    private var data: ArrayList<BoardingModel> = ArrayList()

    init {
        setData()
    }

    private fun setData() {
        data.clear()
        data.add(
            BoardingModel(
                1,
                R.drawable.boarding_1,
                "Find Parking Places Around You Easily",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
            )
        )
        data.add(
            BoardingModel(
                2,
                R.drawable.boarding_2,
                "Book and Pay Parking Quickly & Safely",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
            )
        )
        data.add(
            BoardingModel(
                3,
                R.drawable.boarding_3,
                "Extend Parking Time As You Need",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
            )
        )
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemBoardingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: BoardingModel) {

            binding.image.setImageResource(data.image)
            binding.title.text = data.title
            binding.description.text = data.description

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBoardingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(data[position])
    }
}