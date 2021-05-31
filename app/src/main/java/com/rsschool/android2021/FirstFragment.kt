package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var minValue: EditText? = null
    private var maxValue: EditText? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previousResult)
        generateButton = view.findViewById(R.id.generate)
        minValue = view.findViewById(R.id.minValue)
        maxValue = view.findViewById(R.id.maxValue)
        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        generateButton?.setOnClickListener {
            if(minValue?.text?.isNotEmpty() == true && maxValue?.text?.isNotEmpty() == true) {
                val minString = minValue?.text?.toString() ?: null
                val maxString = maxValue?.text?.toString()
                if (!(minString!![0] == '0' && minString.length > 1) && !(maxString!![0] == '0' && maxString.length > 1)) {
                    val min: Int? = minValue?.text?.toString()?.toIntOrNull()
                    val max: Int? = maxValue?.text?.toString()?.toIntOrNull()
                    when {
                        (min == null) || (max == null) -> Toast.makeText(
                            activity?.applicationContext,
                            "Exceeded the maximum value!!!",
                            Toast.LENGTH_LONG
                        ).show()

                        (min < 0) || (max < 0) -> Toast.makeText(
                            activity?.applicationContext,
                            "min>=0 and max>=0!!!",
                            Toast.LENGTH_LONG
                        ).show()

                        (min > max) -> Toast.makeText(
                            activity?.applicationContext,
                            "min <= max !!!",
                            Toast.LENGTH_LONG
                        ).show()

                        else -> mListener?.onFirstFragmentDataListener(min, max)
                    }
                }
                else {
                    Toast.makeText(
                        activity?.applicationContext,
                        "The value is not a number!!!",
                        Toast.LENGTH_LONG
                    ).show()
                }
                } else {
                    Toast.makeText(
                        activity?.applicationContext,
                        "Enter number!!!",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
    }

    interface OnFirstFragmentDataListener {
        fun onFirstFragmentDataListener(min: Int, max: Int)
    }

    private var mListener: OnFirstFragmentDataListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = if (context is OnFirstFragmentDataListener) {
            context
        } else {
            throw RuntimeException(
                context.toString()
                        + " must implement OnFirstFragmentDataListener"
            )
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}