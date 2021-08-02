package com.ssh.androidarchitectures.mvi

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ssh.androidarchitectures.R
import com.ssh.androidarchitectures.repositories.CountriesService
import com.ssh.androidarchitectures.utils.ScheduleProvider
import com.ssh.androidarchitectures.utils.ViewModelsFactory
import kotlinx.android.synthetic.main.activity_mvvm.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class MviActivity : AppCompatActivity() {
    private lateinit var viewModel: MviCountriesViewModel
    private lateinit var listAdapter: ArrayAdapter<String>
    private var listValues = ArrayList<String>()

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, MviActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvi)
        setupUI()
        setupViewModel()
        observeViewModel()
        observeState()
    }

    private fun setupUI() {
        listAdapter = ArrayAdapter(this, R.layout.row_layout, R.id.listText, listValues)

        list.adapter = listAdapter
        list.setOnItemClickListener { _, _, position, _ ->
            Toast.makeText(this, "you clicked :: ${listValues[position]}", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.countryIntent.send(MviIntent.FetchCountries)
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelsFactory(CountriesService(), ScheduleProvider())
        ).get(MviCountriesViewModel::class.java)
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is ViewState.Idle -> {

                    }
                    is ViewState.Loading -> {
                        showProgress()
                    }

                    is ViewState.Countries -> {
                        hideProgress()
                        showList()
                        listValues.clear()
                        listValues.addAll(it.user.map { country -> country.name })
                        listAdapter.notifyDataSetChanged()
                    }
                    is ViewState.Error -> {
                        hideProgress()
                        showRetry()
                        Toast.makeText(this@MviActivity, it.error, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progress.visibility = View.GONE
    }

    @SuppressLint("used from layout xml file")
    fun onRetry(view: View) {
        observeState()
        hideRetry()
        showProgress()
        hideList()
    }

    private fun showList() {
        list.visibility = View.VISIBLE
    }

    private fun hideList() {
        list.visibility = View.GONE
    }

    private fun hideRetry() {
        retryButton.visibility = View.GONE
    }

    private fun showRetry() {
        retryButton.visibility = View.VISIBLE
    }
}