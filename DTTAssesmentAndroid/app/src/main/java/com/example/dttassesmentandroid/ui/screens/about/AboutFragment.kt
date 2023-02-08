package com.example.dttassesmentandroid.ui.screens.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import coil.load
import com.example.dttassesmentandroid.R
import com.example.dttassesmentandroid.common.Constants
import com.example.dttassesmentandroid.databinding.FragmentAboutBinding
import com.example.dttassesmentandroid.ui.screens.main.MainActivity
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AboutFragment : Fragment() {
    private lateinit var binding: FragmentAboutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configTopAppBar()
        binding.imageView.load(R.drawable.dtt_banner)
    }

    private fun configTopAppBar() {
        val topAppBar = (activity as MainActivity).findViewById<MaterialToolbar>(R.id.toolbar)
        topAppBar.menu.clear()
        val topAppBarTextView =  (activity as MainActivity).findViewById<TextView>(R.id.toolbar_title)
        topAppBarTextView.text = Constants.ABOUT_FRAGMENT_TITLE
    }
}