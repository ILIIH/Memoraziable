package com.lum.plain_ui

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.lum.plain_ui.R
import com.lum.plain_data.Task
import com.lum.plain_ui.databinding.FragmentPlainBinding
import com.lum.plain_ui.navigation.PlainNavigation
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class PlainFragment : Fragment() {

    private val viewModel: PlainViewModel by viewModel()
    private val navigation: PlainNavigation by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentPlainBinding.inflate(inflater, container, false)
        lifecycleScope.launch {
            viewModel.tasks.onEach { themes ->
                if (themes?.isEmpty() == true) {
                    renderEmptyThemes(view)
                } else {
                    themes?.let { render(view, it) }
                }
            }.collect()
        }

        return view.root
    }
    private fun renderEmptyThemes(view: FragmentPlainBinding){

        view.plainView.visibility = View.GONE
        view.teacher.startButton.visibility = View.GONE
        view.buttonsLayout.visibility = View.GONE

        view.teacher.ruleText.visibility = View.VISIBLE
        view.teacher.root.visibility = View.VISIBLE
        view.teacher.messageBottomPart.visibility = View.VISIBLE

        SpannableString(getString(R.string.no_theme_created)).apply {
            val create = getString(R.string.create_theme)
            val createIndex = indexOf(create)
            val clickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    navigation.toCreateNewTheme()
                }
            }

            setSpan(clickableSpan, createIndex, createIndex + create.length, 0)
            setSpan(ForegroundColorSpan( ContextCompat.getColor(requireContext(),android.R.color.black )),
                createIndex, createIndex + create.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)


            setSpan(StyleSpan(Typeface.BOLD), createIndex, createIndex + create.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(UnderlineSpan(), createIndex, +createIndex+ create.length, 0)

            view.teacher.ruleText.text = this
            view.teacher.ruleText.movementMethod = LinkMovementMethod.getInstance()
        }

    }

    private fun render(view: FragmentPlainBinding, tasksList: List<Task>){
        view.plainView.visibility = View.VISIBLE

        view.teacher.ruleText.visibility = View.GONE
        view.teacher.root.visibility = View.GONE
        view.teacher.messageBottomPart.visibility = View.GONE

        val states = arrayOf(
            intArrayOf(-android.R.attr.state_enabled),
            intArrayOf(android.R.attr.state_enabled)
        )
        val colorUnselectedStateList = ColorStateList(
            states, intArrayOf(
                Color.BLACK,
                Color.BLACK
            )
        )

        val colorSelectedStateList = ColorStateList(
            states , intArrayOf(
                Color.BLACK,
                ContextCompat.getColor(requireContext(),com.lum.core.R.color.secondary)
            )
        )


        view.day.setOnClickListener {
            turnOffRadioBtn(view, colorUnselectedStateList, colorSelectedStateList)
            view.plainView.changePeriodType(getString(R.string.day))
            view.day.buttonTintList = colorSelectedStateList

        }
        view.week.setOnClickListener {
            turnOffRadioBtn(view, colorUnselectedStateList, colorSelectedStateList)
            view.plainView.changePeriodType(getString(R.string.week))
            view.week.buttonTintList =  colorSelectedStateList

        }
        view.month.setOnClickListener {
            turnOffRadioBtn(view, colorUnselectedStateList, colorSelectedStateList)
            view.plainView.changePeriodType(getString(R.string.month))
            view.month.buttonTintList = colorSelectedStateList
        }

        view.plainView.setTasks(tasksList)
    }

    private fun turnOffRadioBtn(view : FragmentPlainBinding, colorUnselectedStateList:ColorStateList, colorSelectedStateList:ColorStateList ){
        view.day.buttonTintList =  colorUnselectedStateList

        view.week.buttonTintList =  colorUnselectedStateList

        view.month.buttonTintList =  colorUnselectedStateList
    }
}
