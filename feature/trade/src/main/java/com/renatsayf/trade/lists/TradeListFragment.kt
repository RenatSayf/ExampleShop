package com.renatsayf.trade.lists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.renatsayf.network.models.Category
import com.renatsayf.network.models.product.FlashSales
import com.renatsayf.network.models.product.LatestDeals
import com.renatsayf.network.models.product.Product
import com.renatsayf.trade.R
import com.renatsayf.trade.adapters.BrandsAdapter
import com.renatsayf.trade.adapters.CategoryAdapter
import com.renatsayf.trade.adapters.FlashSalesAdapter
import com.renatsayf.trade.adapters.LatestDealsAdapter
import com.renatsayf.trade.databinding.FragmentTradeListBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TradeListFragment : Fragment() {

    private lateinit var binding: FragmentTradeListBinding

    private val viewModel: TradeListViewModel by viewModels()

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
    private val dividerItemDecor9: DividerItemDecoration by lazy {
        DividerItemDecoration(requireContext(), RecyclerView.HORIZONTAL).apply {
            setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.item_divider_transparent_9dp)!!)
        }
    }
    private val dividerItemDecor12: DividerItemDecoration by lazy {
        DividerItemDecoration(requireContext(), RecyclerView.HORIZONTAL).apply {
            setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.item_divider_transparent_12dp)!!)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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
                addItemDecoration(dividerItemDecor12)
                adapter = latestDealsAdapter
            }

            includeFlashLayout.rvFlashSales.apply {
                addItemDecoration(dividerItemDecor9)
                adapter = flashSalesAdapter
            }

            includeListBrands.rvLatest.apply {
                addItemDecoration(dividerItemDecor12)
                adapter = brandsAdapter
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
                            progress.visibility = View.GONE
                            swProducts.visibility = View.INVISIBLE
                        }
                        TradeListViewModel.State.Loading -> {
                            swProducts.visibility = View.INVISIBLE
                            progress.visibility = View.VISIBLE
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

            progress.visibility = View.GONE
            swProducts.visibility = View.VISIBLE
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

    override fun onResume() {
        super.onResume()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })
    }

}