package com.example.easypeasy.screens.searchByNutrientsList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easypeasy.R;
import com.example.easypeasy.common.utils.Utils;
import com.example.easypeasy.models.Nutrient;

import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.example.easypeasy.common.utils.Constants.PAYLOAD_INSERT_NUTRIENT_FIELD;

public class NutrientsAdapter extends RecyclerView.Adapter<NutrientsAdapter.ViewHolder> {

    interface Listener {
        void insertItemFieldAndNotify();

        void removeItemFieldAndNotify(Nutrient nutrient);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        EditText nutrientAmount;
        Spinner nutrientSpinner;
        TextView nutrientName;
        ImageView insertNutrientField, removeNutrientField;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nutrientAmount = itemView.findViewById(R.id.nutrientAmount);
            insertNutrientField = itemView.findViewById(R.id.addNewNutrientFieldId);
            nutrientSpinner = itemView.findViewById(R.id.nutrientsSpinnerId);
            nutrientName = itemView.findViewById(R.id.nutrientName);
            removeNutrientField = itemView.findViewById(R.id.removeNutrientFieldId);
        }
    }

    private final List<Nutrient> mNutrientList;
    private final Context mContext;
    private final Listener mListener;

    public NutrientsAdapter(List<Nutrient> nutrientList, Context context, NutrientsAdapter.Listener listener) {
        mNutrientList = nutrientList;
        mContext = context;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_insert_nutrient, parent, false);
        return new NutrientsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NutrientsAdapter.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
        } else {
            // compare each Object in the payloads to the PAYLOAD you provided to notifyItemChanged
            for (Object payload : payloads) {
                if (payload.equals(PAYLOAD_INSERT_NUTRIENT_FIELD)) {
                    ((ViewHolder) holder).insertNutrientField.setVisibility(View.GONE);
                    ((ViewHolder) holder).removeNutrientField.setVisibility(View.VISIBLE);
                    ((ViewHolder) holder).nutrientSpinner.setVisibility(View.GONE);
                    ((ViewHolder) holder).nutrientName.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull NutrientsAdapter.ViewHolder holder, int position) {
        Nutrient nutrient = mNutrientList.get(position);

        if (nutrient != null) {
            holder.nutrientAmount.setText(String.valueOf(nutrient.getAmount()));
            holder.nutrientName.setText(nutrient.getName());

            if (position == getItemCount() - 1) {
                holder.insertNutrientField.setVisibility(View.VISIBLE);
                holder.removeNutrientField.setVisibility(View.GONE);
                holder.nutrientSpinner.setVisibility(View.VISIBLE);
                holder.nutrientName.setVisibility(View.INVISIBLE);
            } else {
                holder.insertNutrientField.setVisibility(View.GONE);
                holder.removeNutrientField.setVisibility(View.VISIBLE);
                holder.nutrientSpinner.setVisibility(View.INVISIBLE);
                holder.nutrientName.setVisibility(View.VISIBLE);
            }

            if (nutrient.getName() != null && !nutrient.getName().isEmpty()) {
                holder.nutrientAmount.setEnabled(true);
            }

            Log.d(TAG, "marija: " + Arrays.toString(Utils.getPossibleNutrients(mNutrientList)));

            ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, Utils.getPossibleNutrients(mNutrientList));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.nutrientSpinner.setAdapter(adapter);

            holder.nutrientSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (checkIfNutrientIsAlreadyInserted(parent.getItemAtPosition(position).toString())) {
                        Toast.makeText(mContext, parent.getItemAtPosition(position).toString() + " nutrient is already added. Please choose another nutrient", Toast.LENGTH_LONG).show();
                        holder.nutrientSpinner.setSelection(position + 1);
                    } else {
                        nutrient.setName(parent.getItemAtPosition(position).toString());
                        holder.nutrientName.setText(parent.getItemAtPosition(position).toString());
                        holder.nutrientAmount.setEnabled(true);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            holder.insertNutrientField.setOnClickListener(v -> {
                for (Nutrient n : mNutrientList) {
                    Log.d(TAG, "nutrient: " + n.toString());
                }
                mListener.insertItemFieldAndNotify();
            });

            holder.removeNutrientField.setOnClickListener(v ->
                    mListener.removeItemFieldAndNotify(nutrient));
        }
    }

    private boolean checkIfNutrientIsAlreadyInserted(String selectedNutrientFromSpinner) {
        boolean isNutrientAlreadyInserted = false;
        for (Nutrient n : mNutrientList) {
            if (selectedNutrientFromSpinner.equalsIgnoreCase(n.getName())) {
                isNutrientAlreadyInserted = true;
                break;
            }
        }
        return isNutrientAlreadyInserted;
    }

    @Override
    public int getItemCount() {
        return mNutrientList.size();
    }
}
