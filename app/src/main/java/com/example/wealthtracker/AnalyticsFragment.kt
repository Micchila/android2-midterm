package com.example.wealthtracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.util.Locale

class AnalyticsFragment : Fragment() {

    private val wealthManager by lazy {
        WealthManager(
            nameLength = StudentIdentity.FULL_NAME.length,
            surnameLength = StudentIdentity.SURNAME.length,
            birthDay = StudentIdentity.BIRTH_DAY_OF_MONTH
        )
    }
    private var latestReport: WealthManager.WealthReport? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentFragmentManager.setFragmentResultListener(
            ResultContract.RESULT_KEY_WEALTH,
            this
        ) { _, bundle ->
            val income = bundle.getDouble(ResultContract.BUNDLE_KEY_INCOME, 0.0)
            val expenses = bundle.getDouble(ResultContract.BUNDLE_KEY_EXPENSES, 0.0)
            latestReport = wealthManager.buildReport(income, expenses)
            val report = latestReport ?: return@setFragmentResultListener
            view?.let { renderReport(it, report) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_analytics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val kValue = wealthManager.calculateK()
        view.findViewById<TextView>(R.id.nm_li_tv_k_value).text = kValue.toString()
        latestReport?.let { renderReport(view, it) }
    }

    private fun formatMoney(value: Double): String = String.format(Locale.US, "%.2f", value)

    private fun renderReport(root: View, report: WealthManager.WealthReport) {
        root.findViewById<TextView>(R.id.nm_li_tv_income_value).text = formatMoney(report.income)
        root.findViewById<TextView>(R.id.nm_li_tv_expenses_value).text = formatMoney(report.expenses)
        root.findViewById<TextView>(R.id.nm_li_tv_net_value).text = formatMoney(report.netIncome)
        root.findViewById<TextView>(R.id.nm_li_tv_k_value).text = report.kCoefficient.toString()
        root.findViewById<TextView>(R.id.nm_li_tv_final_value).text = formatMoney(report.finalSavings)
    }
}
