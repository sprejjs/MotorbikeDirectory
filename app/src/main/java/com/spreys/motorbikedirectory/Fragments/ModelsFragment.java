package com.spreys.motorbikedirectory.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.spreys.motorbikedirectory.MainActivity;
import com.spreys.motorbikedirectory.Model.Make;
import com.spreys.motorbikedirectory.Model.Model;
import com.spreys.motorbikedirectory.Network.ApiWrapper;
import com.spreys.motorbikedirectory.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by vspreys on 18/06/16.
 */

public class ModelsFragment extends Fragment {
    private Make make;

    @Bind(R.id.fragment_models_list_view)
    ListView modelsListView;

    @Bind(R.id.fragment_models_add_new)
    FloatingActionButton btnAddNew;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_models, container, false);

        ButterKnife.bind(this, view);

        make = ((MainActivity)getActivity()).getSelectedMake();
        modelsListView.setAdapter(new ModelsAdapter(getContext(), make));

        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = LayoutInflater.from(getContext()).inflate(R.layout.new_question_layout, null);

                final EditText edtTxtName = (EditText)view.findViewById(R.id.new_model_layout_name);
                final EditText edtTxtEngineSize = (EditText)view.findViewById(R.id.new_model_layout_engine_size);
                final EditText edtTxtClass = (EditText)view.findViewById(R.id.new_model_layout_class);

                new AlertDialog.Builder(getActivity())
                        .setTitle("Add new model")
                        .setView(view)
                        .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Model newModel = new Model(edtTxtName.getText().toString(),
                                        edtTxtClass.getText().toString(),
                                        Integer.valueOf(edtTxtEngineSize.getText().toString())
                                        );

                                new AddModelTask().execute(newModel);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        return view;
    }

    class ModelsAdapter extends ArrayAdapter<Model> {
        Make make;

        public ModelsAdapter(Context context, Make make) {
            super(context, 0, make.getModels());
            this.make = make;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.model_list_item, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }

            Model model = make.getModels().get(position);

            holder.txtViewName.setText(model.getName());
            holder.txtViewEngineSize.setText(model.getEngineSize());
            holder.txtViewClass.setText(model.getModelClass());

            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.model_list_item_text_view_name)
            TextView txtViewName;

            @Bind(R.id.model_list_item_text_view_style)
            TextView txtViewClass;

            @Bind(R.id.model_list_item_text_view_engine_size)
            TextView txtViewEngineSize;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    private class AddModelTask extends AsyncTask<Model, Void, Void> {

        @Override
        protected Void doInBackground(Model... params) {
            try {
                ApiWrapper.AddModel(params[0], make.getName());
                make.addModel(params[0]);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ((ArrayAdapter<Model>)modelsListView.getAdapter()).notifyDataSetChanged();
        }
    }
}
