package com.renatsayf.trade.lists

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.renatsayf.trade.adapters.CategoryAdapter
import com.renatsayf.trade.databinding.FragmentTradeListBinding
import com.renatsayf.trade.models.Category

class TradeListFragment : Fragment() {

    private lateinit var binding: FragmentTradeListBinding
    private val viewModel: TradeListViewModel by viewModels()

    private val categoryAdapter = AsyncListDifferDelegationAdapter(
        CategoryAdapter.diffCallback,
        CategoryAdapter.categoryAdapterDelegate {
            categoryAdapterItemClick(it)
        }
    )

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
                        TODO()
                    }
                }
            }



        }
    }

    private fun categoryAdapterItemClick(category: Category) {

    }

}