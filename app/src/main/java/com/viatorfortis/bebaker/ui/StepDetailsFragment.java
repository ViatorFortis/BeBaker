package com.viatorfortis.bebaker.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.FixedTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;

import com.google.android.exoplayer2.upstream.DataSource;
//import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.viatorfortis.bebaker.R;
import com.viatorfortis.bebaker.model.Step;

import java.util.ArrayList;



public class StepDetailsFragment extends Fragment {

    private ArrayList<Step> mStepList;
    private int mStepId;

    private TextView mStepDescriptionTextView;
    private Button mPrevStepButton;
    private Button mNextStepButton;

    private PlayerView mPlayerView;
    private SimpleExoPlayer mPlayer;
    private DataSource.Factory mMediaDataSourceFactory;
    private MediaSource mMediaSource;



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

        mPlayerView = rootView.findViewById(R.id.pv_step_video);

        if (savedInstanceState == null) {
            populateUI();
            setButtonClickListeners();

            //mMediaDataSourceFactory = new DefaultDataSourceFactory(getActivity(), Util.getUserAgent(getActivity(), null) );

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

    private void initializePlayer() {

        mPlayerView.requestFocus();

        TrackSelection.Factory videoTrackSelectionFactory = new FixedTrackSelection.Factory();
        DefaultTrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        mPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
        mPlayerView.setPlayer(mPlayer);

        String videoUrl = mStepList.get(mStepId).getVideoUrl();
        mMediaSource = buildMediaSource(Uri.parse(videoUrl) );
        //mMediaSource = buildMediaSource(Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4") );
        mPlayer.prepare(mMediaSource, true, false);
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private void releasePlayer() {
        if (mPlayer != null) {
//            playbackPosition = mPlayer.getCurrentPosition();
//            currentWindow = mPlayer.getCurrentWindowIndex();
//            playWhenReady = mPlayer.getPlayWhenReady();
            mPlayer.release();
            mPlayer = null;
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory(Util.getUserAgent(getActivity(), null) ) )
                .createMediaSource(uri);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT <= 23 || mPlayer == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }
}
