package com.renatsayf.trade.lists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.renatsayf.network.models.Category
import com.renatsayf.network.repository.NetRepository
import com.renatsayf.trade.adapters.CategoryAdapter
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

    private val categoryAdapter = AsyncListDifferDelegationAdapter(
        CategoryAdapter.diffCallback,
        CategoryAdapter.categoryAdapterDelegate {
            categoryAdapterItemClick(it)
        }
    )

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

            includeCategory.rvCategoryList.apply {
//                this.setFlat(true)
//                setInfinite(true)
//                setIntervalRatio(0.9f)
            }.adapter = categoryAdapter

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
                            latest
                        }
                    }
                }
            }
        }
    }

    private fun categoryAdapterItemClick(category: Category) {

    }

}