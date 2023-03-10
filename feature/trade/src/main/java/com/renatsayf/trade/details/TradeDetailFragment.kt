package com.renatsayf.trade.details

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.renatsayf.network.models.details.ProductDetails
import com.renatsayf.resourses.extensions.dp
import com.renatsayf.resourses.extensions.toDeepLink
import com.renatsayf.trade.BuildConfig
import com.renatsayf.trade.R
import com.renatsayf.trade.adapters.ColorListAdapter
import com.renatsayf.trade.adapters.ProductImageAdapter
import com.renatsayf.trade.databinding.FragmentTradeDetailBinding
import com.renatsayf.trade.databinding.ItemImagePointBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TradeDetailFragment : Fragment() {

    private lateinit var binding: FragmentTradeDetailBinding
    private val viewModel: TradeDetailViewModel by viewModels()

    private val productImageAdapter: AsyncListDifferDelegationAdapter<String> by lazy {
        AsyncListDifferDelegationAdapter(
            ProductImageAdapter.diffCallback,
            ProductImageAdapter().productImageDelegate()
        )
    }
    private val productColorAdapter: AsyncListDifferDelegationAdapter<String> by lazy {
        AsyncListDifferDelegationAdapter(
            ColorListAdapter.diffCallback,
            ColorListAdapter().productColorDelegate {
                colorAdapterItemClick(it)
            }
        )
    }
    private val colorItemsDivider: DividerItemDecoration by lazy {
        DividerItemDecoration(requireContext(), RecyclerView.HORIZONTAL).apply {
            setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.item_divider_transparent_16dp)!!)
        }
    }
    private val imageItemsDivider: DividerItemDecoration by lazy {
        DividerItemDecoration(requireContext(), RecyclerView.HORIZONTAL).apply {
            setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.item_divider_transparent_26dp)!!)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTradeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            rvImages.apply {
                adapter = productImageAdapter
                addItemDecoration(imageItemsDivider)

                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        val visibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                        setProductIndicator(visibleItemPosition)
                    }
                })
            }

            rvColors.apply {
                adapter = productColorAdapter
                addItemDecoration(colorItemsDivider)
            }

            layoutToCart.setOnClickListener {
                findNavController().navigate("cart".toDeepLink())
            }

            btnMinus.setOnClickListener {
                val balance = tvBalance.text.toString().replace("#", "").trim().toDouble()
                val price = tvPrice.text.toString().replace("$", "").trim().toDouble()
                val newBalance = balance - price
                if (newBalance >= viewModel.getBalance()) tvBalance.text = "#$newBalance"
            }
            btnPlus.setOnClickListener {
                val balance = tvBalance.text.toString().replace("#", "").trim().toDouble()
                val price = tvPrice.text.toString().replace("$", "").trim().toDouble()
                val newBalance = balance + price
                tvBalance.text = "#$newBalance"
            }

            lifecycleScope.launchWhenResumed {
                viewModel.state.collect { state ->
                    when(state) {
                        is TradeDetailViewModel.State.DataFailure -> {
                            progress.visibility = View.GONE
                            Snackbar.make(binding.root, state.error, Snackbar.LENGTH_LONG).show()
                        }
                        is TradeDetailViewModel.State.DataSuccess -> {
                            handleSuccessData(state.details)
                        }
                        TradeDetailViewModel.State.Loading -> {
                            progress.visibility = View.VISIBLE
                        }
                    }
                }
            }

        }
    }

    private fun handleSuccessData(details: ProductDetails) {

        with(binding) {

            progress.visibility = View.GONE
            productImageAdapter.items = details.imageUrls
            tvProductName.text = details.name
            tvDescription.text = details.description

            val displayPrice = "$ ${details.price}"
            tvPrice.text = displayPrice

            tvStars.text = details.rating.toString()
            val displayReviews = "(${details.reviews} reviews)"
            tvReviews.text = displayReviews

            val displayBalance = "#${viewModel.getBalance()}"
            tvBalance.text = displayBalance

            productColorAdapter.items = details.colors

            val indicatorsList = productImageAdapter.items

            repeat(indicatorsList.size, action = { index ->

                val imagePointBinding = ItemImagePointBinding.inflate(layoutInflater, binding.root, false)

                layoutIndicator.addView(imagePointBinding.root)

                Glide.with(requireContext()).load(indicatorsList[index])
                    .addListener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {

                            val layoutParams = imagePointBinding.root.layoutParams as LinearLayoutCompat.LayoutParams
                            val newLayoutParams = LinearLayoutCompat.LayoutParams(layoutParams.width, layoutParams.height)
                                    .apply {
                                        setMargins(3.dp, 0, 3.dp, 0)
                                    }
                            imagePointBinding.root.layoutParams = newLayoutParams
                            imagePointBinding.cvProduct.setOnClickListener {
                                rvImages.smoothScrollToPosition(index)
                            }
                            return false
                        }

                    })
                    .placeholder(R.drawable.product_small_image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imagePointBinding.imgProduct)
            })
        }
    }

    private fun setProductIndicator(position: Int) {

        val defaultWidth = 66.dp
        val defaultHeight = 38.dp
        with(binding) {

            val children = layoutIndicator.children.toList()
            children.forEach {
                val frameLayout = it as FrameLayout
                val cardView = frameLayout.findViewById<CardView>(R.id.cv_product)
                cardView.layoutParams = FrameLayout.LayoutParams(defaultWidth, defaultHeight)
            }

            try {
                children[position].apply {
                    val frameLayout = this as FrameLayout
                    val cardView = frameLayout.findViewById<CardView>(R.id.cv_product)
                    cardView.layoutParams = FrameLayout.LayoutParams(83.dp, 45.dp)
                    cardView.cardElevation = (20.dp).toFloat()
                }
            } catch (e: IndexOutOfBoundsException) {
                if (BuildConfig.DEBUG) e.printStackTrace()
            }
            Unit
        }
    }

    private fun colorAdapterItemClick(color: String) {
        Snackbar.make(binding.root, "Selected color $color", Snackbar.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}

