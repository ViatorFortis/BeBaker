package com.viatorfortis.bebaker.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.FixedTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;

import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;
import com.viatorfortis.bebaker.R;
import com.viatorfortis.bebaker.model.Step;

import java.util.ArrayList;


public class StepDetailsFragment extends Fragment {

    private static final String TAG = "StepDetailsFragment";

    private View mRootView;

    private ArrayList<Step> mStepList;
    private int mStepId;

    private TextView mDescriptionTextView;
    private Button mPrevStepButton;
    private Button mNextStepButton;

    private PlayerView mPlayerView;
    private SimpleExoPlayer mPlayer;

    private ImageView mImageView;

    private boolean mVideoProvided;

    private boolean mNavigationLayoutVisible;


    private Dialog mFullScreenDialog;
    private ImageView mFullScreenIcon;

    private final String PLAYER_RESUME_WINDOW_STATE_KEY = "resumeWindow";

    private final String PLAYER_RESUME_POSITION_STATE_KEY = "resumePosition";

    private final String PLAYER_IN_FULL_SCREEN_STATE_KEY = "playerInFullScreen";

    private final String PLAYER_IN_PAUSE_STATE_KEY = "playerInPause";


    private int mResumeWindow = -1;
    private long mResumePosition = 0;
    private boolean mPlayerInFullScreen = false;
    private boolean mPlayerInPause = true;

    public void setStep(ArrayList<Step> stepList, int stepId, boolean navigationLayoutVisible) {
        Log.d(TAG, "setStep");

        mStepList = stepList;
        mStepId = stepId;
        mNavigationLayoutVisible = navigationLayoutVisible;
    }

    public StepDetailsFragment() {
    }

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach()");

