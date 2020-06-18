package com.ssh.androidarchitectures.mvvm

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ssh.androidarchitectures.R
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

        viewModel = ViewModelProviders.of(this).get(CountriesViewModel::class.java)
        listAdapter = ArrayAdapter(this, R.layout.row_layout, R.id.listText, listValues)

        list.adapter = listAdapter
        list.setOnItemClickListener { _, _, position, _ ->
            Toast.makeText(this, "you clicked :: ${listValues[position]}", Toast.LENGTH_SHORT)
                .show()
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.getCountries().observe(this,
            Observer { countries->
                if (countries != null) {
                    listValues.clear()
                    listValues.addAll(countries)
                    list.visibility = VISIBLE
                    listAdapter.notifyDataSetChanged()
                } else {
                    list.visibility = GONE
                }
            }
        )

        viewModel.getCountriesError().observe(this, Observer { error ->

            progress.visibility = GONE
            if (error) {
                Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_SHORT).show()
                retryButton.visibility = VISIBLE
            } else {
                retryButton.visibility = GONE
            }
        })
    }

    @SuppressLint("used from layout xml file")
    fun onRetry(view: View) {
        viewModel.onRefresh()
        retryButton.visibility = GONE
        progress.visibility = VISIBLE
        list.visibility = GONE
    }
}
