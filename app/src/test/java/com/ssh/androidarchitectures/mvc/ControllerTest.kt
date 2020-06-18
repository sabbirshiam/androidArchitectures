package com.ssh.androidarchitectures.mvc

import com.ssh.androidarchitectures.BaseSchedulerProvider
import com.ssh.androidarchitectures.ImmediateSchedulerProvider
import com.ssh.androidarchitectures.model.Country
import com.ssh.androidarchitectures.repositories.CountryRepository
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ControllerTest {

    @Mock
    private lateinit var repository: CountryRepository
    @Mock
    private lateinit var view: MVCActivity

    private lateinit var controller: CountriesController
    private lateinit var schedulerProvider: BaseSchedulerProvider

    private val countries = listOf(Country("a"), Country("b"))

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        schedulerProvider = ImmediateSchedulerProvider()
    }

    @Test
    fun fetchCountriesAndShowIntoView() {
        controller = CountriesController(view,repository, schedulerProvider)
        Mockito.`when`(repository.getCountries()).thenReturn(Single.just(countries))
        Mockito.verify(view).showCountriesName(countries.map { it.name })
    }
}