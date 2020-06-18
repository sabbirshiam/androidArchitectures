package com.ssh.androidarchitectures.mvc

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ArrayAdapter
import android.widget.Toast
import com.ssh.androidarchitectures.R
import com.ssh.androidarchitectures.repositories.CountriesService
import com.ssh.androidarchitectures.utils.ScheduleProvider
import kotlinx.android.synthetic.main.activity_mvc.*

class MVCActivity : AppCompatActivity() {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, MVCActivity::class.java)
        }
    }

    private var listValues = ArrayList<String>()
    private lateinit var listAdapter: ArrayAdapter<String>
    private lateinit var countriesController: CountriesController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvc)
        setTitle(R.string.title_mvc)
        countriesController = CountriesController(this@MVCActivity, CountriesService(), ScheduleProvider())
        listAdapter = ArrayAdapter(this, R.layout.row_layout, R.id.listText, listValues)

        list.adapter = listAdapter
        list.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, "you clicked :: ${listValues[position]}", Toast.LENGTH_SHORT).show()
        }
    }

    fun showCountriesName(values: List<String>) {
        listValues.clear()
        listValues.addAll(values)
        retryButton.visibility = GONE
        progress.visibility = GONE
        list.visibility = VISIBLE
        listAdapter.notifyDataSetChanged()
    }

    fun onRetry(view: View) {
        countriesController.onRefresh()
        retryButton.visibility = GONE
        progress.visibility = VISIBLE
        list.visibility = GONE
    }

    fun onError() {
        Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_SHORT).show()
        progress.visibility = GONE
        retryButton.visibility = VISIBLE
        list.visibility = GONE
    }
}
