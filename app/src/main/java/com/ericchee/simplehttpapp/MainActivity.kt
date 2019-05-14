package com.ericchee.simplehttpapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setUpVolleyFetching()
        setUpUrlAnkoFetching()
    }

    /**
     * Fetch using Volley library
     */
    private fun setUpVolleyFetching() {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "http://www.google.com"

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                showLoadingSpinner(false)

                // Display the first 500 characters of the response string.
                tvResponse.text = "Response is: ${response.substring(0, 500)}"
            },
            Response.ErrorListener {
                showLoadingSpinner(false)
                tvResponse.text = "That didn't work!"
            }
        )


        // Button fetchs
        btnFetchVolley.setOnClickListener {
            showLoadingSpinner(true)

            // Add the request to the RequestQueue.
            queue.add(stringRequest)
        }
    }

    /**
     * Fetch using kotlin's URL extension functions + Anko library
     */
    private fun setUpUrlAnkoFetching() {
        btnFetchAnko.setOnClickListener {
            doAsync {
                val response = URL("http://www.google.com").readText()
                uiThread {
                    showLoadingSpinner(false)

                    // Display the first 500 characters of the response string.
                    tvResponse.text = "Response is: ${response.substring(0, 500)}"
                }
            }
        }
    }

    private fun showLoadingSpinner(show: Boolean) {
        loadingSpinner.visibility = if (show) View.VISIBLE else View.GONE
    }
}
