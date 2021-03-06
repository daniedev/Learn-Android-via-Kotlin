package com.mobileapp.learnkotlin.codelabs.androidtrivia.screens


import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.mobileapp.learnkotlin.R
import com.mobileapp.learnkotlin.databinding.FragmentGameWonAndroidTriviaBinding

/**
 * A simple [Fragment] subclass.
 */
class GameWonFragment : Fragment() {

    lateinit var binding: FragmentGameWonAndroidTriviaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_game_won_android_trivia,
            container,
            false
        )
        binding.nextMatchButton.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(GameWonFragmentDirections.actionGameWonFragmentToGameFragment())
        }
        val args =
            GameWonFragmentArgs.fromBundle(
                arguments!!
            )
        Toast.makeText(
            context,
            "NumCorrect : ${args.numCorrect}, NumQuestions : ${args.numQuestions}",
            Toast.LENGTH_LONG
        ).show()
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.trivia_winner_menu, menu)
        if(null == getShareIntent().resolveActivity(activity!!.packageManager)) {
            menu.findItem(R.id.share)?.isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.share -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getShareIntent(): Intent {
        val args =
            GameWonFragmentArgs.fromBundle(
                arguments!!
            )
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(
            Intent.EXTRA_TEXT,
            getString(
                R.string.android_trivia_share_success_text,
                args.numCorrect,
                args.numQuestions
            )
        )
        return shareIntent
    }

    private fun shareSuccess() {
        startActivity(getShareIntent())
    }
}
