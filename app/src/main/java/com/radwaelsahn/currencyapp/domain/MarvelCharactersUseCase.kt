package com.radwaelsahn.currencyapp.domain

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.radwaelsahn.currencyapp.data.Resource
import com.radwaelsahn.currencyapp.data.models.Character
import com.radwaelsahn.currencyapp.data.models.responses.BaseResponse
import com.radwaelsahn.currencyapp.data.source.DataSource
import com.radwaelsahn.currencyapp.utils.Constants
import com.radwaelsahn.currencyapp.utils.convertToMd5
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Created by Radwa Elsahn on 7/7/2020
 */

class MarvelCharactersUseCase @Inject constructor(
    private val dataRepository: DataSource,
    override val coroutineContext: CoroutineContext
) : CoroutineScope {

    val isLastPage = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()
    private val _uiFlow = MutableStateFlow<Resource<List<Character>>>(Resource.Loading(true))
    val uiFlow: StateFlow<Resource<List<Character>>> = _uiFlow


    private val _resource = MutableLiveData<Resource<BaseResponse<Character>>>()
    val resource = _resource

    private val _response = MutableLiveData<Resource<BaseResponse<Character>>>()
    val response = _response

    fun getMarvelCharacters(page: Int) {

        val pagesNumber = response.value?.let { it.data?.data?.total!! / Constants.pageSize } ?: 1
        if (page == 1 || page <= pagesNumber) {
            isLastPage.value = false
            val ts = System.currentTimeMillis().toString()
            val hash =
                convertToMd5(ts + Constants.marvel_private_key + Constants.marvel_public_key)

            launch {
                try {
                    _uiFlow.value = Resource.Loading(true)
                    isLoading.value = true
                    var resources = dataRepository.getMarvelCharacters(
                        ts,
                        Constants.marvel_public_key,
                        hash,
                        Constants.pageSize,
                        page
                    )
                    _uiFlow.value = Resource.Loading(false)
                    isLoading.value = false
                    if (resources.errorResponse != null) {
                        _uiFlow.value = Resource.Error(resources.errorResponse?.message)
                        _uiFlow.value = Resource.Success(dataRepository.getAllCharacters())
                    } else if (resources!!.data != null) {
                        _response.postValue(resources)
                        _uiFlow.value = Resource.Success(resources.data?.data?.results)

                        resources.data?.data?.results?.map {
                            Log.e("charName", it.name)
                            dataRepository.saveCharacter(it)
                            Log.e("char size", dataRepository.getAllCharacters()?.size.toString())
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    _uiFlow.value = Resource.Loading(false)
                    isLoading.value = false
                    _uiFlow.value = Resource.Error(e.message)
                }
            }
        } else
            isLastPage.value = true
    }

}
