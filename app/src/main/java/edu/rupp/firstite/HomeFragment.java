package edu.rupp.firstite;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {
    private TextView courseNameTV;
    private ImageView courseIV;
    private ProgressBar loadingPB;
    private CardView courseCV;

    // http://10.1.50.86:8080/api/comdy_information

    String url = "http://127.0.0.1:8080/api/comdy_information";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        loadingPB = view.findViewById(R.id.idLoadingPB);
        courseNameTV = view.findViewById(R.id.idTVCourseName);
        courseIV = view.findViewById(R.id.idIVCourse);
        courseCV = view.findViewById(R.id.idCVCourse);

        RequestQueue queue;
        queue = Volley.newRequestQueue(requireContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONObject response) {
                loadingPB.setVisibility(View.VISIBLE);
                courseCV.setVisibility(View.VISIBLE);

                try {
                    String courseName = response.getString("name");
                    String courseImageURL = response.getString("image");

                    courseNameTV.setText(courseName);

                    Picasso.get().load(courseImageURL).into(courseIV);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Error parsing JSON: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    courseNameTV.setText("Unknown");
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null && error.networkResponse.statusCode == 404) {
                    Toast.makeText(getContext(), "Data not found", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Fail to get data..", Toast.LENGTH_LONG).show();
                }
                Log.d("MyTag", "Error: " + error.getMessage());
            }

        });
        queue.add(jsonObjectRequest);


        return view;
    }
}