        super.onAttach(context);
    }

    private void initFullScreenDialog() {
        Log.d(TAG, "initFullScreenDialog()");

        mFullScreenDialog = new Dialog(getActivity(),
                android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {
                Log.d(TAG, "onBackPressed()");

                if (mPlayerInFullScreen) {
                    closeFullScreenDialog();
                }
                super.onBackPressed();
            }
        };
    }

    private void openFullScreenDialog() {
        Log.d(TAG, "openFullScreenDialog()");

        ( (ViewGroup) mPlayerView.getParent() ).removeView(mPlayerView);
        mFullScreenDialog.addContentView(mPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT) );
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_shrink_screen) );
        mPlayerInFullScreen = true;
        mFullScreenDialog.show();
    }

    private void closeFullScreenDialog() {
        Log.d(TAG, "closeFullScreenDialog()");

        ( (ViewGroup) mPlayerView.getParent() ).removeView(mPlayerView);
        ( (FrameLayout) mRootView.findViewById(R.id.fl_player_container) ).addView(mPlayerView);
        mPlayerInFullScreen = false;
        mFullScreenDialog.dismiss();
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_expand_screen) );
    }

    private void initFullScreenButton() {
        Log.d(TAG, "initFullScreenButton()");

        PlayerControlView playerControlView = mPlayerView.findViewById(R.id.exo_controller);
        mFullScreenIcon = playerControlView.findViewById(R.id.exo_fullscreen_icon);
        FrameLayout fullScreenButton = playerControlView.findViewById(R.id.exo_fullscreen_button);
        fullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayerInFullScreen) {
                    closeFullScreenDialog();
                } else {
                    openFullScreenDialog();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");

        mRootView = inflater.inflate(R.layout.fragment_step_details, container, false);

        mDescriptionTextView = mRootView.findViewById(R.id.tv_step_description);
        mPrevStepButton = mRootView.findViewById(R.id.b_prev_step);
        mNextStepButton = mRootView.findViewById(R.id.b_next_step);

        mPlayerView = mRootView.findViewById(R.id.pv_step_video);
        mImageView = mRootView.findViewById(R.id.iv_step_image);


        final String STEP_LIST_PARCEL_KEY = getString(R.string.step_list_parcel_key),
                STEP_ID_KEY = getString(R.string.step_id_key);

        if (savedInstanceState != null) {
//                && savedInstanceState.containsKey(STEP_LIST_PARCEL_KEY)
//                && savedInstanceState.containsKey(STEP_ID_KEY)
            mStepList = savedInstanceState.getParcelableArrayList(STEP_LIST_PARCEL_KEY);
            mStepId = savedInstanceState.getInt(STEP_ID_KEY);
            mNavigationLayoutVisible = savedInstanceState.getBoolean(getString(R.string.navigation_layout_visibility_bundle_value) );

            mResumeWindow = savedInstanceState.getInt(PLAYER_RESUME_WINDOW_STATE_KEY);
            mResumePosition = savedInstanceState.getLong(PLAYER_RESUME_POSITION_STATE_KEY);
            mPlayerInFullScreen = savedInstanceState.getBoolean(PLAYER_IN_FULL_SCREEN_STATE_KEY);
            mPlayerInPause = savedInstanceState.getBoolean(PLAYER_IN_PAUSE_STATE_KEY);
        }

        LinearLayout navigationLayout = mRootView.findViewById(R.id.l_navigation);



        // redundant code
//        String videoUrl = mStepList.get(mStepId).getVideoUrl();
//        if (videoUrl == null
//                || videoUrl.isEmpty() ) {
//            mVideoProvided = false;
//            mPlayerView.setVisibility(View.GONE);
//        } else {
//            mVideoProvided = true;
//            mImageView.setVisibility(View.GONE);
//        }



        if (mNavigationLayoutVisible) {
            navigationLayout.setVisibility(View.VISIBLE);
            setNavigationButtonsVisibility();

            setButtonClickListeners();
        } else {
            navigationLayout.setVisibility(View.INVISIBLE);
        }

        Step step = mStepList.get(mStepId);

        mVideoProvided = checkVideoProvided(step.getVideoUrl() );

        mDescriptionTextView.setText(step.getDescription() );

        setMediaViewsVisibility(mVideoProvided);

        //populateUI(); - empty



//        navigationLayout.setVisibility(mNavigationLayoutVisible ? View.VISIBLE : View.INVISIBLE);
//        setButtonClickListeners();

        return mRootView;
    }

    private void setNavigationButtonsVisibility() {
            if (mStepId == 0) {
                mPrevStepButton.setVisibility(View.GONE);
            } else {
                mPrevStepButton.setVisibility(View.VISIBLE);
            }

            if (mStepId == (mStepList.size() - 1)) {
                mNextStepButton.setVisibility(View.GONE);
            } else {
                mNextStepButton.setVisibility(View.VISIBLE);
            }
    }

    private void setButtonClickListeners() {
        Log.d(TAG, "setButtonClickListeners()");

        mPrevStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "setOnClickListener()");

                mStepId--;

                if (mPlayer != null) {
                    mPlayer.stop();
                }

                mResumeWindow = -1;
                mResumePosition = 0;
                mPlayerInFullScreen = false;
                mPlayerInPause = true;


                Step step = mStepList.get(mStepId);

                mVideoProvided = checkVideoProvided(step.getVideoUrl() );

                if (!mVideoProvided
                        && mPlayer != null) {
                    mPlayer.release();
                    mPlayer = null;
                }

                mDescriptionTextView.setText(step.getDescription() );

                // 04.08.2018
                setNavigationButtonsVisibility();

                setMediaViewsVisibility(mVideoProvided);

                //populateUI(); - empty

                initMediaViews();
            }
        });

        mNextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "setOnClickListener()");

                mStepId++;

                if (mPlayer != null) {
                    mPlayer.stop();
                }

                mResumeWindow = -1;
                mResumePosition = 0;
                mPlayerInFullScreen = false;
                mPlayerInPause = true;


                Step step = mStepList.get(mStepId);

                mVideoProvided = checkVideoProvided(step.getVideoUrl() );

                if (!mVideoProvided
                        && mPlayer != null) {
                    mPlayer.release();
                    mPlayer = null;
                }

                mDescriptionTextView.setText(step.getDescription() );

                // 04.08.2018
                setNavigationButtonsVisibility();

                setMediaViewsVisibility(mVideoProvided);

                //populateUI(); - empty

                initMediaViews();
            }
        });
    }


    private boolean checkVideoProvided (String videoUrl) {
        Log.d(TAG, "checkVideoProvided()");

        return !(videoUrl == null
                    || videoUrl.isEmpty() );
    }

    private void setMediaViewsVisibility(boolean videoProvided) {
        Log.d(TAG, "setMediaViewsVisibility()");

        FrameLayout playerFrameLayout = mRootView.findViewById(R.id.fl_player_container);
        FrameLayout stepImageFrameLayout = mRootView.findViewById(R.id.fl_step_image_container);


        if (videoProvided) {
            mPlayerView.setVisibility(View.VISIBLE);
            playerFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1) );

            mImageView.setVisibility(View.GONE);
            stepImageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0) );
        } else {
            mPlayerView.setVisibility(View.GONE);
            playerFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0) );

            mImageView.setVisibility(View.VISIBLE);
            stepImageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1) );
        }
    }


    private void initMediaViews() {
        if (!mVideoProvided) {
            String thumbnailUrl = mStepList.get(mStepId).getThumbnailUrl();

            if (thumbnailUrl.isEmpty() ) {
                mImageView.setImageResource(R.drawable.img_labeled_oven);
            } else {
                Picasso.with(getActivity())
                        .load(Uri.parse(thumbnailUrl) )
                        .placeholder(R.drawable.img_labeled_loading)
                        .error(R.drawable.img_labeled_oven)
                        .into(mImageView);
            }
        } else {
            if (mPlayer == null) {
                initializePlayer();
            } else {
                String videoUrl = mStepList.get(mStepId).getVideoUrl();
                preparePlayer(videoUrl);

                if (mResumeWindow != -1) {
                    mPlayerView.getPlayer().seekTo(mResumeWindow, mResumePosition);
                }
            }
        }
    }

    private void initializePlayer() {
        Log.d(TAG, "initializePlayer()");

        mPlayerView.requestFocus();

        TrackSelection.Factory videoTrackSelectionFactory = new FixedTrackSelection.Factory();
        DefaultTrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        LoadControl loadControl = new DefaultLoadControl();
        mPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getActivity() ), trackSelector, loadControl);

        //mPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);

        mPlayer.addListener(new Player.DefaultEventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playWhenReady
                        && playbackState == Player.STATE_READY) {
                    mPlayerInPause = false;
                } else if (playWhenReady) {
                } else {
                    mPlayerInPause = true;
                }
            }
        });

        mPlayerView.setPlayer(mPlayer);

        String videoUrl = mStepList.get(mStepId).getVideoUrl();
        preparePlayer(videoUrl);

        if (mResumeWindow != -1) {
            mPlayerView.getPlayer().seekTo(mResumeWindow, mResumePosition);
        }
    }

    private void preparePlayer(String videoUrl) {
        Log.d(TAG, "preparePlayer()");

        //mPlayer.setPlayWhenReady(false);
        mPlayer.setPlayWhenReady(!mPlayerInPause);

        MediaSource mediaSource = buildMediaSource(Uri.parse(videoUrl));

        //mPlayer.prepare(mMediaSource, true, true);
        mPlayer.prepare(mediaSource);

        //mPlayer.setPlayWhenReady(!mPlayerInPause);

    }

    private void releasePlayer() {
        Log.d(TAG, "releasePlayer()");

        if (mPlayer != null) {
//            playbackPosition = mPlayer.getCurrentPosition();
//            currentWindow = mPlayer.getCurrentWindowIndex();
//            playWhenReady = mPlayer.getPlayWhenReady();
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        Log.d(TAG, "buildMediaSource()");

        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory(Util.getUserAgent(getActivity(), null) ) )
                .createMediaSource(uri);
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart()");

        super.onStart();

        if (Util.SDK_INT > 23)
        // 04.08.2018
//        if (mVideoProvided
//                && Util.SDK_INT > 23)
        {
            initMediaViews();
            // 04.08.2018
            //initializePlayer();

            initFullScreenDialog();
            initFullScreenButton();

            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                mPlayerInFullScreen = false;
            } else {
                mPlayerInFullScreen = true;
            }

            if (mPlayerInFullScreen) {
                openFullScreenDialog();
            }
        }
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume()");

        super.onResume();
        //hideSystemUi();

        if (Util.SDK_INT <= 23)
        // 04.08.2018
