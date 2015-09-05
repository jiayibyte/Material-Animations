package com.lgvalle.material_animations;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lgvalle.material_animations.databinding.RowSampleBinding;

import java.util.List;

public class SamplesRecyclerAdapter extends RecyclerView.Adapter<SamplesRecyclerAdapter.SamplesViewHolder> {
    private final Activity activity;
    private List<Sample> samples;

    public SamplesRecyclerAdapter(Activity activity, List<Sample> samples) {
        this.activity = activity;
        this.samples = samples;
    }

    @Override
    public SamplesViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        RowSampleBinding binding = RowSampleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SamplesViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(final SamplesViewHolder viewHolder, final int position) {
        final Sample sample = samples.get(position);
        viewHolder.binding.setSample(sample);
        viewHolder.binding.sampleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        transitionToActivity(DetailActivity1.class, sample);
                        break;
                    case 1:
                        transitionToActivity(DetailActivity2.class, viewHolder, sample);
                        break;

                    case 2:
                        /*
                        TransitionManager.beginDelayedTransition(sceneRoot);
                        setViewWidth(squareRed, 500);
                        setViewWidth(squareBlue, 500);
                        setViewWidth(squareGreen, 500);
                        setViewWidth(squareOrange, 500);
                        */
                        break;

                    case 3:
                        transitionToActivity(DetailActivity3.class, viewHolder, sample, R.string.square_orange_name);
                        break;
                }
            }
        });
    }

    private void transitionToActivity(Class target, Sample sample) {
        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(activity, true);
        startActivity(target, pairs, sample);
    }


    private void transitionToActivity(Class target, SamplesViewHolder viewHolder, Sample sample, int transitionName) {
        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(activity, false,
                new Pair<>(viewHolder.binding.sampleIcon, activity.getString(transitionName)));
        startActivity(target, pairs, sample);
    }

    private void transitionToActivity(Class target, SamplesViewHolder viewHolder, Sample sample) {
        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(activity, false,
                new Pair<>(viewHolder.binding.sampleIcon, activity.getString(R.string.square_blue_name)),
                new Pair<>(viewHolder.binding.sampleName, activity.getString(R.string.sample_blue_title)));
        startActivity(target, pairs, sample);
    }

    private void startActivity(Class target, Pair<View, String>[] pairs, Sample sample) {
        Intent i = new Intent(activity, target);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairs);
        i.putExtra("sample", sample);
        activity.startActivity(i, transitionActivityOptions.toBundle());
    }

    @Override
    public int getItemCount() {
        return samples.size();
    }


    public class SamplesViewHolder extends RecyclerView.ViewHolder {
        RowSampleBinding binding;

        public SamplesViewHolder(View rootView) {
            super(rootView);
            binding = DataBindingUtil.bind(rootView);

        }
    }
}