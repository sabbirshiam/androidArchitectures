package com.ssh.androidarchitectures.mvp

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
import com.ssh.androidarchitectures.R
import com.ssh.androidarchitectures.repositories.CountriesService
import com.ssh.androidarchitectures.utils.ScheduleProvider
import kotlinx.android.synthetic.main.activity_mvp.*

class MVPActivity : AppCompatActivity(), MvpContractor.View {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, MVPActivity::class.java)
        }
    }

    private var countries = ArrayList<String>()
    private lateinit var countriesAdapter: ArrayAdapter<String>
    override lateinit var presenter: MvpContractor.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvp)
        setTitle(R.string.title_mvp)
        CountriesPresenter(this, CountriesService(), ScheduleProvider())
        countriesAdapter = ArrayAdapter(this, R.layout.row_layout, R.id.listText, countries)

        list.adapter = countriesAdapter
        list.setOnItemClickListener { _, _, position, _ ->
            Toast.makeText(this, "you clicked :: ${countries[position]}", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.start()
    }

    @SuppressLint("used from layout xml file")
    fun onRetry(view: View) {
        presenter.onRetry()
        retryButton.visibility = GONE
        progress.visibility = VISIBLE
        list.visibility = GONE
    }

    override fun showCountriesName(countries: List<String>) {
        this.countries.clear()
        this.countries.addAll(countries)
        retryButton.visibility = GONE
        progress.visibility = GONE
        list.visibility = VISIBLE
        countriesAdapter.notifyDataSetChanged()
    }

    override fun showError() {
        Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_SHORT).show()
        progress.visibility = GONE
        retryButton.visibility = VISIBLE
        list.visibility = GONE
    }
}
