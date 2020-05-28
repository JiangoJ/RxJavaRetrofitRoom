package com.example.rxjavaretrofit.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rxjavaretrofit.R;

import java.util.ArrayList;
import java.util.List;

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.UserHolder> {

    private List<User> users = new ArrayList<>();

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_card, parent, false);

        return new UserHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        User currentUser = users.get(position);
        holder.idTV.setText(String.valueOf(currentUser.getId()));
        holder.nameTV.setText(currentUser.getName());
        holder.websiteTV.setText(currentUser.getWebsite());

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setUsers(List<User> users){
        this.users = users;
        notifyDataSetChanged();
    }

    class UserHolder extends RecyclerView.ViewHolder {
        private TextView idTV;
        private  TextView nameTV;
        private TextView websiteTV;

        public UserHolder(@NonNull View itemView) {
            super(itemView);

            idTV = itemView.findViewById(R.id.userId);
            nameTV = itemView.findViewById(R.id.userName);
            websiteTV = itemView.findViewById(R.id.userWebsite);
        }
    }

}
