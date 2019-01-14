package com.fearaujo.mememaker.web

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.fearaujo.mememaker.R
import com.fearaujo.mememaker.dashboard.REQUEST_PHOTO_URL
import com.fearaujo.mememaker.databinding.WebSelectorFragmentBinding
import com.fearaujo.model.MemeModel
import com.github.pwittchen.infinitescroll.library.InfiniteScrollListener

class WebSelectorFragment : Fragment(), SelectImage {

    companion object {
        const val COLUMNS_NUMBER = 2
        fun newInstance() = WebSelectorFragment()
    }

    private val viewModel: WebSelectorViewModel by lazy {
        ViewModelProviders.of(this).get(WebSelectorViewModel::class.java)
    }

    private val adapter: WebMemesAdapter by lazy {
        WebMemesAdapter(this)
    }

    private val layoutManager: GridLayoutManager by lazy {
        GridLayoutManager(activity, COLUMNS_NUMBER)
    }

    private lateinit var binding: WebSelectorFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.web_selector_fragment, container, false
        )
        binding.viewModel = viewModel
        binding.rvMemes.layoutManager = layoutManager
        binding.rvMemes.adapter = adapter
        binding.rvMemes.setHasFixedSize(true)
        binding.rvMemes.addOnScrollListener(scrollListener)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.memesData.observe(this, Observer<List<MemeModel>> {
            if (it != null) {
                adapter.updateMemes(it)
            }
        })

        viewModel.fetchMemes()
    }

    override fun onSelectImage(imageUrl: String) {
        val intent = Intent()
        intent.putExtra(REQUEST_PHOTO_URL, imageUrl)
        activity?.setResult(Activity.RESULT_OK, intent)
        activity?.finish()
    }

    private val scrollListener = object : InfiniteScrollListener(1, layoutManager) {
        override fun onScrolledToEnd(firstVisibleItemPosition: Int) {
            viewModel.requestNextPage()
        }

    }
}