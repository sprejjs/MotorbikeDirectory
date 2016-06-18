package com.spreys.motorbikedirectory.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.spreys.motorbikedirectory.MainActivity;
import com.spreys.motorbikedirectory.Model.Make;
import com.spreys.motorbikedirectory.MotorbikesApplication;
import com.spreys.motorbikedirectory.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by vspreys on 18/06/16.
 */

public class MakesFragment extends Fragment {
    private List<Make> makes;

    @Bind(R.id.fragment_makes_listview)
    ListView makesListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_makes, container, false);

        ButterKnife.bind(this, view);

        makes = ((MotorbikesApplication)getActivity().getApplication()).getMakes();

        makesListView.setAdapter(new MakesAdapter(getContext(), makes));
        makesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((MainActivity)getActivity()).loadModelsFragment(makes.get(position));
            }
        });

        return view;
    }

    class MakesAdapter extends ArrayAdapter<Make> {
        List<Make> makes;

        public MakesAdapter(Context context, List<Make> makes) {
            super(context, 0, makes);
            this.makes = makes;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;

            if(convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.make_list_item, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.nameTextView.setText(makes.get(position).getName());

            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.make_list_item_text_view)
            TextView nameTextView;

            ViewHolder(View view){
                ButterKnife.bind(this, view);
            }
        }
    }
}
