package com.radwaelsahn.currencyapp.data.remote

import com.radwaelsahn.currencyapp.data.source.local.LocalRepository
import com.radwaelsahn.currencyapp.data.source.remote.RemoteRepository
import okhttp3.ResponseBody
import javax.inject.Inject


/**
 * Created by Radwa Elsahn on 7/7/2020
 */

class DataRepository @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository,
) : DataSource {


}