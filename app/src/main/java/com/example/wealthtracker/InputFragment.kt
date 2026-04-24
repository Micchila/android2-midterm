package com.example.wealthtracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class InputFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val incomeEditText = view.findViewById<TextInputEditText>(R.id.nk_ol_et_income)
        val expensesEditText = view.findViewById<TextInputEditText>(R.id.nk_ol_et_expenses)
        val saveButton = view.findViewById<MaterialButton>(R.id.nk_ol_btn_save)
        val clearButton = view.findViewById<MaterialButton>(R.id.nk_ol_btn_clear)

        saveButton.setOnClickListener {
            val incomeText = incomeEditText.text?.toString()?.trim().orEmpty()
            val expensesText = expensesEditText.text?.toString()?.trim().orEmpty()

            val income = incomeText.toDoubleOrNull()
            val expenses = expensesText.toDoubleOrNull()

            var isValid = true
            if (income == null) {
                incomeEditText.error = getString(R.string.error_income_required)
                isValid = false
            } else {
                incomeEditText.error = null
            }

            if (expenses == null) {
                expensesEditText.error = getString(R.string.error_expenses_required)
                isValid = false
            } else {
                expensesEditText.error = null
            }

            if (!isValid) return@setOnClickListener

            parentFragmentManager.setFragmentResult(
                ResultContract.RESULT_KEY_WEALTH,
                bundleOf(
                    ResultContract.BUNDLE_KEY_INCOME to income,
                    ResultContract.BUNDLE_KEY_EXPENSES to expenses
                )
            )

            (activity as? MainActivity)?.openAnalyticsTab()
            Toast.makeText(requireContext(), getString(R.string.saved_successfully), Toast.LENGTH_SHORT)
                .show()
        }

        clearButton.setOnClickListener {
            incomeEditText.setText("")
            expensesEditText.setText("")
            incomeEditText.error = null
            expensesEditText.error = null

            parentFragmentManager.setFragmentResult(
                ResultContract.RESULT_KEY_WEALTH,
                bundleOf(
                    ResultContract.BUNDLE_KEY_INCOME to 0.0,
                    ResultContract.BUNDLE_KEY_EXPENSES to 0.0
                )
            )

            (activity as? MainActivity)?.openAnalyticsTab()
            Toast.makeText(requireContext(), getString(R.string.cleared_successfully), Toast.LENGTH_SHORT)
                .show()
        }
    }
}
