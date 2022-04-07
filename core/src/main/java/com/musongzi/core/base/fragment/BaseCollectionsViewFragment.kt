package com.musongzi.core.base.fragment

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.musongzi.core.base.business.collection.BaseMoreViewEngine
import com.musongzi.core.base.business.collection.CollectionsViewSupport
import com.musongzi.core.base.business.collection.IHolderCollections
import com.musongzi.core.base.client.CollectionsViewClient
import com.musongzi.core.base.client.IRefreshViewClient
import com.musongzi.core.base.vm.CollectionsViewModel
import com.musongzi.core.itf.page.IDataEngine
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import io.reactivex.rxjava3.core.Observable

abstract class BaseCollectionsViewFragment<D : ViewDataBinding> : LRefreshFrament<CollectionsViewModel, D, Any>(),
    CollectionsViewClient,
    CollectionsViewSupport {

//    private var mIHolderCollections : IHolderCollections? = createHolderCollections()

    private var mRecycleViewClient: IRefreshViewClient = createRecycleViewClient()

    abstract fun createRecycleViewClient(): IRefreshViewClient

//    final override fun openQuickBusinessSet() = false

    override fun initEvent() {
        (getMainViewModel()?.getHolderBusiness()?.base as? IHolderCollections)?.onRefreshViewClientEvent(mRecycleViewClient);
    }

    override fun initData() {
        getMainViewModel()?.getHolderBusiness()?.refresh()
    }

    override fun setRefresh(b: Boolean) {}

    override fun engineName() = null

    companion object {
        @kotlin.jvm.JvmField
        var BUNDLE_KEY = "bundle_key"
    }

    override fun recycleView(): RecyclerView? {
        return mRecycleViewClient.recycleView()
    }

    override fun normalView(): View? {
        return mRecycleViewClient.normalView()
    }

    override fun refreshView(): SmartRefreshLayout? {
        return mRecycleViewClient.refreshView()
    }

    override fun <I, D> getCollectionsViewEngine(): IHolderCollections? {
        return object : BaseMoreViewEngine<I, D>() {


            override fun myAdapter() = this@BaseCollectionsViewFragment.getAdapter()!!

            override fun getLayoutManger() = this@BaseCollectionsViewFragment.getLayoutManger()

            override fun getRemoteDataReal(index: Int): Observable<D>? = this@BaseCollectionsViewFragment.getRemoteData(index);

            override fun transformDataToList(entity: D): List<I> {
               return this@BaseCollectionsViewFragment.transformDataToList(entity);
            }

        }
    }

    protected abstract fun <DATA, ITEM> transformDataToList(entity: DATA): List<ITEM>

    override fun getAdapter(): RecyclerView.Adapter<*>? = null

    fun <D> getRemoteData(index: Int): Observable<D>? = null

    fun <D> createDataEngine(): IDataEngine<D>? = null

    override fun getLayoutManger(): RecyclerView.LayoutManager? = null

    override fun superDatabindingName(): String  = BaseCollectionsViewFragment::class.java.name

    override fun actualTypeArgumentsDatabindinIndex(): Int = 0
}