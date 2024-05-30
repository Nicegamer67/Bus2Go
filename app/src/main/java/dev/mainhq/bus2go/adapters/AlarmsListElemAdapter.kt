package dev.mainhq.bus2go.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.textview.MaterialTextView
import dev.mainhq.bus2go.R
import dev.mainhq.bus2go.fragments.Alarms
import dev.mainhq.bus2go.preferences.Alarm

class AlarmsListElemAdapter(private val list : List<Alarm>)
    : RecyclerView.Adapter<AlarmsListElemAdapter.ViewHolder>() {

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val materialSwitch : MaterialSwitch = view.findViewById(R.id.alarmSwitch)
        val alarmTitle : MaterialTextView = view.findViewById(R.id.alarmTitle)
        val alarmBusInfo : MaterialTextView = view.findViewById(R.id.alarmBusInfo)
        val alarmTimeBefore : MaterialTextView = view.findViewById(R.id.alarmTimeBefore)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.elem_alarm_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.itemView.tag = data.id
        holder.alarmTitle.text = data.title
        holder.alarmBusInfo.text = data.busInfo.stopName
        holder.alarmTimeBefore.text = "${data.timeBefore.hour}h, ${data.timeBefore.min}"
        holder.materialSwitch.text = data.ringDays
        if (data.isOn) holder.materialSwitch.isChecked = true
        holder.materialSwitch.setOnClickListener {
            (holder.itemView.findFragment<Alarms>()).alarmViewModel.updateLiveActivatedState(data.id, holder.materialSwitch.isChecked)
        }
        //holder.itemView.setOnClickListener {
        //
        //}
    }
}