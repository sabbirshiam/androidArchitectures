package com.ssh.androidarchitectures.mvvm

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ssh.androidarchitectures.R
import com.ssh.androidarchitectures.repositories.CountriesService
import com.ssh.androidarchitectures.utils.ScheduleProvider
import kotlinx.android.synthetic.main.activity_mvvm.*

class MVVMActivity : AppCompatActivity() {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, MVVMActivity::class.java)
        }
    }

    private var listValues = ArrayList<String>()
    private lateinit var viewModel: CountriesViewModel
    private lateinit var listAdapter: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvvm)
        setTitle(R.string.title_mvvm)

        viewModel = ViewModelProvider(this).get(CountriesViewModel::class.java)
        viewModel.init(CountriesService(), ScheduleProvider())
        listAdapter = ArrayAdapter(this, R.layout.row_layout, R.id.listText, listValues)

        list.adapter = listAdapter
        list.setOnItemClickListener { _, _, position, _ ->
            Toast.makeText(this, "you clicked :: ${listValues[position]}", Toast.LENGTH_SHORT)
                .show()
        }

        observeViewModel()
    }

    override fun onStart() {
        super.onStart()
        viewModel.start()
    }

    private fun observeViewModel() {
        viewModel.getCountries().observe(this,
            Observer { countries ->
                if (countries != null) {
                    showList()
                    listValues.clear()
                    listValues.addAll(countries)
                    listAdapter.notifyDataSetChanged()
                } else {
                    hideList()
                }
            }
        )

        viewModel.getCountriesError().observe(this, Observer { error ->
            hideProgress()
            if (error) {
                Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_SHORT).show()
                showRetry()
            } else {
                hideRetry()
            }
        })
    }

    @SuppressLint("used from layout xml file")
    fun onRetry(view: View) {
        viewModel.onRefresh()
        hideRetry()
        showProgress()
        hideList()
    }

    fun showList() { list.visibility = VISIBLE }

    fun hideList() { list.visibility = GONE }

    fun hideRetry() { retryButton.visibility = GONE }

    fun showRetry() { retryButton.visibility = VISIBLE }

    fun showProgress() { progress.visibility = VISIBLE }

    fun hideProgress() { progress.visibility = GONE }
}
