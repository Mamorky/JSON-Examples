package com.example.json.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.json.R;
import com.example.json.pojo.Repo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mamorky on 6/02/18.
 */

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.ViewHolder> {
    private ArrayList<Repo> repos;
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    // Store the context for easy access
    private Context mContext;

    // Pass in the contact array into the constructor
    public RepoAdapter() {
        this.repos = new ArrayList<>();
    }

    public void setRepos(ArrayList<Repo> repos) {
        this.repos.addAll(repos);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_view, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Repo repo = repos.get(position);

        holder.txvName.setText(repo.getName());
        holder.txvDescription.setText(repo.getDescription());
        holder.txvName.setText(repo.getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView txvName;
        public TextView txvDescription;
        public TextView txvCreated;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            txvName = (TextView) itemView.findViewById(R.id.txvName);
            txvDescription = (TextView) itemView.findViewById(R.id.txvDescription);
            txvCreated = (TextView) itemView.findViewById(R.id.txvCreated);
        }
    }
}
