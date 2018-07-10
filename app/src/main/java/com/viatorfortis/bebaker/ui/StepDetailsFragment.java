package com.viatorfortis.bebaker.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.viatorfortis.bebaker.R;
import com.viatorfortis.bebaker.model.Step;

import java.util.ArrayList;

public class StepDetailsFragment extends Fragment {

    private ArrayList<Step> mStepList;
    private int mStepId;

    private TextView mStepDescriptionTextView;
    private Button mPrevStepButton;
    private Button mNextStepButton;

    public void setStep(ArrayList<Step> stepList, int stepId) {
        mStepList = stepList;
        mStepId = stepId;
    }

    public StepDetailsFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);

        mStepDescriptionTextView = rootView.findViewById(R.id.tv_step_description);
        mPrevStepButton = rootView.findViewById(R.id.b_prev_step);
        mNextStepButton = rootView.findViewById(R.id.b_next_step);


        if (savedInstanceState == null) {
            populateUI();
            setButtonClickListeners();
        }
//        else {
//            ;
//        }

        return rootView;
    }

    private void setButtonClickListeners() {

        mPrevStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStepId--;
                populateUI();
            }
        });

        mNextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStepId++;
                populateUI();
            }
        });
    }

    private void populateUI() {
        mStepDescriptionTextView.setText(mStepList.get(mStepId).getDescription() );

        if (mStepId == 0) {
            mPrevStepButton.setVisibility(View.GONE);
        } else {
            mPrevStepButton.setVisibility(View.VISIBLE);
        }

        if (mStepId == (mStepList.size() - 1) ) {
            mNextStepButton.setVisibility(View.GONE);
        } else {
            mNextStepButton.setVisibility(View.VISIBLE);
        }
    }

}
