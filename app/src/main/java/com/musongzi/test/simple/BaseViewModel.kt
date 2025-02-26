package com.musongzi.test.simple

import com.musongzi.core.base.manager.RetrofitManager
import com.musongzi.core.base.vm.MszViewModel
import com.musongzi.core.itf.IBusiness
import com.musongzi.core.itf.IClient
import com.musongzi.core.itf.holder.IHolderApi
import com.musongzi.test.Api

open class BaseViewModel<C : IClient, B : IBusiness> : MszViewModel<C, B>(), IHolderApi<Api> {

    private val apiInstance: Api by lazy {
        RetrofitManager.getInstance().getApi(Api::class.java, this)
    }

    override fun getApi(): Api {
        return apiInstance
    }

}