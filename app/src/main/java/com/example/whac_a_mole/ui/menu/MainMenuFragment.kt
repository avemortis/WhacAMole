package com.example.whac_a_mole.ui.menu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.whac_a_mole.R
import com.example.whac_a_mole.databinding.MainMenuFragmentBinding

class MainMenuFragment : Fragment() , View.OnClickListener {
    private val TAG = "MainMenu"

    private var _binding: MainMenuFragmentBinding? = null
    private val binding get() = _binding!!

    var amount : Int = 9

    private lateinit var amountSwitcherButton4 : Button
    private lateinit var amountSwitcherButton9 : Button
    private lateinit var amountSwitcherButton16 : Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainMenuFragmentBinding.inflate(inflater, container, false)
        val root : View = binding.root

        amountSwitcherButton4 = binding.amountSwitcher4
        amountSwitcherButton9 = binding.amountSwitcher9
        amountSwitcherButton16 = binding.amountSwitcher16
        amountSwitcherButton4.setOnClickListener(this)
        amountSwitcherButton9.setOnClickListener(this)
        amountSwitcherButton16.setOnClickListener(this)

        val startButton : Button = binding.startButton
        startButton.setOnClickListener(this)

        choose(9)

        return root
    }

    override fun onClick(v: View?) {
        val action = MainMenuFragmentDirections.actionMainMenuToGame()
        action.amount = amount
        when(v?.id){
            R.id.start_button ->
                v.findNavController().navigate(action)
            R.id.amount_switcher_4 ->
                choose(4)
            R.id.amount_switcher_9 ->
                choose(9)
            R.id.amount_switcher_16 ->
                choose(16)
        }
    }
    fun choose (choice : Int){
        setUnactive(amountSwitcherButton4)
        setUnactive(amountSwitcherButton9)
        setUnactive(amountSwitcherButton16)

        when(choice){
            4 -> {
                amount = 4
                setActive(amountSwitcherButton4)
            }
            9 -> {
                amount = 9
                setActive(amountSwitcherButton9)
            }
            16 -> {
                amount = 16
                setActive(amountSwitcherButton16)
            }
        }
    }

    fun setUnactive(button: Button){
        button.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
    }
    fun setActive(button: Button){
        button.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.purple_500))
    }
}