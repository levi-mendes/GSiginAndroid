package com.example.googlesiginsample

import android.widget.Toast
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {

    fun showToast(msg: String) =
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()

}