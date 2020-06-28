package com.ssh.androidarchitectures.mvvm

import com.ssh.androidarchitectures.BaseSchedulerProvider
import com.ssh.androidarchitectures.ImmediateSchedulerProvider
import com.ssh.androidarchitectures.model.Country
import com.ssh.androidarchitectures.repositories.CountryRepository
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ViewModelTest {

    @Mock
    private lateinit var repository: CountryRepository
    private lateinit var schedulerProvider: BaseSchedulerProvider
    private val countries = listOf(Country("a"), Country("b"))
    private lateinit var viewModel: CountriesViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        schedulerProvider = ImmediateSchedulerProvider()
        viewModel = CountriesViewModel()
        viewModel.init(repository, schedulerProvider)
    }

//    @After
//    fun tearDown() {
//        schedulerProvider = null
//        viewModel = null
//    }

    @Test
    fun createPresenter_setsPresenterToView() {
    }

    @Test
    fun fetchCountriesAndShowIntoView() {
        Mockito.`when`(repository.getCountries()).thenReturn(Single.just(countries))
        viewModel.start()
        Assert.assertEquals(viewModel.getCountries(), countries)
    }
}