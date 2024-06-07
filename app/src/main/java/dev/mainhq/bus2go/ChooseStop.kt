package dev.mainhq.bus2go

import android.os.Build
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.mainhq.bus2go.fragments.favouritesDataStore
import dev.mainhq.bus2go.adapters.StopListElemsAdapter
import dev.mainhq.bus2go.utils.BusAgency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//todo
//instead of doing a huge query on getting the time, we could first retrieve
//all the possible stations (ordered by id, and prob based on localisation? -> not privacy friendly
//and once the user clicks, either new activity OR new fragment? -> in the latter case need to implement onback
//todo add possibility of searching amongst all the stops
class ChooseStop : BaseActivity() {

    private lateinit var agency: BusAgency

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choose_stop)

        //below represents strings of stopsinfo
        val data : List<String> = intent.getStringArrayListExtra("stops") ?: listOf()
        val headsign = intent.getStringExtra("headsign") ?: ""
        agency = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra (AGENCY, BusAgency::class.java) ?: throw AssertionError("AGENCY is Null")
        } else {
            intent.getSerializableExtra (AGENCY) as BusAgency? ?: throw AssertionError("AGENCY is Null")
        }
        if (data.isNotEmpty()) {
            val recyclerView: RecyclerView = findViewById(R.id.stop_recycle_view)
            val layoutManager = LinearLayoutManager(applicationContext)
            lifecycleScope.launch {
                //the datastore may be closed!
                val favourites = favouritesDataStore.data.first().listSTM.toList()
                withContext(Dispatchers.Main){
                    recyclerView.adapter = StopListElemsAdapter(data, favourites, headsign, agency)
                    layoutManager.orientation = LinearLayoutManager.VERTICAL
                    recyclerView.layoutManager = layoutManager
                }
            }

        }
        else{
            TODO("Display message saying no stops found")
        }
    }
}