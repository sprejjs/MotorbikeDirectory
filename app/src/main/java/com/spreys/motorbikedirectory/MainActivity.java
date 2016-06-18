package com.spreys.motorbikedirectory;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.spreys.motorbikedirectory.Fragments.MakesFragment;
import com.spreys.motorbikedirectory.Model.Make;
import com.spreys.motorbikedirectory.Network.ApiWrapper;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new GetMotorbikesTask().execute();
    }

    private void loadMakesFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.android_main_activity_container, new MakesFragment())
                .commit();
    }

    private class GetMotorbikesTask extends AsyncTask<Void, Void, List<Make>> {

        @Override
        protected List<Make> doInBackground(Void... params) {
            return ApiWrapper.GetAllMakes();
        }

        @Override
        protected void onPostExecute(List<Make> makes) {
            super.onPostExecute(makes);

            ((MotorbikesApplication)getApplication()).setMakes(makes);
            loadMakesFragment();
        }
    }
}
