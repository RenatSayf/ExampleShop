@file:Suppress("ObjectLiteralToLambda")

package com.renatsayf.trade.lists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.renatsayf.network.models.Category
import com.renatsayf.network.models.product.FlashSales
import com.renatsayf.network.models.product.LatestDeals
import com.renatsayf.network.models.product.Product
import com.renatsayf.resourses.extensions.setPopUpMenu
import com.renatsayf.resourses.extensions.toDeepLink
import com.renatsayf.trade.R
import com.renatsayf.trade.adapters.BrandsAdapter
import com.renatsayf.trade.adapters.CategoryAdapter
import com.renatsayf.trade.adapters.FlashSalesAdapter
import com.renatsayf.trade.adapters.LatestDealsAdapter
import com.renatsayf.trade.databinding.FragmentTradeListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


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
    private val dividerItemDecor16: DividerItemDecoration by lazy {
        DividerItemDecoration(requireContext(), RecyclerView.HORIZONTAL).apply {
            setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.item_divider_transparent_16dp)!!)
        }
    }
    private val hintAdapter: ArrayAdapter<String> by lazy {
        ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item)
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
            includeCategory.rvCategoryList.apply {
                addItemDecoration(dividerItemDecor16)
                setInfinite(true)
                setFlat(true)
            }

            includeListLatest.rvLatest.apply {
                adapter = latestDealsAdapter
                setInfinite(true)
                setFlat(true)
            }

            includeFlashLayout.rvFlashSales.apply {
                adapter = flashSalesAdapter
                setInfinite(true)
                setFlat(true)
            }

            includeListBrands.rvBrands.apply {
                adapter = brandsAdapter
                setInfinite(true)
                setFlat(true)
            }

            imgPhoto.setOnClickListener {
                val deepLink = "ExampleShop://profile".toUri()
                findNavController().navigate("profile".toDeepLink())
            }

            btnUserLocation.setOnClickListener {
                it.setPopUpMenu(R.menu.menu_location).apply {
                    this.menu.findItem(R.id.location).title = getDeviceLocation()
                }.show()
            }

            lifecycleScope.launchWhenStarted {
                viewModel.categoryList.collect { res ->
                    res.onSuccess { list ->
                        categoryAdapter.items = list
                    }
                }
            }
            lifecycleScope.launchWhenStarted {
                viewModel.brandsList.collect { brands ->
                    includeListBrands.apply {
                        includeHeader.tvTitle.text = brands.getListTitle()
                        brandsAdapter.items = brands.brands
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
                            if (!flash.flash_sale.isNullOrEmpty() && !latest.latest.isNullOrEmpty()) {
                                handleSuccessState(flash, latest)
                            }
                        }
                    }
                }
            }
            includeSearch.etSearch.apply {
                setAdapter(hintAdapter)
                doAfterTextChanged {
                    lifecycleScope.launch {
                        viewModel.getHint().collect {
                            hintAdapter.addAll(it.words)
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

            includeListLatest.apply {
                includeHeader.tvTitle.text = latest.getListTitle()
                latestDealsAdapter.items = latest.latest
            }
            includeFlashLayout.apply {
                includeHeader.tvTitle.text = flash.getListTitle()
                flashSalesAdapter.items = flash.flash_sale
                flashSalesAdapter.setItems(flash.flash_sale)
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

    fun getDeviceLocation(): String {
        return "Delhi"
    }

}