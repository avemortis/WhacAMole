package com.example.whac_a_mole.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.whac_a_mole.databinding.ResultFragmentBinding

class ResultFragment : Fragment() , View.OnClickListener {
    private var _binding: ResultFragmentBinding? = null
    private val binding get() = _binding!!

    private val args: ResultFragmentArgs by navArgs()
    private var score : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ResultFragmentBinding.inflate(inflater, container, false)
        val root : View = binding.root

        val result = binding.resultTextView
        score = args.result
        result.text = score.toString()

        val restart = binding.restartButton
        restart.setOnClickListener(this)

        return root
    }

    override fun onClick(v: View?) {
        val action = ResultFragmentDirections.actionResultToMainMenu()
        when (v?.id){
            binding.restartButton.id -> {
                v.findNavController().navigate(action)
            }
        }
    }
}