package com.renatsayf.trade.lists

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.renatsayf.network.models.Category
import com.renatsayf.network.models.product.FlashSales
import com.renatsayf.network.models.product.LatestDeals
import com.renatsayf.network.models.product.Product
import com.renatsayf.network.repository.NetRepository
import com.renatsayf.trade.adapters.*
import com.renatsayf.trade.databinding.FragmentTradeListBinding
import javax.inject.Inject

class TradeListFragment : Fragment() {

    private lateinit var binding: FragmentTradeListBinding

    @Inject
    lateinit var netRepository: NetRepository

    private val viewModel: TradeListViewModel by lazy {
        val factory = TradeListViewModel.Factory(netRepository)
        ViewModelProvider(this, factory)[TradeListViewModel::class.java]
    }

    private val categoryAdapter: AsyncListDifferDelegationAdapter<Category> by lazy {
        AsyncListDifferDelegationAdapter(
            CategoryAdapter.diffCallback,
            CategoryAdapter.categoryAdapterDelegate {
                categoryAdapterItemClick(it)
            }
        )
    }
    private val latestDealsAdapter: AsyncListDifferDelegationAdapter<Product> by lazy {
        AsyncListDifferDelegationAdapter(
            LatestDealsAdapter.diffCallback,
            LatestDealsAdapter().latestDealsAdapterDelegate {
                latestDealsItemClick(it)
            }
        )
    }
    private val flashSalesAdapter: AsyncListDifferDelegationAdapter<Product> by lazy {
        AsyncListDifferDelegationAdapter(
            FlashSalesAdapter.diffCallback,
            FlashSalesAdapter().flashSalesAdapterDelegate {
                flashSalesItemClick(it)
            }
        )
    }
    private val brandsAdapter: AsyncListDifferDelegationAdapter<Product> by lazy {
        AsyncListDifferDelegationAdapter(
            BrandsAdapter.diffCallback,
            BrandsAdapter().brandsAdapterDelegate {
                brandsItemClick(it)
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTradeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            includeCategory.rvCategoryList.adapter = categoryAdapter
            includeListLatest.rvLatest.apply {
                addItemDecoration(VerticalSpaceDecoration())
                adapter = latestDealsAdapter
            }
            includeFlashLayout.rvFlashSales.apply {
                addItemDecoration(VerticalSpaceDecoration())
                adapter = flashSalesAdapter
            }

            lifecycleScope.launchWhenStarted {
                viewModel.categoryList.collect { res ->
                    res.onSuccess { list ->
                        categoryAdapter.items = list
                    }
                    res.onFailure {
                        //TODO()
                    }
                }
            }
            lifecycleScope.launchWhenStarted {
                viewModel.state.collect { state ->
                    when(state) {
                        TradeListViewModel.State.EmptyData -> {

                        }
                        TradeListViewModel.State.Loading -> {

                        }
                        is TradeListViewModel.State.Success -> {
                            val flash = state.flash
                            val latest = state.latest
                            if (flash.flash_sale.isNotEmpty() && latest.latest.isNotEmpty()) {
                                handleSuccessState(flash, latest)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun handleSuccessState(flash: FlashSales, latest: LatestDeals) {
        with(binding) {

            includeListLatest.run {
                includeHeader.tvTitle.text = latest.getListTitle()
                latestDealsAdapter.items = latest.latest
            }
            includeFlashLayout.run {
                includeHeader.tvTitle.text = flash.getListTitle()
                flashSalesAdapter.items = flash.flash_sale
            }
            includeListBrands.run {
                includeHeader.tvTitle.text = "Brands"
                brandsAdapter.items = latest.latest
            }
        }
    }

    private fun categoryAdapterItemClick(category: Category) {

    }

    private fun latestDealsItemClick(product: Product) {

    }

    private fun flashSalesItemClick(product: Product) {

    }

    private fun brandsItemClick(product: Product) {

    }

}