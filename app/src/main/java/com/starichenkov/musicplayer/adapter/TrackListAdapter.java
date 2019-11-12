package com.starichenkov.musicplayer.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.starichenkov.musicplayer.BR;
import com.starichenkov.musicplayer.retrofit.Track;
import com.starichenkov.musicplayer.viewmodel.TrackViewModel;

import java.util.List;

public class TrackListAdapter extends RecyclerView.Adapter<TrackListAdapter.TrackViewHolder> {

    //private final String TAG = Resources.getSystem().getString(R.string.log_name);

    private List<Track> tracks;
    private int layoutId;
    private TrackViewModel viewModel;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class TrackViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private final ViewDataBinding  binding;

        public TrackViewHolder(@NonNull ViewDataBinding  binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(TrackViewModel viewModel, Integer position){
            //viewModel.fetchTrackImagesAt(position);
            binding.setVariable(BR.viewModel, viewModel);
            binding.setVariable(BR.position, position);
            binding.executePendingBindings();
        }

        /*public void loadImage(String url){
            //Picasso.with(context).load(url).placeholder(R.drawable.placeholder).error(R.drawable.error_ph).into(this.image);
            //Picasso.get().load(url).placeholder(R.drawable.event_map_logo).error(R.drawable.event_map_logo).into(imageEvent);
        }*/
    }

    //Constructor
    public TrackListAdapter(@LayoutRes int layoutId, TrackViewModel viewModel){
        this.layoutId = layoutId;
        this.viewModel = viewModel;
    }

    private int getLayoutIdForPosition(int position) {
        return layoutId;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        ViewDataBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        viewType, parent, false);
        return new TrackViewHolder(binding);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.bind(viewModel, position);
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }

    public void setTrackList(List<Track> tracks) {
        this.tracks = tracks;
        //notifyDataSetChanged();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return tracks == null ? 0 : tracks.size();
    }

    public void filter(String text) {
        /*events.clear();
        if(text.isEmpty()){
            events.addAll(eventsCopy);
        } else{
            text = text.toLowerCase();
            for(Events event: eventsCopy){
                if(event.getNameEvent().toLowerCase().contains(text)){
                    events.add(event);
                }
            }
        }
        notifyDataSetChanged();*/
    }

}