//        if (mVideoProvided
//                && (Util.SDK_INT <= 23 || mPlayer == null) )
        {
            initMediaViews();
            // 04.08.2018
            //initializePlayer();

            initFullScreenDialog();
            initFullScreenButton();

            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                mPlayerInFullScreen = false;
            } else {
                mPlayerInFullScreen = true;
            }

            if (mPlayerInFullScreen) {
                openFullScreenDialog();
            }
        }
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause()");

        super.onPause();

        if (mVideoProvided
                && Util.SDK_INT <= 23) {

            // 04.08.2018
//            if (mPlayerView != null
//                    && mPlayerView.getPlayer() != null) {
//                mResumeWindow = mPlayerView.getPlayer().getCurrentWindowIndex();
//                mResumePosition = Math.max(0, mPlayerView.getPlayer().getContentPosition() );
//            }

            releasePlayer();

            if (mFullScreenDialog != null) {
                mFullScreenDialog.dismiss();
            }
        }
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop()");

        super.onStop();
        if (mVideoProvided
                && Util.SDK_INT > 23) {

            // 04.08.2018
//            if (mPlayerView != null
//                    && mPlayerView.getPlayer() != null) {
//                mResumeWindow = mPlayerView.getPlayer().getCurrentWindowIndex();
//                mResumePosition = Math.max(0, mPlayerView.getPlayer().getContentPosition() );
//            }

            releasePlayer();

            if (mFullScreenDialog != null) {
                mFullScreenDialog.dismiss();
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d(TAG, "onSaveInstanceState()");

        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(getString(R.string.step_list_parcel_key), mStepList);
        outState.putInt(getString(R.string.step_id_key), mStepId);
        outState.putBoolean(getString(R.string.navigation_layout_visibility_bundle_value), mNavigationLayoutVisible);

        if (mPlayerView != null
                && mPlayerView.getPlayer() != null) {
            mResumeWindow = mPlayerView.getPlayer().getCurrentWindowIndex();
            mResumePosition = Math.max(0, mPlayerView.getPlayer().getContentPosition() );
        }

        outState.putInt(PLAYER_RESUME_WINDOW_STATE_KEY, mResumeWindow);
        outState.putLong(PLAYER_RESUME_POSITION_STATE_KEY, mResumePosition);
        outState.putBoolean(PLAYER_IN_FULL_SCREEN_STATE_KEY, mPlayerInFullScreen);
        outState.putBoolean(PLAYER_IN_PAUSE_STATE_KEY, mPlayerInPause);
    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//    }
}
