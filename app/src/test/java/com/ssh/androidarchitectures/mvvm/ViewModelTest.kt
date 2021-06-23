package com.ssh.androidarchitectures.mvvm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ssh.androidarchitectures.BaseSchedulerProvider
import com.ssh.androidarchitectures.ImmediateSchedulerProvider
import com.ssh.androidarchitectures.model.Country
import com.ssh.androidarchitectures.repositories.CountryRepository
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class ViewModelTest {
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var repository: CountryRepository
    private lateinit var schedulerProvider: BaseSchedulerProvider
    private val countries = listOf(Country("a"), Country("b"))
    private lateinit var viewModel: CountriesViewModel

    @Mock
    lateinit var observer: Observer<List<String>>

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
        viewModel.getCountries().observeForever(observer)
        val expected = countries.map { it.name }
        Assert.assertEquals(viewModel.getCountries().value, expected)
        verify(observer).onChanged(expected)
    }
}