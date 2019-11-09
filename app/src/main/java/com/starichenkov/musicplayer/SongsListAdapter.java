package com.starichenkov.musicplayer;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SongsListAdapter extends RecyclerView.Adapter<SongsListAdapter.SongViewHolder> {
    private List<Song> songs;
    //private List<Events> eventsCopy;
    private Context mContext;
    private int mResourse;
    private final String TAG = Resources.getSystem().getString(R.string.log_name);
    int lastPosition = -1;
    //TypeEvent typeEvent;

    //private Map<String,String> dictionary = new HashMap<String,String>();

    private OnSongListener mOnSongListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class SongViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        //TextView textNameSong;
        //TextView textTypeAuthor;
        //ImageView imageSong;
        private SongListItemBinding songListItemBinding;

        OnSongListener onSongListener;

        public SongViewHolder(@NonNull SongListItemBinding songListItemBinding, OnSongListener onSongListener) {
            super(songListItemBinding.getRoot());
            this.songListItemBinding = songListItemBinding;
            //textNameEvent = (TextView) view.findViewById(R.id.textNameEvent);
            //textTypeEvent = (TextView) view.findViewById(R.id.textTypeEvent);
            //textAddressEvent = (TextView) view.findViewById(R.id.textAddressEvent);
            //imageEvent = (ImageView) view.findViewById(R.id.imageEvent);
            //llBookMark = (LinearLayout) view.findViewById(R.id.llBookMark);
            //imageDot = (ImageView) view.findViewById(R.id.imageDot);

            //this.onSongListener = onSongListener;
            //itemView.setOnClickListener(this);
        }

        public void loadImage(String url){
            //Picasso.with(context).load(url).placeholder(R.drawable.placeholder).error(R.drawable.error_ph).into(this.image);
            //Picasso.get().load(url).placeholder(R.drawable.event_map_logo).error(R.drawable.event_map_logo).into(imageEvent);
        }

        @Override
        public void onClick(View v) {
            onSongListener.onSongClick(getAdapterPosition());
        }
    }

    public interface OnSongListener{
        void onSongClick(int position);
    }

    //Constructor
    //public void EventsListAdapter(Context mContext, Fragment fragment, int resource/*, List<Events> events*/){
        //this.mContext = mContext;
        //this.mResourse = resource;
        //this.events = events;
        //this.eventsCopy = new ArrayList<Events>(events);
        //this.mOnEventListener = (OnEventListener)fragment;
        //this.typeEvent = new TypeEvent();
    //}

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // create a new view
        //LayoutInflater inflater = LayoutInflater.from(mContext);
        //View view = inflater.inflate(mResourse, parent, false);
        //return new SongViewHolder(view, mOnSongListener);
        SongListItemBinding songListItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_song, viewGroup, false);
        return new SongViewHolder(songListItemBinding);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull SongViewHolder songViewHolder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        Song currentSong = songs.get(position);
        songViewHolder.songListItemBinding.setSong(currentSong);

        /*Events event = events.get(position);
        holder.textNameEvent.setText(event.getNameEvent());
        holder.textTypeEvent.setText(event.getTypeEvent());
        holder.textAddressEvent.setText(event.getAddressEvent());
        holder.imageDot.setImageDrawable(typeEvent.getDrawable(mContext, event.getTypeEvent()));
        //holder.imageEvent.setImageURI(Uri.parse(event.photoEvent));
        //Picasso.with(mContext).load(event.photoEvent).into(imageView);
        Log.d(TAG, "event.nameEvent: " + event.getNameEvent());
        holder.loadImage(event.getPhotoEvent());
        setAnimation(holder.llBookMark, position);*/

    }

    public void setSongList(List<Song> songs) {
        this.songs = songs;
        notifyDataSetChanged();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        /*return events.size()*/
        if (songs != null) {
            return songs.size();
        } else {
            return 0;
        }
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
