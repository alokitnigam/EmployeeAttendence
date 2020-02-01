package com.example.syncbyte.Views.Adapter

import android.content.Intent
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.syncbyte.DI.Models.AttendenceModel
import com.example.syncbyte.R
import java.text.DateFormatSymbols
import java.util.*
import kotlin.collections.ArrayList


class AttendenceAdapter: RecyclerView.Adapter<AttendenceAdapter.ImageViewHolder>() {
    private var list: ArrayList<AttendenceModel> = ArrayList()
    lateinit var onItemDeleteListener: OnItemDeleteListener

    fun setListener(onItemDeleteListener: OnItemDeleteListener){
        this.onItemDeleteListener = onItemDeleteListener
    }

    fun setList(list: List<AttendenceModel>)
    {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class ImageViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(attendenceModel: AttendenceModel) {



            entrytime.text = getFormattedDate(attendenceModel.entryTime)
            exittime.text = if(attendenceModel.exitTime != null)
                getFormattedDate(attendenceModel.exitTime!!)
                        else
                "-"
            delete.setOnClickListener {
             onItemDeleteListener.onItemDeleted(attendenceModel)

            }


        }

        private val entrytime: TextView = itemView.findViewById(R.id.entrytime)
        private val delete: ImageView = itemView.findViewById(R.id.delete)
        private val exittime: TextView = itemView.findViewById(R.id.exititme)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.attendence_item_view, parent, false)


        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(list.get(position));
    }


    interface OnItemDeleteListener{
        fun onItemDeleted(attendenceModel: AttendenceModel)
    }

    fun getFormattedDate(smsTimeInMilis: Long): String? {
        val postTime = Calendar.getInstance()
        postTime.timeInMillis = smsTimeInMilis
        val now = Calendar.getInstance()
        val symbols =
            DateFormatSymbols(Locale.getDefault())
        // OVERRIDE SOME symbols WHILE RETAINING OTHERS
        symbols.amPmStrings = arrayOf("am", "pm")
        val timeFormatString = "h:mm aa"
        return if (now[Calendar.DATE] == postTime[Calendar.DATE]) {
            "Today, " + DateFormat.format(
                "h:mm:ss aa",
                postTime
            ).toString().replace("AM", "am").replace("PM", "pm")
        } else if (now[Calendar.DATE] - postTime[Calendar.DATE] == 1) {
            "Yesterday, " + DateFormat.format(
                "h:mm:ss aa",
                postTime
            ).toString().replace("AM", "am").replace("PM", "pm")
        } else {
            DateFormat.format("MMMM dd , h:mm:ss aa", postTime).toString()
                .replace("AM", "am").replace("PM", "pm")
        }
    }


}

