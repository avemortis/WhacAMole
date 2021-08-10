package com.example.whac_a_mole.ui.game

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.ScaleDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.whac_a_mole.R
import com.example.whac_a_mole.databinding.GameFragmentBinding
import com.example.whac_a_mole.model.Hole
import kotlin.math.sqrt
import kotlin.random.Random

class GameProcessFragment : Fragment() , View.OnClickListener {
    private val TAG = "GameProcessTag"
    private lateinit var gameProcessViewModel: GameProcessViewModel

    private var _binding: GameFragmentBinding? = null
    private val binding get() = _binding!!

    private val args: GameProcessFragmentArgs by navArgs()
    private var holesAmount : Double = 0.0

    private val roundTime : Long = 30000
    private val intervalBetweenShowing : Long = 600
    lateinit var timer: CountDownTimer

    private val holesViewList : MutableList<ImageView> = mutableListOf()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        gameProcessViewModel =
            ViewModelProvider(this).get(GameProcessViewModel::class.java)
        _binding = GameFragmentBinding.inflate(inflater, container, false)
        val root : View = binding.root

        val score = binding.score
        val time = binding.gameFragmentTimer

        gameProcessViewModel.score.observe(viewLifecycleOwner, { score.text = it.toString() })
        gameProcessViewModel.time.observe(viewLifecycleOwner, { t ->
            time.text = t.toString()
        if (t < 1) showResult(gameProcessViewModel.score.value!!)})

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        holesAmount = args.amount.toDouble()
        setGamePole(binding.gamePoleMatrixRoot, sqrt(holesAmount).toInt(), sqrt(holesAmount).toInt())
        gameProcessViewModel.startGame(roundTime, intervalBetweenShowing)
        gameProcessViewModel.activeHole.observe(viewLifecycleOwner, {
            showMoleInHole(it)
        })
    }

    override fun onClick(v: View?) {
        Log.d(TAG, v?.id.toString())
        gameProcessViewModel.check(v!!.id)
    }

    private fun setGamePole(pole : LinearLayout, lineAmount : Int, columnAmount : Int) {
        for (i in 0 until lineAmount){
            val column = LinearLayout(requireContext())
            column.orientation = LinearLayout.HORIZONTAL
            column.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            pole.addView(column)
            for (j in 0 until columnAmount){
                val hole = ImageView(requireContext())
                hole.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_hole))
                hole.id = i * columnAmount + j
                hole.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                if (gameProcessViewModel.holes.size < holesAmount.toInt()) gameProcessViewModel.holes.add(Hole())
                holesViewList.add(hole)
                column.addView(hole)
                hole.setOnClickListener(this)
            }
        }
    }


    private fun showMoleInHole(count : Int){
        holesViewList.forEach{
            hideMoleInHole(it)
        }
        val moleSize = 64
        var moleDrawable : Drawable? = ContextCompat.getDrawable(requireContext(), R.drawable.mole)
        val bitmap : Bitmap = moleDrawable!!.toBitmap(moleSize, moleSize)
        gameProcessViewModel.activateHole(count)
        holesViewList[count].setImageBitmap(bitmap)
    }

    private fun hideMoleInHole(hole : ImageView){
        hole.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_hole))
    }

    fun showResult(score : Int){
        val action = GameProcessFragmentDirections.actionGameToResult()
        action.result = score
        binding.root.findNavController().navigate(action)
    }

}