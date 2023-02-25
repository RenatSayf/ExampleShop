package com.renatsayf.trade.lists

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.renatsayf.trade.R
import com.renatsayf.trade.databinding.FragmentTradeListBinding

class TradeListFragment : Fragment() {

    private lateinit var binding: FragmentTradeListBinding
    private lateinit var viewModel: TradeListViewModel

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


        }
    }

}