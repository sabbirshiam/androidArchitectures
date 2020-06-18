package com.ssh.androidarchitectures.mvp

import com.ssh.androidarchitectures.BaseSchedulerProvider
import com.ssh.androidarchitectures.ImmediateSchedulerProvider
import com.ssh.androidarchitectures.model.Country
import com.ssh.androidarchitectures.repositories.CountryRepository
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class PresenterTest {

    @Mock
    private lateinit var repository: CountryRepository
    @Mock
    private lateinit var view: MvpContractor.View

    private lateinit var presenter: MvpContractor.Presenter
    private lateinit var schedulerProvider: BaseSchedulerProvider

    private val countries = listOf(Country("a"), Country("b"))

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        schedulerProvider = ImmediateSchedulerProvider()
    }

    @Test
    fun createPresenter_setsPresenterToView() {
        presenter = CountriesPresenter(view, repository, schedulerProvider)
        verify(view).presenter = presenter
    }

    @Test
    fun fetchCountriesAndShowIntoView() {
        presenter = CountriesPresenter(view,repository, schedulerProvider)
        `when`(repository.getCountries()).thenReturn(Single.just(countries))
        presenter.start()
        verify(view).showCountriesName(countries.map { it.name })
    }
}